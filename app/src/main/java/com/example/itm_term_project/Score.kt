package com.example.itm_term_project

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Score: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.score)

        val quarter_note = MyQuarterNote(this)
        setContentView(quarter_note)

    }

    class MyQuarterNote(cont: Context): View(cont){
        var paint = Paint()
        var circleX: Float = 0f
        var circleY: Float = 0f
        var circleRadius = 15f

        override fun onDraw(canvas: Canvas){
            paint.setColor(Color.BLACK)

//            setBackgroundColor(Color.YELLOW)
            setBackgroundResource(R.drawable.score_background_test)


            circleX = 80f
            circleY = 80f
            canvas.drawCircle(circleX, circleY, circleRadius, paint)

        }
    }
}