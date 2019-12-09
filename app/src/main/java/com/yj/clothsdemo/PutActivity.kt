package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.yj.clothsdemo.adapter.PutAdapter
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_put.*
import android.widget.Toast
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import java.util.*


class PutActivity : AppCompatActivity() {
    private val putAdapter by lazy {
        PutAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.tv_open -> {
                        open(position)
                    }
                    R.id.tv_sure -> {
                        sure(position)
                    }
                    R.id.tv_num -> {
                        select(position)
                    }
                }
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
        setContentView(R.layout.activity_put)

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
        (application as App)
                .client
                .clothsService
                .put("appApi.GetReadyOrder", UserClient.userEntity?.list?.token ?: "", code)
                .threadSwitch()
                .subscribe(object : Observer<PutEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: PutEntity) {
                        if (t.code == 200) {
                            putAdapter.setNewData(t.list?.order ?: arrayListOf())
                            tv_address.text = (t.list?.cabinetInfo?.area
                                    ?: "") + (t.list?.cabinetInfo?.address
                                    ?: "") + (t.list?.cabinetInfo?.cabinetName ?: "")
                            tv_order_num.text = "当前需放件：${t.list?.orderCount ?: "0"}"

                            timer.schedule(object : TimerTask() {
                                override fun run() {
                                    recycle_view.post {
                                        refreshData1()
                                    }
                                }
                            }, 0, 1000)
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })

    }
    private fun refreshData1() {
        (application as App)
                .client
                .clothsService
                .put("appApi.GetReadyOrder", UserClient.userEntity?.list?.token ?: "", code)
                .threadSwitch()
                .subscribe(object : Observer<PutEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: PutEntity) {
                        if (t.code == 200) {
                            putAdapter.setNewData(t.list?.order ?: arrayListOf())
                            tv_address.text = (t.list?.cabinetInfo?.area
                                    ?: "") + (t.list?.cabinetInfo?.address
                                    ?: "") + (t.list?.cabinetInfo?.cabinetName ?: "")
                            tv_order_num.text = "当前需放件：${t.list?.orderCount ?: "0"}"
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })

    }
    private fun open(position:Int) {
//        if (putAdapter.data[position].status != "3") {
////            ToastUtils.show(this.application,"此柜已有物品，请更换柜体")
////            return
////        }
        if ((putAdapter.data[position].finalBox ?: "").isNullOrBlank()) {
            ToastUtils.show(this.application,"请选择柜号")
            return
        }
        (application as App)
                .client
                .clothsService
                .open("boxApi.openBox", UserClient.userEntity?.list?.token ?: "", putAdapter.data[position].finalBox
                        ?: return)
                .threadSwitch()
                .subscribe(object : Observer<OpenEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: OpenEntity) {
                        if (t.code == 200) {
                            putAdapter.data[position].isEnable = false
                            putAdapter.notifyItemChanged(position)
                            ToastUtils.show(applicationContext, "开柜成功")
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "打开柜门失败")
                    }
                })

    }
    private fun sure(position:Int) {
        if (putAdapter.data[position].finalBox == null) {
            ToastUtils.show(this.application,"请选择其他柜子")
            return
        }

        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        val builder = AlertDialog.Builder(this@PutActivity)
        //    设置Content来显示一个信息
        builder.setMessage("确定放件吗？")
        //    设置一个PositiveButton
        builder.setPositiveButton("确定") { dialog, which ->
            (application as App)
                    .client
                    .clothsService
                    .ensure("appApi.ConfirmOrder", UserClient.userEntity?.list?.token ?: "", putAdapter.data[position].finalBox?:"",code, putAdapter.data[position].orderId?:"")
                    .threadSwitch()
                    .subscribe(object : Observer<SaveEntity> {
                        override fun onComplete() {

                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: SaveEntity) {
                            if (t.code == 200) {
                                putAdapter.data[position].status = "4"
                                putAdapter.data[position].isEnable=true
                                putAdapter.notifyItemChanged(position)
                            } else {
                                ToastUtils.show(this@PutActivity.application,"确认失败")
                            }
                        }

                        override fun onError(e: Throwable) {
                            ToastUtils.show(this@PutActivity.application,"确认失败")
                        }

                    })

        }
        //    设置一个NegativeButton
        builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss()}
        //    显示出该对话框
        builder.show()
    }
    private fun select(position:Int) {
        if (putAdapter.data[position].isEnable != false) {
            return
        }
        (application as App)
                .client
                .clothsService
                .boxNum("appApi.GetBoxNumber", UserClient.userEntity?.list?.token ?: "", code)
                .threadSwitch()
                .subscribe(object : Observer<BoxNumEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BoxNumEntity) {
                        val smallData= arrayListOf<String>()
                        t.list?.forEach { smallData.add(it?.boxNumber ?: "") }
                        val pvOptions = OptionsPickerBuilder(this@PutActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                            putAdapter.data[position].finalBox=t.list?.get(options1)?.boxId?:""
                            putAdapter.data[position].boxNumber=t.list?.get(options1)?.boxNumber?:""
                            putAdapter.notifyItemChanged(position)
                        }).build<String>()
                        pvOptions.setPicker(smallData, null, null)
                        pvOptions.show()
                    }

                    override fun onError(e: Throwable) {
                    }

                })
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
