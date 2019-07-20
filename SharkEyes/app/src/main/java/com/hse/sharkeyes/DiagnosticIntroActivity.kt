package com.hse.sharkeyes

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.android.synthetic.main.activity_diagnosticintro.*

class DiagnosticIntroActivity: ReadingActivity() {

    private val utteranceProgressListener = object: UtteranceProgressListener() {
        override fun onDone(utteranceId: String?) {
            when(utteranceId) {
                "silence" -> speak("Disconnect the intake manifold pressure/temperature sensor and inspect the harness side for bent, spread, or corroded terminals. Is any damage to the harness found?", "question1", instruction)
                "question1" -> captureSpeech()
                "stage4silence" -> goToStageFive()
            }
        }

        override fun onError(utteranceId: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStart(utteranceId: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private fun captureSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, 1)
    }

    private fun goToStageThree() {
        speak("Repair the wiring harness as needed. Advance to TechLane for repair and SRT information", "stage3part1", instruction)
        pauseForASecond("stage3silence")
    }

    private fun goToStageFour() {
        speak("Turn the ignition on", "stage4", instruction)
        pauseForASecond("stage4silence")
    }

    private fun goToStageFive() {

        //Do a thing here with whatever we're doing with the choosing the probe
        speak("Measure the voltage between pin 1 of the intake manifold pressure/temperature sensor harness connector and ground. Enter the voltage below", "stage5instruction")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val spokenText: String? =
            data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                results[0]
            }

        when(spokenText) {
            "yes" -> goToStageThree()
            "no" -> goToStageFour()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosticintro)
    }

    override fun onInit(status: Int) {
        super.onInit(status)
        tts.setOnUtteranceProgressListener(utteranceProgressListener)
        speak("This is the Wiring Harness Guided Diagnostic with fault code SPN 3563 / FMI 3", "intro", instruction)
        pauseForASecond("silence")
    }
}

