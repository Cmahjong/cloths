package com.yj.clothsdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.yj.clothsdemo.util.GlideUtils
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.BannerEntity
import com.yj.xxxbanner.Banner
import com.yj.xxxbanner.loader.LoaderInterface
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
            scan(1001)
        }
        ll_put.onClick {
            scan(1002)
        }
        ll_price.onClick {
            PriceActivity.start(this)
        }
        ll_user.onClick {
            UserInfoActivity.start(this)
        }
    }

    private fun scan(requestCode:Int) {
        val intent = Intent("com.summi.scan")
        intent.setPackage("com.sunmi.sunmiqrcodescanner")
        intent.setClassName("com.sunmi.sunmiqrcodescanner",
                "com.sunmi.sunmiqrcodescanner.activity.ScanActivity")

        //扫码模块有一些功能选项，开发者可以通过传递参数控制这些参数，

        //所有参数都有一个默认值，开发者只要在需要的时候添加这些配置就可以。

        intent.putExtra("CURRENT_PPI", 0X0003)//当前分辨率

        //M1和V1的最佳是800*480,PPI_1920_1080 = 0X0001;PPI_1280_720 =

        //0X0002;PPI_BEST = 0X0003;

        intent.putExtra("PLAY_SOUND", true)// 扫描完成声音提示  默认true

        intent.putExtra("PLAY_VIBRATE", false)

        //扫描完成震动,默认false，目前M1硬件支持震动可用该配置，V1不支持

        intent.putExtra("IDENTIFY_INVERSE_QR_CODE", true)// 识别反色二维码，默认true

        intent.putExtra("IDENTIFY_MORE_CODE", false)// 识别画面中多个二维码，默认false

        intent.putExtra("IS_SHOW_SETTING", true)// 是否显示右上角设置按钮，默认true

        intent.putExtra("IS_SHOW_ALBUM", true)// 是否显示从相册选择图片按钮，默认true

        startActivityForResult(intent, requestCode)
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
                                            scaleType= ImageView.ScaleType.FIT_XY
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

            val result = data?.extras?.getSerializable("data") as ArrayList<HashMap<String, String>>
            result.forEach {
                TakeActivity.start(this, it["VALUE"])
                ToastUtils.show(this, it["VALUE"])
                return@forEach
            }

        } else if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            val result = data?.extras?.getSerializable("data") as ArrayList<HashMap<String, String>>
            result.forEach {
                PutActivity.start(this, it["VALUE"])
                ToastUtils.show(this, it["VALUE"])
                return@forEach
            }
        }
    }
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}
