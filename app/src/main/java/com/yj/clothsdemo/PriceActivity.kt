package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.PriceAdapter
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.activity_price.*

class PriceActivity : AppCompatActivity() {
    private val priceAdapter by lazy {
        PriceAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)
        tv_order_num.text = "待定价格：3"
        refreshData()

        img_back.onClick {
            onBackPressed()
        }
        recycle_view.apply {
            layoutManager = LinearLayoutManager(this@PriceActivity)
            adapter = priceAdapter
        }

    }

    private fun refreshData() {
        priceAdapter.setNewData(arrayListOf(
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


    companion object {
        fun start(context: Context) {
            context?.startActivity(Intent(context, PriceActivity::class.java))
        }
    }
}
