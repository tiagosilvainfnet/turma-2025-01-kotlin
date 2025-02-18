package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dev.tiagosilva.taskmanager.utils.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.submitRegisterBtn)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val LoginLink = findViewById<TextView>(R.id.login)

        btnRegister.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailInput).text.toString()
            val password = findViewById<TextView>(R.id.passwordInput).text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                register(email, password)
            }
        }

        forgotPassword.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        LoginLink.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Usuário cadastrado com sucesso!!!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Navigation.goToScreen(this, MainActivity::class.java)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Falha ao registrar usuário.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}