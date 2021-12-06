package com.example.itm_term_project

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Score : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score)


        val fl : FrameLayout = findViewById(R.id.score_page)
        val iv = ImageView(this)

        iv.setImageDrawable(resources.getDrawable(R.drawable.quarter_note))

        fl.addView(iv)

    }

}