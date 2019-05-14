package com.chaldrac.facebookrecipientapp.views.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.chaldrac.facebookrecipientapp.views.main.ui.MainActivity
import com.chaldrac.facebookrecipientapp.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class LoginActivity : AppCompatActivity() {
    @BindView(R.id.login_button)
    lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager
    @BindView(R.id.container)
    lateinit var container: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        if (AccessToken.getCurrentAccessToken() != null){
            navigateToMainScreen()
        }
        callbackManager = CallbackManager.Factory.create()
       // loginButton.setPublishPermissions(Arrays.asList("publish_actions"))
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                navigateToMainScreen()
            }

            override fun onCancel() {
                // display cancel error by Snackbar
            }

            override fun onError(error: FacebookException?) {
               // val msgError = error?.localizedMessage
                // display error
            }

        })
    }

    private fun navigateToMainScreen() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
