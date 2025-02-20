package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dev.tiagosilva.taskmanager.fragments.PasswordDifficult
import dev.tiagosilva.taskmanager.utils.Navigation
import dev.tiagosilva.taskmanager.utils.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lateinit var text: Editable;

        val btnRegister = findViewById<Button>(R.id.submitRegisterBtn)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val LoginLink = findViewById<TextView>(R.id.login)
        val confirmPassword = findViewById<EditText>(R.id.passwordConfirmInput)
        val password = supportFragmentManager.findFragmentById(R.id.passwordInput) as PasswordDifficult

        btnRegister.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailInput).text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val passText = password.passwordInput.text.toString()
                val confirmPasswordText = confirmPassword.text.toString()

                register(email, passText, confirmPasswordText)
            }
        }

        password.passwordInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    text = s;
                }
                val result = Password.verifyPasswordDificult(text.toString());
                if (result < 3) {
                    btnRegister.isEnabled = false;
                } else {
                    btnRegister.isEnabled = true;
                }
            }

        })


        forgotPassword.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        LoginLink.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword){
            Toast.makeText(
                baseContext,
                "As senhas não são iguais",
                Toast.LENGTH_SHORT,
            ).show()
        }else if (password == "" || confirmPassword == ""){
            Toast.makeText(
                baseContext,
                "As senhas não podem ser vazias",
                Toast.LENGTH_SHORT,
            ).show()
        } else {
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
}