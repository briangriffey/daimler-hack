package com.hse.sharkeyes

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity

class ReadingActivity: AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
    }

    protected fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id")
    }

    override fun onInit(status: Int) {
        
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}