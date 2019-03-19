package com.yj.clothsdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.yj.clothsdemo.util.GlideUtils
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.xxxbanner.Banner
import com.yj.xxxbanner.loader.LoaderInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val data by lazy {
        arrayListOf(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553021771504&di=73f24b3bd06329462b57df56dcc763a5&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201303%2F09%2F20130309193622_XYZyt.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553021771504&di=73f24b3bd06329462b57df56dcc763a5&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201303%2F09%2F20130309193622_XYZyt.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553021771504&di=73f24b3bd06329462b57df56dcc763a5&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201303%2F09%2F20130309193622_XYZyt.jpeg"
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        ll_take.onClick {
            ToastUtils.show(this,"取件")
        }
        ll_put.onClick {
            ToastUtils.show(this,"放件")
        }
        ll_price.onClick {
            ToastUtils.show(this,"价格")
        }
        ll_user.onClick {
            ToastUtils.show(this,"业务员")
        }
    }
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}
