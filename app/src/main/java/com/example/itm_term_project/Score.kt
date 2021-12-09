package com.example.itm_term_project

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaRecorder
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton

class Score: AppCompatActivity()  {

    //오디오권한
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO
    )

    //오디오권한
    companion object{
        private const val REQUESTED_RECORD_AUDIO_PERMISSION = 201
    }

    private var recorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score)

        //앱 실행시 오디오 권한 요청(처음에만)
        requestAudioPermission()

        val fl : FrameLayout = findViewById(R.id.score_page)

        val quarter_note = MyQuarterNote(this)
        fl.addView(quarter_note)

        val recording_button: Button = findViewById(R.id.recording_button)

        recording_button.setOnClickListener({
            startRecording()
        })

    }

    class MyQuarterNote(cont: Context): View(cont){
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

    //오디오 권한 요청
    private fun requestAudioPermission(){
        requestPermissions(requiredPermissions, REQUESTED_RECORD_AUDIO_PERMISSION)
    }

    //오디오 권한 요청
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        val audioRecordPermissionGranted=
            requestCode == REQUESTED_RECORD_AUDIO_PERMISSION &&
                    grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
        
        if(!audioRecordPermissionGranted){
            finish()
            //오디오 권한 거절시 앱 종료
        }
    }

    private fun startRecording(){
        Toast.makeText(this, "recording start", Toast.LENGTH_SHORT).show()
        recorder = MediaRecorder().apply{
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)//저장경로 나중에 바꾸기
            prepare()
        }
        recorder?.start()
//        recordTimeTextView.startCountup()
//        soundVisualizerView.startVisualizing(false)
//        state = State.ON_RECORDING
//        https://whyprogrammer.tistory.com/584
    }

    private val recordingFilePath: String by lazy{
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    private fun stopRecording(){
        recorder?.run{
            stop()
            release()
        }
        recorder = null
//        suondVisualizerView.stopVisualizing()
//        recordTimeTextView.stopCountup()
//        state = State.AFTER_RECORDING
    }
}
