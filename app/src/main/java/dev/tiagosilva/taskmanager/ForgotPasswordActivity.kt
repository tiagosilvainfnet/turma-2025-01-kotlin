package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import dev.tiagosilva.taskmanager.utils.Navigation

class ForgotPasswordActivity : AppCompatActivity() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val forgotPasswordBtn = findViewById<Button>(R.id.forgotPasswordBtn)
        val login = findViewById<TextView>(R.id.login)

        forgotPasswordBtn.setOnClickListener {
            val passwordText = findViewById<TextView>(R.id.emailInput).text.toString();
            recoveryPassword(passwordText)
        }

        login.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    fun recoveryPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ForgotPasswordActivity, "Link enviado com sucesso!!!", Toast.LENGTH_SHORT).show()
                    Navigation.goToScreen(this@ForgotPasswordActivity, LoginActivity::class.java)
                } else {
                    Toast.makeText(this@ForgotPasswordActivity, "Falha ao enviar link de reset de senha.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}