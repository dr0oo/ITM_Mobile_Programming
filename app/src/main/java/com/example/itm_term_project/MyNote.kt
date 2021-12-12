package com.example.itm_term_project

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View



//bar: 몇번째 마디인지
//notNum: 몇번째 음표인지
//key: 어떤 음인지
//note: 어떤 음표인지
class MyNote(cont: Context, noteNum: Int, key: Int, note: String): View(cont) {
    val paint = Paint()

    var circleX: Float = 0f
    var circleY: Float = 0f
    var lineX: Float = 0f
    var lineY: Float = 0f
    var circleRadius = 15f

    var bar = (noteNum / 8).toInt()
    var noteNum = noteNum
    var key = key
    var note = note

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)

        paint.setColor(Color.BLACK)
        paint.setStrokeWidth(8f)

        circleX = calculateX(noteNum)
        circleY = 200f

        //마지막으로 어떤 음표인지 확인하고 그림
        when(note){
            "quarter" -> drawQuarterNote(canvas, circleX, circleY)
        }

    }

    fun drawQuarterNote(canvas: Canvas, circleX: Float, circleY: Float){
        lineY = circleY
        //head
        canvas?.drawCircle(circleX, circleY, circleRadius, paint)
        //tail
        when(checkLine(circleY)){
            "left" -> leftTail(canvas, circleX, circleY)
            "right" -> rightTail(canvas, circleX, circleY)

        }
    }

    //선을 왼쪽에 그릴건지 오른쪽에 그릴건지 리턴해줌
    fun checkLine(y: Float): String{
        var where=""
        when(y){
            in 0f..155f -> where = "left"
            in 156f..410f -> where = "right"
            else -> where="I don't know"
        }
        return where
    }
    
    //오른쪽 꼬리: 약간 위쪽에 있음
    fun rightTail(canvas: Canvas, circleX: Float, circleY: Float){
        Log.d("tail","right")
        lineX = circleX+15f
        lineY = circleY
        canvas.drawLine(lineX, lineY, lineX, lineY-90, paint)
    }

    //왼쪽 꼬리: 약간 아래쪽에 있음
    fun leftTail(canvas: Canvas, circleX: Float, circleY: Float){
        Log.d("tail","left")
        lineX = circleX-15f
        lineY = circleY
        canvas.drawLine(lineX, lineY, lineX, lineY+90, paint)
    }

    fun calculateX(noteNum: Int): Float{
        var x = 0f
        when(noteNum % 8){
            1 -> x = 120f
            2 -> x = 120f + (430f / 4f)
            3 -> x = 120f + 2 * (430f / 4f)
            4 -> x = 120f + 3 * (430f / 4f)
            5 -> x = 600f
            6 -> x = 600f + (430f / 4f)
            7 -> x = 600f + 2 * (430f / 4f)
            0 -> x = 600f + 3 * (430f / 4f)
        }
        return x
    }
}