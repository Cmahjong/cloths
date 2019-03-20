package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.TakeAdapter
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.activity_take.*
import java.util.*

class TakeActivity : AppCompatActivity() {
    private val takeAdapter1 by lazy {
        TakeAdapter()
    }
    private val takeAdapter2 by lazy {
        TakeAdapter()
    }
    private val timer by lazy {
        Timer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take)
        tv_address.text = "新都区天府新谷"
        tv_order_num.text = "本次订单数量：3"
        refreshData()
        timer.schedule(object : TimerTask() {
            override fun run() {
                recycle_view1.post {
                    refreshData()
                }
            }
        }, 0, 2000)
        img_back.onClick {
            onBackPressed()
        }
        recycle_view1.apply {
            layoutManager = LinearLayoutManager(this@TakeActivity)
            adapter = takeAdapter1
        }
        recycle_view2.apply {
            layoutManager = LinearLayoutManager(this@TakeActivity)
            adapter = takeAdapter2
        }
    }

    private fun refreshData() {
        takeAdapter1.setNewData(arrayListOf(
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any()

        ))
        takeAdapter2.setNewData(arrayListOf(
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any(),
                Any()

        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    companion object {
        fun start(context: Context) {
            context?.startActivity(Intent(context, TakeActivity::class.java))
        }
    }
}
