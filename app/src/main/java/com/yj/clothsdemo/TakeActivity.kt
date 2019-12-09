package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.yj.clothsdemo.adapter.Take1Adapter
import com.yj.clothsdemo.adapter.TakeAdapter
import com.yj.clothsdemo.util.AidlUtil
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.OpenEntity
import com.yj.service.response.OrderBox
import com.yj.service.response.PrintContentEntity
import com.yj.service.response.TakeBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_take.*
import java.text.FieldPosition
import java.util.*


class TakeActivity : AppCompatActivity() {

    private val takeAdapter1 by lazy {
        TakeAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                open(position)
            }
        }
    }
    private val takeAdapter2 by lazy {
        Take1Adapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                open1(position)
            }
        }
    }
    private val timer by lazy {
        Timer()
    }

    private val code by lazy {
        intent.getStringExtra(EXTRA_CODE)
    }
    private var ensure = false
    private var isEnableClose=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take)

        refreshData()
        img_back.onClick {
            if (!isEnableClose) {
                ToastUtils.show(this,"有柜体未关")
            } else {
                onBackPressed()
            }

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
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                recycle_view1.post {
                                    refreshData1()
                                }
                            }
                        }, 0, 1000)
                    }

                    override fun onError(e: Throwable) {
                    }

                })

    }

    private fun refreshData1() {
        (application as App)
                .client
                .clothsService
                .take("appApi.GetBoxInfo_time", UserClient.userEntity?.list?.token ?: "", code)
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
                        tv_order_num.text = "本次订单数量：${t.list?.orderCount ?: "0"}"

                        t.list?.orderBox?.forEach {
                            if (it?.onOff == "1") {
                                isEnableClose = false
                                return
                            } else {
                                isEnableClose = true
                            }
                        }
                        t.list?.emptyBox?.forEach {
                            if (it?.onOff == "1") {
                                isEnableClose = false
                                return
                            } else {
                                isEnableClose = true
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                })

    }
    override fun onBackPressed() {
        if (ensure) {
            super.onBackPressed()
        } else {
            isOrder()
        }

    }

    private fun isOrder() {
        (application as App)
                .client
                .clothsService
                .isOrder("appApi.IsOrder", UserClient.userEntity?.list?.token ?: "", code)
                .threadSwitch()
                .subscribe(object : Observer<Boolean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Boolean) {
                        if (!t) {
                            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                            val builder = AlertDialog.Builder(this@TakeActivity)
                            //    设置Title的图标
                            builder.setIcon(R.mipmap.ic_launcher)
                            //    设置Title的内容
                            builder.setTitle("提示")
                            //    设置Content来显示一个信息
                            builder.setMessage("还有物品未取出，确定返回？")
                            //    设置一个PositiveButton
                            builder.setPositiveButton("确定") { dialog, which ->
                                ensure = true
                                dialog.dismiss()
                                onBackPressed()
                            }
                            //    设置一个NegativeButton
                            builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                            //    显示出该对话框
                            builder.show()
                        } else {
                            ensure = true
                            onBackPressed()
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })

    }
    private fun open(position: Int) {
        (application as App)
                .client
                .clothsService
                .open("appApi.StaffOpenBox", UserClient.userEntity?.list?.token ?: "", takeAdapter1.data[position].boxId
                        ?: return)
                .threadSwitch()
                .subscribe(object : Observer<OpenEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: OpenEntity) {
                        if (t.code == 200) {
                            ToastUtils.show(applicationContext, "开柜成功")
                            takeAdapter1.data[position].onOff="1"
                            takeAdapter1.clickBoxId.add( takeAdapter1.data[position].boxId?:"")
                            takeAdapter1.notifyItemChanged(position)
                            printContent(position)
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "打开箱门失败")
                    }
                })
    }

    private fun printContent(position: Int) {
        (application as App)
                .client
                .clothsService
                .print("AppApi.GetOrderRecord", UserClient.userEntity?.list?.token ?: "",  takeAdapter1.data[position].boxId
                        ?: return)
                .threadSwitch()
                .subscribe(object : Observer<PrintContentEntity> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {

                    }


                    override fun onNext(t: PrintContentEntity) {
                        if (t.code == 200) {
                            var content:StringBuilder=StringBuilder()
                            t.list?.forEach {
                                content.append(it+"\n\n")
                            }
                            AidlUtil.getInstance().printText(content.toString(), 28F, false, false)
                        } else {
                            ToastUtils.show(applicationContext, t.msg)
                        }

                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取打印信息失败")
                    }
                })
    }

    private fun open1(position: Int) {
        (application as App)
                .client
                .clothsService
                .open("boxApi.openBox", UserClient.userEntity?.list?.token ?: "",  takeAdapter2.data[position].boxId
                        ?: return)
                .threadSwitch()
                .subscribe(object : Observer<OpenEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: OpenEntity) {
                        if (t.code == 200) {
                            ToastUtils.show(applicationContext, "开柜成功")
                            takeAdapter2.data[position].onOff="1"
                            takeAdapter2.notifyItemChanged(position)
//                            refreshData1()
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
