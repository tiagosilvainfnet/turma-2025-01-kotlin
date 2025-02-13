package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.tiagosilva.taskmanager.utils.Navigation

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.submitRegisterBtn)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val LoginLink = findViewById<TextView>(R.id.login)

        btnRegister.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }

        forgotPassword.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        LoginLink.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }
}