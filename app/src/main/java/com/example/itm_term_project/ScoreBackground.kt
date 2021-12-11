package com.example.itm_term_project

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class ScoreBackground(cont: Context): View(cont) {
    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)

        setBackgroundResource(R.drawable.score_background_test)

    }
}