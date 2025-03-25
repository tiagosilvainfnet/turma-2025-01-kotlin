package dev.tiagosilva.taskmanager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar


class TaskActivity : AppCompatActivity() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks")
    var task_id: String = ""

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val currentDateTime = Calendar.getInstance()
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val month = currentDateTime.get(Calendar.MONTH)
        val year = currentDateTime.get(Calendar.YEAR)
        val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentDateTime.get(Calendar.MINUTE)

        val title = findViewById<EditText>(R.id.title_input)
        val description = findViewById<EditText>(R.id.description_input)
        val date = findViewById<EditText>(R.id.date_input)
        val time = findViewById<EditText>(R.id.time_input)

        val saveBtn = findViewById<Button>(R.id.save_btn)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val dateIcon = findViewById<ImageView>(R.id.date_icon)
        val timeIcon = findViewById<ImageView>(R.id.time_icon)

        loadTask(title, description, date, time)

        saveBtn.setOnClickListener {
            val titleText = title.text.toString();
            val descriptionText = description.text.toString();
            val dateText = date.text.toString();
            val timeText = time.text.toString();

            saveTask(titleText, descriptionText, dateText, timeText)
        }

        backButton.setOnClickListener {
            finish()
        }

        dateIcon.setOnClickListener {
            openDatePicker(date, year, month, day);
        }
        date.setOnClickListener {
            openDatePicker(date, year, month, day);
        }

        timeIcon.setOnClickListener {
            openTimePicker(time, hour, minute)
        }
        time.setOnClickListener {
            openTimePicker(time, hour, minute)
        }
    }

    fun openDatePicker(date: EditText, year: Int, month: Int, day: Int) {
        val datePickerDialog = DatePickerDialog(this, {_, yearOfYear, monthOfYear, dayOfMonth ->
            date.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, yearOfYear))
        }, year, month, day);
        datePickerDialog.show();
    }

    fun openTimePicker(time: EditText, hour: Int, minute: Int) {
        val timePickerDialog = TimePickerDialog(this, {_, hourOfDay, minuteOfHour->
            time.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour))
        }, hour, minute, true);
        timePickerDialog.show();
    }

    fun loadTask(title: EditText, description: EditText, date: EditText, time: EditText) {
        this.task_id = intent.getStringExtra("taskId") ?: ""
        if(task_id === "") return

        val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks/${task_id}")

        db_ref.addListenerForSingleValueEvent(object: ValueEventListener {
            val ctx = this@TaskActivity;

            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()) return;

                title.setText(snapshot.child("title").value.toString())
                description.setText(snapshot.child("description").value.toString())
                date.setText(snapshot.child("date").value.toString())
                time.setText(snapshot.child("time").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(ctx, R.string.error_loading_task, Toast.LENGTH_SHORT).show()
            }
        })
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
            Toast.makeText(this, R.string.task_saved, Toast.LENGTH_SHORT).show()
        } else {
            val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/tasks/${task_id}")
            db_ref.addListenerForSingleValueEvent(object: ValueEventListener {
                val ctx = this@TaskActivity;

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) return;

                    val task = snapshot.value as HashMap<String, String>;

                    task["title"] = title
                    task["description"] = description
                    task["date"] = date
                    task["time"] = time
                    db_ref.setValue(task)
                    Toast.makeText(ctx, R.string.task_edited, Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(ctx, R.string.error_task_edited, Toast.LENGTH_SHORT).show()
                }

            })
        }
        android.os.Handler().postDelayed({
            finish()
        }, 3000)
    }
}