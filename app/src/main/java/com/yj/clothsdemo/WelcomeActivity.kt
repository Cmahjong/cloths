package com.yj.clothsdemo

import android.content.Intent
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

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_welcome_activity)
        val token = getSharedPreferences("USER", MODE_PRIVATE).getString("USER_TOKEN", "")
        if (token.isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            (application as App)
                    .client
                    .clothsService
                    .userInfo("AppApi.GetStaffInfo", token)
                    .threadSwitch()
                    .subscribe(object : Observer<UserEntity> {
                        override fun onComplete() {

                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: UserEntity) {
                            if (t.code == 200) {
                                t.list?.token=token
                                UserClient.userEntity = t
                                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                            }
                        }

                        override fun onError(e: Throwable) {
                            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                        }
                    })

        }

    }


}
