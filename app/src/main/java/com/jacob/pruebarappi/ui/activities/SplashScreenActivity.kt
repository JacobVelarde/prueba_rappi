package com.jacob.pruebarappi.ui.activities

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val mLauncher: Launcher = Launcher()

    override fun onStart() {
        super.onStart()
        mHandler.postDelayed(mLauncher, 3000)
    }

    override fun onStop() {
        mHandler.removeCallbacks(mLauncher)
        super.onStop()
    }

    private fun launch() {
        if (!isFinishing) {
            startActivity(Intent(this@SplashScreenActivity, MainMenuActivity::class.java))
            finish()
        }
    }

    private inner class Launcher : Runnable {
        override fun run() {
            launch()
        }
    }
}
