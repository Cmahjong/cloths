package com.yj.clothsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.yj.clothsdemo.util.ToastUtils
import com.yj.clothsdemo.util.onClick
import com.yj.clothsdemo.util.threadSwitch
import com.yj.service.UserClient
import com.yj.service.response.UserEntity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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
            login()

        }
    }

    private fun login() {
        (application as App)
                .client
                .clothsService
                .login("appApi.StaffLogin", et_phone.text.toString(), et_password.text.toString())
                .threadSwitch()
                .subscribe(object : Observer<UserEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: UserEntity) {
                        if (t.code == 200) {
                            UserClient.userEntity = t
                            getSharedPreferences("USER", MODE_PRIVATE).edit().putString("USER_TOKEN", t.list?.token ?: "").apply()

                            MainActivity.start(this@LoginActivity)
                            ToastUtils.show(application, "登录成功")
                        } else {
                            ToastUtils.show(application, "登录失败")
                        }

                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.show(application, "登录失败")
                    }
                })

    }
}
