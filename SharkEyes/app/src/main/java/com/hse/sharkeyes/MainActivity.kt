package com.hse.sharkeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    override fun onInit(status: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var tts: TextToSpeech

    protected fun speak(text: String) {
        tts.speak("Stephen, you are one sexy human being", TextToSpeech.QUEUE_FLUSH, null, "id")
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        button.setOnClickListener(View.OnClickListener { speak("") })
    }
}
