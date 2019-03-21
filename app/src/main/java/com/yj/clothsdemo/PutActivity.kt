package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.PutAdapter
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.activity_put.*

class PutActivity : AppCompatActivity() {
    private val putAdapter by lazy {
        PutAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put)
        tv_address.text = "新都区天府新谷"
        tv_order_num.text = "当前需放件：3"
        refreshData()

        img_back.onClick {
            onBackPressed()
        }
        recycle_view.apply {
            layoutManager = LinearLayoutManager(this@PutActivity)
            adapter = putAdapter
        }

    }

    private fun refreshData() {
        putAdapter.setNewData(arrayListOf(
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
        private const val EXTRA_CODE="EXTRA_CODE"
        fun start(context: Context,code:String?) {
            context?.startActivity(Intent(context, PutActivity::class.java).apply {
                putExtra(EXTRA_CODE,code)
            })
        }
    }
}
