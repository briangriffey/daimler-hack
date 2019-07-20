package com.hse.sharkeyes

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.UtteranceProgressListener
import kotlinx.android.synthetic.main.activity_diagnosticintro.*

class DiagnosticIntroActivity: ReadingActivity() {

    private val utteranceProgressListener = object: UtteranceProgressListener() {
        override fun onDone(utteranceId: String?) {
            when(utteranceId) {
                "stage1silence"-> goToStageTwo()
                "stage2instruction" -> captureSpeech(2)
                "stage5instruction" -> captureSpeech(5)
                "stage4silence" -> goToStageFive()
                "stage7silence" -> captureSpeech(7)
            }
        }

        override fun onError(utteranceId: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStart(utteranceId: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private fun captureSpeech(stage: Int) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, stage)
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
        speak("Measure the voltage between pin 1 of the intake manifold pressure/temperature sensor harness connector and ground. What is the voltage?", "stage5instruction")
    }

    private fun goToStageSeven() {
        speak("Check for the presence of voltage between pins 2 and 4 on the intake manifold pressure/temperature sensor harness connector. Is voltage present?", "stage7instruction")
        pauseForASecond("stage7silence")
    }

    private fun goToStageTwo() {
        speak("Disconnect the intake manifold pressure/temperature sensor and inspect the harness side for bent, spread, or corroded terminals. Is any damage to the harness found?", "stage2instruction", instruction)
    }

    private fun goToStageOne() {
        speak("This is the Wiring Harness Guided Diagnostic with fault code SPN 3563 / FMI 3", "intro", instruction)
        pauseForASecond("stage1silence")
    }

    private fun goToStageNine() {
        speak("This component troubleshooting is complete with no trouble found. Go to the next component.", "stage9instruction")
    }

    private fun stageTwoDecision(spokenText: String?) {
        when(spokenText) {
            "yes" -> goToStageThree()
            "no" -> goToStageFour()
        }
    }

    private fun stageSevenDecision(spokenText: String?) {
        when(spokenText) {
            "yes" -> goToStageNine()
            "no" -> goToStageThree()
        }
    }

    private fun stageFiveDecision(spokenText: String?) {
        var voltage = 0
        when(spokenText) {
            "zero" -> voltage = 0
            "one" -> voltage = 1
            "two" -> voltage = 2
            "three" -> voltage = 3
            "four" -> voltage = 4
            "five" -> voltage = 5
            "six" -> voltage = 6
            else -> voltage = 200
        }

        if(voltage >= 6) {
            goToStageThree()
        } else {
            goToStageSeven()
        }
    }

    override fun onActivityResult(stage: Int, resultCode: Int, data: Intent?) {
        val spokenText: String? =
            data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                results[0]
            }

        when(stage) {
            2 -> stageTwoDecision(spokenText)
            7 -> stageSevenDecision(spokenText)
            5 -> stageFiveDecision(spokenText)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosticintro)
    }

    override fun onInit(status: Int) {
        super.onInit(status)
        tts.setOnUtteranceProgressListener(utteranceProgressListener)
        goToStageOne()
    }
}
