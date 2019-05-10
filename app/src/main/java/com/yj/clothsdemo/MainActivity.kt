package com.yj.clothsdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.yj.clothsdemo.util.GlideUtils
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.BannerEntity
import com.yj.service.response.TakeBean
import com.yj.xxxbanner.Banner
import com.yj.xxxbanner.loader.LoaderInterface
import com.yj.zxinglibrary.CaptureActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val data by lazy {
        arrayListOf<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshData()
        ll_take.onClick {
            val intent = Intent(this@MainActivity, CaptureActivity::class.java)
            startActivityForResult(intent, 1001)
        }
        ll_put.onClick {
            val intent = Intent(this@MainActivity, CaptureActivity::class.java)
            startActivityForResult(intent, 1002)
        }
        ll_price.onClick {
            PriceActivity.start(this)
        }
        ll_user.onClick {
            UserInfoActivity.start(this)
        }
    }

    private fun refreshData() {
        (application as App)
                .client
                .clothsService
                .banner("appApi.GetBanner", UserClient.userEntity?.list?.token ?: "")
                .threadSwitch()
                .subscribe(object : Observer<BannerEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BannerEntity) {
                        t.list?.forEach {
                            data.add(it?.pictureUrl?:"")
                        }
                        ( banner as Banner<String>).setImages(data)
                                .setImageLoader(object : LoaderInterface<ImageView,String>{
                                    override fun createView(context: Context): ImageView {
                                        return ImageView(context).apply {
                                            scaleType= ImageView.ScaleType.CENTER_CROP
                                        }
                                    }

                                    override fun displayView(context: Context, bean: String, view: ImageView, position: Int, count: Int) {
                                        GlideUtils.loadPic(view,bean)
                                    }
                                })
                                .start()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })

        (application as App)
                .client
                .clothsService
                .longBanner("appApi.GetLongBanner", UserClient.userEntity?.list?.token ?: "")
                .threadSwitch()
                .subscribe(object : Observer<BannerEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BannerEntity) {
                        t.list?.forEach {
                          GlideUtils.loadPic(img_head,it?.pictureUrl?:"")
                        }

                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(applicationContext, "获取失败")
                    }

                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(CaptureActivity.KEY_DATA)
            TakeActivity.start(this,result)
        } else if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(CaptureActivity.KEY_DATA)
            PutActivity.start(this,result)
        }
    }
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}
