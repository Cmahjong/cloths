package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.PutRecodeAdapter
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.activity_put_recode.*

class PutRecodeActivity : AppCompatActivity() {

    private val putRecodeAdapter by lazy {
        PutRecodeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_put_recode)
        refreshData(false)
        recycle_view.apply {
            layoutManager = LinearLayoutManager(this@PutRecodeActivity)
            adapter = putRecodeAdapter
        }
        img_back.onClick {
            onBackPressed()
        }
        tv_all.onClick {
            refreshData(true)
        }
        tv_time.onClick {
            refreshData(false)
        }
    }

    private fun refreshData(isAll: Boolean) {
        val data = arrayListOf<Any>(
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
                Any(),
                Any()

        )
        putRecodeAdapter.setNewData(data)
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, PutRecodeActivity::class.java))
        }
    }
}
