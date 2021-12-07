package com.example.itm_term_project

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Score_Fail_with_FrameLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_fail_with_framelayout)

        //여기서부터 기기의 가로,세로길이 받아오는 중
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        //여기까지

        //xml에서 framelayout 받아옴
        val fl : FrameLayout = findViewById(R.id.score_page)

        //생성하려고 하는 image view
        val iv = ImageView(this)
        iv.setImageDrawable(resources.getDrawable(R.drawable.quarter_note))

        //악보에 음표 추가
        fl.addView(iv)

    }

}

