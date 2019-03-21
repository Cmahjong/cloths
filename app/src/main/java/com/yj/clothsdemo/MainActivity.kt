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
import com.yj.xxxbanner.Banner
import com.yj.xxxbanner.loader.LoaderInterface
import com.yj.zxinglibrary.CaptureActivity
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(CaptureActivity.KEY_DATA)
            Toast.makeText(this, "qrcode result is $result", Toast.LENGTH_SHORT).show()
            TakeActivity.start(this,result)
        } else if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(CaptureActivity.KEY_DATA)
            Toast.makeText(this, "qrcode result is $result", Toast.LENGTH_SHORT).show()
            PutActivity.start(this,result)
        }
    }
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}
