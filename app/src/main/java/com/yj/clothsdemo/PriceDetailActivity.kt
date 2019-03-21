package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.PriceDetailAdapter
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.activity_price_detail.*

class PriceDetailActivity : AppCompatActivity() {
    private val priceDetailAdapter by lazy {
        PriceDetailAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_detail)
        img_back.onClick {
            onBackPressed()
        }
        tv_reduce.onClick {
            onBackPressed()
        }
        recycle_view.apply {
            layoutManager = LinearLayoutManager(this@PriceDetailActivity)
            adapter = priceDetailAdapter
        }
        priceDetailAdapter.setNewData(arrayListOf(Any(), Any(),Any(),Any(),Any(),Any(),Any() ))
    }
    companion object {
        fun start(context: Context) {
            context?.startActivity(Intent(context, PriceDetailActivity::class.java))
        }
    }
}
