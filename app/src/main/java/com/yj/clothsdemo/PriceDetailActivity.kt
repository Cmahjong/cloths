package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
    private var num = 0
    private var price = 0
    private val priceDetailAdapter by lazy {
        PriceDetailAdapter().apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.tv_delete -> {
                        delete(data[position], position)
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
    private var typeOne: Data8? = null
    private var typeTwo: Data10? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_detail)
        img_back.onClick {
            onBackPressed()
        }
        tv_add.onClick {
            onBackPressed()
        }
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
            priceDetailAdapter.data[position].number = (options1 + 1).toString()
            priceDetailAdapter.notifyItemChanged(position)
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
                        val pvOptions = OptionsPickerBuilder(this@PriceDetailActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                            typeTwo = t.list?.classList?.get(options1)
                            priceDetailAdapter.data[position].className = typeTwo?.detailName ?: ""
                            priceDetailAdapter.notifyItemChanged(position)
                            resultPrice()
                        }).build<Data10>()
                        pvOptions.setPicker(t.list?.classList, null, null)
                        pvOptions.show()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "删除失败")
                    }

                })
    }

    private fun selectOne(position: Int) {
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
                        val pvOptions = OptionsPickerBuilder(this@PriceDetailActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                            typeOne = t.list?.classList?.get(options1)
                            priceDetailAdapter.data[position].className = t.list?.classList?.get(options1)?.className ?: ""
                            priceDetailAdapter.notifyItemChanged(position)
                        }).build<Data8>()
                        pvOptions.setPicker(t.list?.classList, null, null)
                        pvOptions.show()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
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
                        } else {
                            ToastUtils.show(applicationContext, "删除失败")
                        }
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "删除失败")
                    }

                })
    }

    //    private fun add() {
//        (application as App)
//                .client
//                .clothsService
//                .add("appApi.DeleteOrderPrice", UserClient.userEntity?.list?.token ?: "", data.id
//                        ?: "")
//                .threadSwitch()
//                .subscribe(object : Observer<DeleteEntity> {
//                    override fun onComplete() {
//
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(t: DeleteEntity) {
//                        if (t.code == 200) {
//                            ToastUtils.show(applicationContext, "删除成功")
//
//                        } else {
//                            ToastUtils.show(applicationContext, "删除失败")
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        ToastUtils.show(applicationContext, "删除失败")
//                    }
//
//                })
//    }
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
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })
    }

    fun resultPrice() {
        priceDetailAdapter.data.forEach {
            num += (it?.number ?: "1").toInt()
            price += (it?.price ?: "1").toInt()
        }
        tv_num1.text = num.toString() + "，"
        tv_price.text = price.toString() + "元"
    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_DATA1 = "EXTRA_DATA1"
        fun start(context: Context, orderId: String?, shopId: String?) {
            context?.startActivity(Intent(context, PriceDetailActivity::class.java).apply {
                putExtra(EXTRA_DATA, orderId)
                putExtra(EXTRA_DATA1, shopId)
            })
        }
    }
}
