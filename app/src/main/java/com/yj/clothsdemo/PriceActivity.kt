package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.PriceAdapter
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.PriceEntity
import com.yj.service.response.TakeBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_price.*

class PriceActivity : AppCompatActivity() {
    private val priceAdapter by lazy {
        PriceAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                PriceDetailActivity.start(this@PriceActivity,data[position].orderID,data[position].shopId,data[position].cabinetName,data[position].pieceNumber)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)
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
        (application as App)
                .client
                .clothsService
                .priceList("appApi.GetPriceSetList", UserClient.userEntity?.list?.token ?: "")
                .threadSwitch()
                .subscribe(object : Observer<PriceEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: PriceEntity) {
                        if (t.code == 200) {
                            priceAdapter.setNewData(t.list?.list)
                            tv_order_num.text = "未定价订单：${t.list?.count?:0}"
                        }

                    }

                    override fun onError(e: Throwable) {

                    }

                })

    }


    companion object {
        fun start(context: Context) {
            context?.startActivity(Intent(context, PriceActivity::class.java))
        }
    }
}
