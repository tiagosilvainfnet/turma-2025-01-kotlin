package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class TaskActivity : AppCompatActivity() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks")
    var task_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val title = findViewById<EditText>(R.id.title_input);
        val description = findViewById<EditText>(R.id.description_input);
        val date = findViewById<EditText>(R.id.date_input);
        val time = findViewById<EditText>(R.id.time_input);

        val saveBtn = findViewById<Button>(R.id.save_btn)
        val backButton = findViewById<ImageView>(R.id.back_button)

        loadTask(title, description, date, time)

        saveBtn.setOnClickListener {
            val titleText = title.text.toString();
            val descriptionText = description.text.toString();
            val dateText = date.text.toString();
            val timeText = time.text.toString();

            saveTask(titleText, descriptionText, "2025-02-10", "11:00:00")
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    fun loadTask(title: EditText, description: EditText, date: EditText, time: EditText) {
        this.task_id = intent.getStringExtra("task_id") ?: ""
        if(task_id === "") return

        // TODO: Pegar a tarefa do banco de dados e preencher formulário
//        title.setText("Teste")
//        description.setText("Teste")
//        date.setText("Teste")
//        time.setText("Teste")
    }

    fun saveTask(title: String, description: String, date: String, time: String) {

        if(task_id === "") {
            val task = hashMapOf(
                "title" to title,
                "description" to description,
                "date" to date,
                "time" to time
            )

            db_ref.push().setValue(task);
            Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_SHORT).show()
            android.os.Handler().postDelayed({
                finish()
            }, 5000)
        } else {
            val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks/${task_id}")
//            TODO: Atualizar tarefa no banco de dados

            Toast.makeText(this, "Tarefa edita!", Toast.LENGTH_SHORT).show()
            android.os.Handler().postDelayed({
                finish()
            }, 5000)
        }

    }
}