package com.hse.sharkeyes

import android.opengl.Visibility
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_diagnosticintro.*

abstract class ReadingActivity: AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit protected var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
    }

    protected fun speak(text: String, id: String, instructionView: TextView? = null) {
//        crossfadeLoadingAnimation(endAlpha = 1f)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
        runOnUiThread({instructionView?.setText(text)})
        spin_kit_wave.visibility = VISIBLE
    }

    protected fun pauseForASecond(id: String) {
        tts.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD, id)
    }

    override fun onInit(status: Int) {
        print("woot")
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    private fun crossfadeLoadingAnimation(endAlpha: Float) {
        spin_kit_wave.alpha = 1f - endAlpha
        spin_kit_wave.animate().alpha(endAlpha).setDuration(7000)
            .setInterpolator(AccelerateInterpolator()).start()
    }
}