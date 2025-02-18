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

class LoginActivity : AppCompatActivity() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.submitLoginBtn)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val registerLink = findViewById<TextView>(R.id.create_account)

        btnLogin.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailInput).text.toString()
            val password = findViewById<TextView>(R.id.passwordInput).text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                login(email, password)
            }
        }

        forgotPassword.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        registerLink.setOnClickListener {
            Navigation.goToScreen(this, RegisterActivity::class.java)
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Seja bem-vindo ${user.email}", Toast.LENGTH_SHORT).show()
                        Navigation.goToScreen(this@LoginActivity, MainActivity::class.java)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Falha ao realizar login", Toast.LENGTH_SHORT).show()
                }
            }
    }
}