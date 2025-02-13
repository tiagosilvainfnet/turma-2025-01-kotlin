package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.tiagosilva.taskmanager.utils.Navigation

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val forgotPasswordBtn = findViewById<Button>(R.id.forgotPasswordBtn)
        val login = findViewById<TextView>(R.id.login)

        forgotPasswordBtn.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }

        login.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }
}