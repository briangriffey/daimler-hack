package com.hse.sharkeyes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spin_kit_wave.visibility = View.GONE
        crossfadeBluetoothLoadingAnimation()
        Handler().postDelayed({
            spin_kit.visibility = View.INVISIBLE
            val intent = Intent(this@MainActivity, WorkActivity::class.java)
            startActivity(intent)
        }, 5000)
    }

    private fun crossfadeBluetoothLoadingAnimation() {

        val shortAnimationDuration = 6000
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        spin_kit.alpha = 0f
        spin_kit.visibility = View.VISIBLE
        bluetooth.visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        spin_kit.animate()
            .alpha(1f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(null)

    }
}
