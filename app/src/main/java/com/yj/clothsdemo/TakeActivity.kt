package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yj.clothsdemo.adapter.Take1Adapter
import com.yj.clothsdemo.adapter.TakeAdapter
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.OpenEntity
import com.yj.service.response.OrderBox
import com.yj.service.response.TakeBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_take.*
import java.util.*

class TakeActivity : AppCompatActivity() {
    private val takeAdapter1 by lazy {
        TakeAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                open(data[position])
            }
        }
    }
    private val takeAdapter2 by lazy {
        Take1Adapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                open(data[position])
            }
        }
    }
    private val timer by lazy {
        Timer()
    }

    private val code by lazy {
        intent.getStringExtra(EXTRA_CODE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take)
        timer.schedule(object : TimerTask() {
            override fun run() {
                recycle_view1.post {
                    refreshData()
                }
            }
        }, 0, 2000)
        refreshData()
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
        (application as App)
                .client
                .clothsService
                .take("appApi.GetBoxInfo", UserClient.userEntity?.list?.token ?: "", code)
                .threadSwitch()
                .subscribe(object : Observer<TakeBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: TakeBean) {
                        takeAdapter1.setNewData(t.list?.orderBox ?: arrayListOf())
                        takeAdapter2.setNewData(t.list?.emptyBox ?: arrayListOf())
                        tv_address.text =(t.list?.cabinetInfo?.area?:"")+ (t.list?.cabinetInfo?.address?:"")+ (t.list?.cabinetInfo?.cabinetName?:"")
                        tv_order_num.text = "本次订单数量：${t.list?.orderCount?:"0"}"
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })

    }


    private fun open(data: OrderBox) {
        (application as App)
                .client
                .clothsService
                .open("appApi.StaffOpenBox", UserClient.userEntity?.list?.token ?: "", data.boxId
                        ?: return)
                .threadSwitch()
                .subscribe(object : Observer<OpenEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: OpenEntity) {
                        if (t.code == 200) {
                           refreshData()
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "打开箱门失败")
                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
    companion object {
        private const val EXTRA_CODE = "EXTRA_CODE"
        fun start(context: Context, code: String?) {
            context?.startActivity(Intent(context, TakeActivity::class.java).apply {
                putExtra(EXTRA_CODE, code)
            })
        }
    }
}
