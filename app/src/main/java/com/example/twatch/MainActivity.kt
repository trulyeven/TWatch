package com.example.twatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.twatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
class MainActivity : AppCompatActivity() {

    private var isRunning = false
    private var startTime = 0L
    private var pauseTime = 0L

    // viewBinding
    private lateinit var viewBinding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.clock.text = "00:00.00"

        viewBinding.startBtn.setOnClickListener {
            if (!isRunning) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                isRunning = true
                startStopwatch()
                startTime = SystemClock.elapsedRealtime() - pauseTime
            }
        }

        viewBinding.stopBtn.setOnClickListener {
            if (isRunning) {
                pauseTime = SystemClock.elapsedRealtime() - startTime
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                isRunning = false
            }
        }

        viewBinding.resetBtn.setOnClickListener {
            if (isRunning) {
                // 스톱워치가 실행 중이면 중지시키고 리셋
                isRunning = false
                pauseTime = 0L
            } else {
                // 스톱워치가 중지된 상태에서 리셋
                pauseTime = 0L
            }
            runOnUiThread {
                viewBinding.clock.text = "00:00.00"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startStopwatch() {
        Timer().scheduleAtFixedRate(0, 10) {

            if (isRunning) {
                val time = SystemClock.elapsedRealtime() - startTime + pauseTime
                val m = (time / 60000).toInt()
                val s = ((time % 60000) / 1000).toInt()
                val ms = (time % 1000).toInt()

                runOnUiThread {
                    viewBinding.clock.text = String.format("%02d:%02d.%02d", m, s, ms / 10)
                }
            }
        }
    }

}