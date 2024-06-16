package com.glucode.about_you.about

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.glucode.about_you.R
import com.glucode.about_you.about.views.ProfileCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.flow.collect

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val viewModel: AboutViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
               Toast.makeText(context, getString(R.string.allow_permission), Toast.LENGTH_SHORT).show()
            }
        }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let { uri ->
                    viewModel.updateProfileImage(uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val engineerName = arguments?.getString("name")
        val engineer = MockData.engineers.first { it.name == engineerName }

        setupEngineerProfile(engineer)
        viewModel.updateEngineerName(engineer.name)
        setUpQuestions()
    }

    private fun setupEngineerProfile(engineer: Engineer) {
        val profileCardView = ProfileCardView(requireContext()) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
            } else {
                openGallery()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.imageUri.collect {
                profileCardView.imageUri = it
            }
        }
        with(engineer) {

            if (defaultImageName != "")
             profileCardView.imageUri = defaultImageName.toUri()
            profileCardView.name = engineer.name
            profileCardView.role = engineer.role
            profileCardView.years = engineer.quickStats.years
            profileCardView.bugs = engineer.quickStats.bugs
            profileCardView.coffees = engineer.quickStats.coffees
        }

        binding.container.addView(profileCardView)
    }

    private fun setUpQuestions() {
        val engineerName = arguments?.getString("name")
        val engineer = MockData.engineers.first { it.name == engineerName }

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_EXTERNAL_STORAGE_PERMISSION
                )
            }
        }
    }

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE_PERMISSION = 1
    }
}