package com.example.itm_term_project

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class MyQuarterNote(cont: Context): View(cont) {
    var paint = Paint()
    var circleX: Float = 0f
    var circleY: Float = 0f
    var lineX: Float = 0f
    var lineY: Float = 0f
    var circleRadius = 15f

    override fun onDraw(canvas: Canvas){
        paint.setColor(Color.BLACK)
        paint.setStrokeWidth(8f)

        setBackgroundResource(R.drawable.score_background_test)

        circleX = 120f
        circleY = 200f

        canvas.drawCircle(circleX, circleY, circleRadius, paint)
        if(checkLine(circleY).equals("left")){
            leftLine(canvas, circleX, circleY)
        }else{
            rightLine(canvas, circleX, circleY)
        }
    }

    //선을 왼쪽에 그릴건지 오른쪽에 그릴건지 리턴해줌
    //나중에 이거는 음표 클래스에 옮겨도 될듯
    fun checkLine(y: Float): String{
        var where=""
        when(y){
            in 0f..155f -> where = "left"
            in 156f..410f -> where = "right"
            else -> where="I don't know"
        }
        return where
    }

    fun rightLine(canvas: Canvas, circleX: Float, circleY: Float){
        lineX = circleX+15f
        lineY = circleY
        canvas.drawLine(lineX, lineY, lineX, lineY-90, paint)
    }

    fun leftLine(canvas: Canvas, circleX: Float, circleY: Float){
        lineX = circleX-15f
        lineY = circleY
        canvas.drawLine(lineX, lineY, lineX, lineY+90, paint)
    }
}