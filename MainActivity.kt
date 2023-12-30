package com.yhjang.kotlinexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.abs
import java.util.Random
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    var p_num = 3
    var k = 1
    val point_list = mutableListOf<Float>()
    var is_Blind = true

    fun start() {
        setContentView(R.layout.activity_start)
        val tv_num: TextView = findViewById(R.id.tv_num)
        val btn_down: Button = findViewById(R.id.btn_minus)
        val btn_up: Button = findViewById(R.id.btn_plus)
        val btn_start: Button = findViewById(R.id.btn_start)
        val btn_blind: Button = findViewById(R.id.btn_blind)
        tv_num.text = p_num.toString()
        btn_blind.setOnClickListener {
            is_Blind = !is_Blind
            if (is_Blind == true)
               btn_blind.text = "ON"
            else btn_blind.text = "OFF"
        }
        btn_down.setOnClickListener {
                p_num--
                if (p_num == 0) {
                    p_num = 1
                }
                tv_num.text = p_num.toString()
            }

        btn_up.setOnClickListener {
            p_num ++
            tv_num.text = p_num.toString()
        }

        btn_start.setOnClickListener {
            main()
        }


    }
    fun main() {
        setContentView(R.layout.activity_main)
        var stage = 1
        var sec: Int = 0
        var timerTask: Timer? = null
        val tv: TextView = findViewById(R.id.tv_random)
        val tv_t: TextView = findViewById(R.id.tv_num)
        val btn: Button = findViewById(R.id.btn_start)
        val btn_st: Button = findViewById(R.id.btn_st)
        val tv_p: TextView = findViewById(R.id.tv_point)
        val tv_s: TextView = findViewById(R.id.tv_ent)

        val bg_main: ConstraintLayout = findViewById(R.id.bg_main)
        val col_list = mutableListOf<String>("#FFF8DBDF","#FFFBD5CB","#FFFBF3BD","#FFE1FBBD","#FFE5F6FF","#FFE5E8FF","#FFEADBFB")
        var col_index = k%7 - 1
        if (col_index == -1) {
            col_index = 6
        }
        val col_sel = col_list.get(col_index)

        val random_box = Random()
        val num = random_box.nextInt(1001)
        tv.text = ((num.toFloat() / 100)).toString()

        bg_main.setBackgroundColor(Color.parseColor(col_sel))

        btn.text = "Start"
        tv_s.text = "Person $k"
        btn_st.setOnClickListener {
            point_list.clear()
            k = 1
            is_Blind = true
            start()
        }
        btn.setOnClickListener {
            stage++
            if (stage == 2) {
                timerTask = timer(period = 10) {
                        sec++
                        runOnUiThread {
                            if (is_Blind == false && stage == 2) {
                                tv_t.text = (sec.toFloat() / 100).toString()
                            } else if (is_Blind == true && stage == 2) {
                                tv_t.text = "★★"
                            }
                        }
                    }
                    btn.text = "Stop"
                } else if (stage == 3){
                    tv_t.text = (sec.toFloat() / 100).toString()
                    timerTask?.cancel()
                    val Point = ((abs(sec - num).toFloat()) / 100)
                    point_list.add(Point)
                    tv_p.text = Point.toString()
                    btn.text = "Next"
                    stage = 0
                } else if (stage == 1){
                    if (k < p_num) {
                        k++
                        main()
                    } else {
                        end()
                    }
                }
            }

        }

    fun end() {
        setContentView(R.layout.activity_end)
        val tv_bottom: TextView = findViewById(R.id.tv_bottom)
        val tv_bpoint: TextView = findViewById(R.id.tv_pt)
        val btn_init: TextView = findViewById(R.id.btn_init)
        tv_bpoint.text = point_list.min().toString()
        var index_last = point_list.indexOf(point_list.min())
        tv_bottom.text = "Person "+(index_last+1).toString()
        btn_init.setOnClickListener {
            point_list.clear()
            k = 1
            is_Blind = true
            start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}