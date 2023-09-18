package com.example.twatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isRunning = false


        stopWatch()

    }

    @SuppressLint("SetTextI18n")
    private fun stopWatch() {
        val clock: TextView = findViewById(R.id.clock)

        val startBtn: Button = findViewById(R.id.startBtn)
        val stopBtn: Button = findViewById(R.id.stopBtn)
        val resetBtn: Button = findViewById(R.id.resetBtn)

//        val calendar: Calendar = Calendar.getInstance()
//        val h: Int = calendar.get(Calendar.HOUR_OF_DAY)
//        val m: Int = calendar.get(Calendar.MINUTE)
//        val s: Int = calendar.get(Calendar.SECOND)
//        val ms: Int = calendar.get(Calendar.MILLISECOND)


        var pauseTime = 0L
        var startTime = 0L

        startBtn.setOnClickListener {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)  // 화면 켜짐 유지

            Timer().scheduleAtFixedRate( object : TimerTask() {
                override fun run() {
                    val currentTime = SystemClock.elapsedRealtime()
                    val time = currentTime - startTime + pauseTime
                    val h = (time / 3600000).toInt()
                    val m = ((time % 3600000) / 60000).toInt()
                    val s = ((time % 60000) / 1000).toInt()
                    val ms = (time % 1000).toInt()
                    if (h >= 1) {
                        clock.text = String.format("%02d:%02d:%02d.%02d", h, m, s, ms)
                    } else {
                        clock.text = String.format("%02d:%02d.%02d", m, s, ms)
                    }
                }
            }, 0, 10)
            startTime = SystemClock.elapsedRealtime()
        }

        stopBtn.setOnClickListener {
//            clock.stop()
//            pauseTime = SystemClock.elapsedRealtime() - clock.base
        }


        resetBtn.setOnClickListener {
//            clock.stop()
//            clock.base = 0
            val text1: TextView = findViewById(R.id.textView)
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            text1.text = SystemClock.elapsedRealtime().toString()
        }
    }


}