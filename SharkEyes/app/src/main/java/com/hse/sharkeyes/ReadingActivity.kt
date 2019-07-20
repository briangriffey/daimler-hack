package com.hse.sharkeyes

import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity

class ReadingActivity: AppCompatActivity(), TextToSpeech.OnInitListener {

    override fun onInit(status: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val tts = TextToSpeech(this, this)

    protected fun speak(text: String) {
        tts.speak("Test test", TextToSpeech.QUEUE_FLUSH, null, "id")
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}