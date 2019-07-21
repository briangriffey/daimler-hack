package com.hse.sharkeyes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.UtteranceProgressListener
import android.view.View
import android.view.View.*
import android.view.animation.AccelerateInterpolator
import androidx.core.view.ViewCompat.animate
import kotlinx.android.synthetic.main.activity_diagnosticintro.*
import java.util.*

class DiagnosticIntroActivity: ReadingActivity() {

    val fourSevenFive = 10;

    private val utteranceProgressListener = object: UtteranceProgressListener() {
        override fun onDone(utteranceId: String?) {
            when(utteranceId) {
                "stage1silence"-> goToStageTwo()
                "stage2instruction" -> captureSpeech(2)
                "stage5instruction" -> captureSpeech(5)
                "stage4silence" -> goToStageFourAndAHalf()
                "stage4.5silence" -> goToStageFourAndThreeQuarters()
                "stage4.75" -> captureSpeech(fourSevenFive)
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

    private fun goToStageFourAndAHalf() {
        speak("Special Tool Required. Use the link below to determine the correct diagnostic probe part number for the Intake Manifold Pressure/Temperature Sensor.", "stage4.5instruction", instruction)
        pauseForASecond("stage4.5silence")
    }

    private fun goToStageFourAndThreeQuarters() {
        runOnUiThread({instructionBG.visibility = VISIBLE})

        speak("I found some relevant manual entries. Please choose from the following options." +
                "One of the following is what you're looking for, is it \n" +
                "Option 1. Intake Pressure/Temperature Sensor " +
                "Option 2. Intake Manifold Temperature Sensor DD13, DD15 and DD16 "+
                "Option 3. Take me to the manual","stage4.75")

    }

    private fun goToStageFive() {
        runOnUiThread({instructionBG.visibility = INVISIBLE})
        //Do a thing here with whatever we're doing with the choosing the probe
        speak("Measure the voltage between pin 1 of the intake manifold pressure/temperature sensor harness connector and ground. What is the voltage?", "stage5instruction", instruction)
    }

    private fun goToStageSeven() {
        speak("Check for the presence of voltage between pins 2 and 4 on the intake manifold pressure/temperature sensor harness connector. Is voltage present?", "stage7instruction", instruction)
        pauseForASecond("stage7silence")
    }

    private fun goToStageTwo() {
        speak("Disconnect the intake manifold pressure/temperature sensor and inspect the harness side for bent, spread, or corroded terminals. Is any damage to the harness found?", "stage2instruction", instruction)
        instruction.text = "Disconnect the intake manifold pressure/temperature sensor and inspect the harness side for bent, spread, or corroded terminals. Is any damage to the harness found?"
    }

    private fun goToStageOne() {
        speak("This is the Wiring Harness Guided Diagnostic with fault code SPN 3563 / FMI 3", "stage1silence", instruction)
    }

    private fun goToStageNine() {
        speak("This component troubleshooting is complete with no trouble found. Go to the next component.", "stage9instruction")
        runOnUiThread({spin_kit_wave.visibility = INVISIBLE})
    }

    private fun stageTwoDecision(spokenText: String?) {
        when(spokenText) {
            "yes" -> goToStageThree()
            "no" -> goToStageFour()
            else -> goToStageFour()
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
            "0" -> voltage = 0
            "one" -> voltage = 1
            "1" -> voltage = 1
            "two" -> voltage = 2
            "2" -> voltage = 2
            "three" -> voltage = 3
            "3" -> voltage = 3
            "four" -> voltage = 4
            "4" -> voltage = 4
            "five" -> voltage = 5
            "5" -> voltage = 5
            "six" -> voltage = 6
            "6" -> voltage = 6
            else -> voltage = 200
        }

        if(voltage >= 5) {
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
            fourSevenFive -> goToStageFive()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosticintro)
        spin_kit.visibility = GONE
    }

    override fun onInit(status: Int) {
        super.onInit(status)
        tts.setOnUtteranceProgressListener(utteranceProgressListener)

        spin_kit_wave.visibility = GONE
        crossfadeBluetoothLoadingAnimation()
        Handler().postDelayed({
            // TODO: Also hide bluetooth icon
            spin_kit.visibility = GONE
            goToStageOne()
        }, 1000)}

    private fun crossfadeBluetoothLoadingAnimation() {

        val shortAnimationDuration = 2000
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        spin_kit.alpha = 0f
        spin_kit.visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        spin_kit.animate()
            .alpha(1f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(null)

    }
}
