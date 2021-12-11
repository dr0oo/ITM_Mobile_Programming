package com.example.itm_term_project

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.Drawable
import android.icu.text.AlphabeticIndex
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import java.io.IOException

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

    private val recordingFilePath: String by lazy{
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

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
                    stopRecording()
                    recording_button.setAndShowEnabled(false)
                }
            }
        })


        val play: Button = findViewById(R.id.recording_test)
        play.setOnClickListener({
            startPlaying()
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
        Log.d("recording","started")
        recorder = MediaRecorder().apply{
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(recordingFilePath)//저장경로 나중에 바꾸기
            prepare()
        }
        recorder?.start()
//        recordTimeTextView.startCountup()
//        soundVisualizerView.startVisualizing(false)
        state = State.ON_RECORDING
//        https://whyprogrammer.tistory.com/584
    }

    private fun stopRecording(){
        recorder?.run{
            stop()
            reset()
            release()
        }
        recorder = null
//        suondVisualizerView.stopVisualizing()
//        recordTimeTextView.stopCountup()
        state = State.AFTER_RECORDING

        Log.d("recording","stopped")
    }


    private fun startPlaying() {
        // MediaPlayer
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).setUsage(AudioAttributes.USAGE_MEDIA).build())
                prepare() // may take a while depending on the media, consider using .prepareAsync() for streaming

                start()
            }

        // 전부 재생 했을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }

//        //재생
//        player?.start()
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
