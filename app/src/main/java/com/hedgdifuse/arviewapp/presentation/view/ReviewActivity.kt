package com.hedgdifuse.arviewapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hedgdifuse.arviewapp.R
import com.hedgdifuse.arviewapp.databinding.ActivityReviewBinding

class ReviewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_ArviewApp)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting rating bar
        with(binding.reviewRating) {
            rating = 5f
            stepSize = 1f
            numStars = 5
        }
    }
}