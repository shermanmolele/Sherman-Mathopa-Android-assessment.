package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewProfileCardBinding

class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)  {

    private val binding: ViewProfileCardBinding =
        ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)


    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
    }

    var name: String? = null
        set(value) {
            field = value
            binding.name.text = value
        }

    var role: String? = null
        set(value) {
            field = value
            binding.role.text = value
        }

    var years: Int? = null
        set(value) {
            field = value
            binding.years.title.text = "Years"
            binding.years.amount.text = value.toString()
        }

    var coffees: Int? = null
        set(value) {
            field = value
            binding.coffees.title.text = "Coffees"
            binding.coffees.amount.text = value.toString()
        }

    var bugs: Int? = null
        set(value) {
            field = value
            binding.bugs.title.text = "Bugs"
            binding.bugs.amount.text = value.toString()
        }

}