package com.nizamisadykhov.messenger.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.ui.main.MainActivity
import com.nizamisadykhov.messenger.ui.signup.SignUpActivity
import com.nizamisadykhov.messenger.utils.showLongToast

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: LoginPresenter
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenterImpl(this)
        bindViews()
    }

    override fun bindViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_sign_up)
        progressBar = findViewById(R.id.progress_bar)
        btnLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun showAuthError() {
        val text = getString(R.string.auth_error_login)
        showLongToast(text)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_login) {
            presenter.executeLogin(
                username = etUsername.text.toString(),
                password = etPassword.text.toString()
            )
        } else if (view.id == R.id.btn_sign_up) {
            navigateToSignUp()
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun setUsernameError() {
        etUsername.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        etPassword.error = getString(R.string.password_error)
    }

    override fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigateToHome() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}