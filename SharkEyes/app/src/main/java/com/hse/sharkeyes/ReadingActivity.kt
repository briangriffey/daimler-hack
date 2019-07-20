package com.hse.sharkeyes

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

abstract class ReadingActivity: AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit protected var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
    }

    protected fun speak(text: String, id: String, instructionView: TextView? = null) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
        instructionView?.setText(text)
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
}