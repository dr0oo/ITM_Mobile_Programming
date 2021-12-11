package com.example.itm_term_project

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.Drawable
import android.icu.text.AlphabeticIndex
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
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

    private var state = State.BEFORE_RECORDING

    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score)

        //앱 실행시 오디오 권한 요청(처음에만)
        requestAudioPermission()

        val fl : FrameLayout = findViewById(R.id.score_page)

        val quarter_note = MyQuarterNote(this)
        fl.addView(quarter_note)

        val recording_button: ImageButton = findViewById(R.id.recording_button)
        recording_button.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24   )

        recording_button.setOnClickListener({
            when(state){
                State.BEFORE_RECORDING ->{
                    recording_button.setImageResource(R.drawable.ic_baseline_stop_24)
                    startRecording()
                }
                State.ON_RECORDING->{
                    recording_button.setImageResource(R.drawable.ic_baseline_stop_24)
                    stopRecording()
                }
                State.AFTER_RECORDING ->{
                    recording_button.setAndShowEnabled(false)
                }
            }
        })

    }

    //ImageButton setEnabled(false)
    fun Drawable.toGrayscale(): Drawable =
        deepCopy().apply { setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN) }

    fun Drawable.deepCopy(): Drawable =
        constantState?.newDrawable()?.mutate() ?:
        throw RuntimeException("Called on null Drawable!")

    fun ImageButton.setAndShowEnabled(enabled: Boolean) {
        if (enabled == isEnabled)
            return
        isEnabled = enabled
        if (enabled) {
            setImageDrawable(tag as Drawable)
        }
        else {
            if (tag == null)
                tag = drawable
            setImageDrawable(drawable.toGrayscale())
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
        state = State.ON_RECORDING
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
        state = State.AFTER_RECORDING
    }


    private fun startPlaying() {
        // MediaPlayer
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }

        // 전부 재생 했을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }

        player?.start() // 재생
//        recordTimeTextView.startCountup()
//        soundVisualizerView.startVisualizing(true)

        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
//        soundVisualizerView.stopVisualizing()
//        recordTimeTextView.stopCountup()

        state = State.AFTER_RECORDING
    }
}
