package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.tiagosilva.taskmanager.utils.Navigation

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val saveBtn = findViewById<Button>(R.id.save_btn)

        saveBtn.setOnClickListener {
            Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_SHORT).show()
            android.os.Handler().postDelayed({
                Navigation.goToScreen(this, MainActivity::class.java)
            }, 5000)
        }
    }
}