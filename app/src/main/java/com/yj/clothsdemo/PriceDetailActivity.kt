package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.yj.clothsdemo.adapter.PriceDetailAdapter
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_price_detail.*


class PriceDetailActivity : AppCompatActivity() {

    private val priceDetailAdapter by lazy {
        PriceDetailAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.tv_delete -> {
                        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                        val builder = AlertDialog.Builder(this@PriceDetailActivity)
                        //    设置Title的图标
                        builder.setIcon(R.mipmap.ic_launcher)
                        //    设置Title的内容
                        builder.setTitle("提示")
                        //    设置Content来显示一个信息
                        builder.setMessage("确定删除该项")
                        //    设置一个PositiveButton
                        builder.setPositiveButton("确定") { dialog, which ->
                            delete(data[position], position)
                            dialog.dismiss()
                        }
                        //    设置一个NegativeButton
                        builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                        //    显示出该对话框
                        builder.show()

                    }
                    R.id.rl_type_one -> {
                        selectOne(position)
                    }
                    R.id.rl_type_two -> {
                        selectTwo(data[position], position)
                    }
                    R.id.rl_type_three -> {
                        selectThree(position)
                    }
                }
            }
        }
    }


    private val orderId by lazy {
        intent.getStringExtra(EXTRA_DATA)
    }
    private val shopId by lazy {
        intent.getStringExtra(EXTRA_DATA1)
    }
    private val area by lazy {
        intent.getStringExtra(EXTRA_DATA2)
    }
    private val numAndTime by lazy {
        intent.getStringExtra(EXTRA_DATA3)
    }
    private var typeOne: Data8? = null
    private var typeTwo: Data10? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_detail)
        img_back.onClick {
            onBackPressed()
        }
//        tv_add.onClick {
//            add()
//        }
        tv_save.onClick {
            save()
        }
        tv_area.text = area
        tv_num.text = numAndTime
        refreshData()
        recycle_view.apply {
            layoutManager = LinearLayoutManager(this@PriceDetailActivity)
            adapter = priceDetailAdapter
        }

    }

    private fun selectThree(position: Int) {
        if (priceDetailAdapter.data[position].detailName == null) {
            ToastUtils.show(application, "请先选择小类")
            return
        }
        val pvOptions = OptionsPickerBuilder(this@PriceDetailActivity, OnOptionsSelectListener { options1, options2, options3, v ->
            if ((priceDetailAdapter.data[position].number ?: "0") == "0") {
              add()
            }
            priceDetailAdapter.data[position].number = (options1 + 1).toString()
            priceDetailAdapter.notifyItemChanged(position)
            change(priceDetailAdapter.data[position])
            resultPrice()
        }).build<String>()
        pvOptions.setPicker(arrayListOf("1", "2", "3", "4", "5"), null, null)
        pvOptions.show()
    }

    private fun selectTwo(data: Data6, position: Int) {
        if (data.className == null) {
            ToastUtils.show(application, "请先选择大类")
            return
        }
        val smallData = mutableListOf<String>()
        (application as App)
                .client
                .clothsService
                .smallType("appApi.GetDetailName", UserClient.userEntity?.list?.token
                        ?: "", data?.className ?: "", shopId
                        ?: "")
                .threadSwitch()
                .subscribe(object : Observer<SmallTypeEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SmallTypeEntity) {
                        t.list?.classList?.forEach { smallData.add(it?.detailName ?: "") }

                        val pvOptions = OptionsPickerBuilder(this@PriceDetailActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                            typeTwo = t.list?.classList?.get(options1)
                            priceDetailAdapter.data[position].detailName = typeTwo?.detailName ?: ""
                            priceDetailAdapter.data[position].price = typeTwo?.price ?: ""
                            priceDetailAdapter.data[position].unit = typeTwo?.unit ?: ""
                            priceDetailAdapter.data[position].priceId = typeTwo?.priceId ?: ""
                            if (priceDetailAdapter.data[position].number != "0") {
                                change(priceDetailAdapter.data[position])
                            }
                            priceDetailAdapter.notifyItemChanged(position)
                            resultPrice()
                        }).build<String>()
                        pvOptions.setPicker(smallData, null, null)
                        pvOptions.show()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "删除失败")
                    }

                })
    }

    private fun selectOne(position: Int) {
        val data = mutableListOf<String>()
        (application as App)
                .client
                .clothsService
                .bigType("appApi.GetClassName", UserClient.userEntity?.list?.token ?: "", shopId
                        ?: "")
                .threadSwitch()
                .subscribe(object : Observer<BigTypeEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BigTypeEntity) {
                        t.list?.classList?.forEach {
                            data.add(it?.className ?: "")
                        }
                        val pvOptions = OptionsPickerBuilder(this@PriceDetailActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                            typeOne = t.list?.classList?.get(options1)
                            priceDetailAdapter.data[position].className = t.list?.classList?.get(options1)?.className ?: ""
                            priceDetailAdapter.notifyItemChanged(position)
                        }).build<String>()
                        pvOptions.setPicker(data, null, null)
                        pvOptions.show()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "暂无数据")
                    }

                })
    }

    private fun delete(data: Data6, position: Int) {
        (application as App)
                .client
                .clothsService
                .delete("appApi.DeleteOrderPrice", UserClient.userEntity?.list?.token ?: "", data.id
                        ?: "")
                .threadSwitch()
                .subscribe(object : Observer<DeleteEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: DeleteEntity) {
                        if (t.code == 200) {
                            ToastUtils.show(applicationContext, "删除成功")
                            priceDetailAdapter.remove(position)
                            resultPrice()
                        } else {
                            ToastUtils.show(applicationContext, "删除失败")
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "删除失败")
                    }

                })
    }

    private fun add() {
        (application as App)
                .client
                .clothsService
                .add("appApi.AddOrderPrice", UserClient.userEntity?.list?.token ?: "", orderId)
                .threadSwitch()
                .subscribe(object : Observer<AddEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: AddEntity) {
                        priceDetailAdapter.addData(t.list ?: return)
                        priceDetailAdapter.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }
    private fun save() {
        if (orderId.isNullOrBlank()) {
            ToastUtils.show(applicationContext, "请输入内容")
            return
        }
//        priceDetailAdapter.data.forEach {
//            if ((it.number ?: "0") == "0") {
//                ToastUtils.show(applicationContext, "信息不完整")
//                return
//            }
//        }
        (application as App)
                .client
                .clothsService
                .save("appApi.SaveOrderPrice", UserClient.userEntity?.list?.token ?: "", orderId)
                .threadSwitch()
                .subscribe(object : Observer<SaveEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SaveEntity) {
                        if (t.code == 200) {
                            ToastUtils.show(applicationContext, "保存成功")

                        } else {
                            ToastUtils.show(applicationContext, "保存失败")
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "保存失败")
                    }

                })
    }
    private fun change(data: Data6) {
        (application as App)
                .client
                .clothsService
                .change("appApi.UpdateOrderPrice", UserClient.userEntity?.list?.token ?: "",data.id?:"",data.number?:"",data?.unit?:"",data?.price?:"",data?.priceId?:"")
                .threadSwitch()
                .subscribe(object : Observer<SaveEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SaveEntity) {

                    }

                    override fun onError(e: Throwable) {

                    }

                })
    }
    private fun refreshData() {
        (application as App)
                .client
                .clothsService
                .priceDetail("appApi.GetPriceSet", UserClient.userEntity?.list?.token
                        ?: "", orderId)
                .threadSwitch()
                .subscribe(object : Observer<PriceDetailEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: PriceDetailEntity) {
                        priceDetailAdapter.setNewData(t.list)
                        resultPrice()
                        if (priceDetailAdapter.data.isEmpty()) {
                            add()
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "暂无数据")
                    }

                })
    }

    fun resultPrice() {
         var num = 0
         var price = 0
        priceDetailAdapter.data.forEach {
            num += (it?.number ?: "1").toInt()
            price += (((it?.price ?: "1").toInt())*((it?.number ?: "1").toInt()))
        }
        tv_num1.text = num.toString() + "，"
        tv_price.text = price.toString() + "元"
    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_DATA1 = "EXTRA_DATA1"
        const val EXTRA_DATA2 = "EXTRA_DATA2"
        const val EXTRA_DATA3 = "EXTRA_DATA3"
        fun start(context: Context, orderId: String?, shopId: String?, area: String?, num: String?) {
            context?.startActivity(Intent(context, PriceDetailActivity::class.java).apply {
                putExtra(EXTRA_DATA, orderId)
                putExtra(EXTRA_DATA1, shopId)
                putExtra(EXTRA_DATA2, area)
                putExtra(EXTRA_DATA3, num)
            })
        }
    }
}
