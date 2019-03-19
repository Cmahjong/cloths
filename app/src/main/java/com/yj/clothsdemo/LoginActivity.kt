package com.yj.clothsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import kotlinx.android.synthetic.main.actvity_login_activity.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_login_activity)
        et_phone.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                tv_login.isEnabled=(s?.length!=0&& et_password.text.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        et_password.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                tv_login.isEnabled=(s?.length!=0&& et_phone.text.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        tv_login.onClick {
            ToastUtils.show(this,"登录")
            MainActivity.start(this)
        }
    }
}
