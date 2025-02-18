package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.tiagosilva.taskmanager.utils.Navigation

data class Task(val id: Int, val title: String, val subtitle: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val profileBtn = findViewById<ImageView>(R.id.profile)
        val listView = findViewById< ListView>(R.id.tasks)
        val fakeData = listOf(
            Task(1, "Task 1", "Description 1"),
            Task(2, "Task 2", "Description 2"),
            Task(3, "Task 3", "Description 3"),
            Task(4, "Task 4", "Description 4"),
            Task(5, "Task 5", "Description 5"),
            Task(6, "Task 6", "Description 6"),
            Task(7, "Task 7", "Description 7"),
            Task(8, "Task 8", "Description 8"),
            Task(9, "Task 9", "Description 9"),
            Task(10, "Task 10", "Description 10"),
            Task(11, "Task 11", "Description 11"),
            Task(12, "Task 12", "Description 12"),
            Task(13, "Task 13", "Description 13"),
            Task(14, "Task 14", "Description 14"),
            Task(15, "Task 15", "Description 15"),
            Task(16, "Task 16", "Description 16"),
            Task(17, "Task 17", "Description 17"),
            Task(18, "Task 18", "Description 18"),
            Task(19, "Task 19", "Description 19"),
            Task(20, "Task 20", "Description 20"),
        )
        val extractTitles = fakeData.map { it.title }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, extractTitles)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val task = fakeData[position]
            Toast.makeText(this, "Task ${task.id} clicked", Toast.LENGTH_SHORT).show()
            Navigation.goToScreen(this, TaskActivity::class.java)
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val task = fakeData[position]
            Toast.makeText(this, "Task ${task.id} long deleted", Toast.LENGTH_SHORT).show()
            true
        }

        fabAddTask.setOnClickListener {
            Navigation.goToScreen(this, TaskActivity::class.java)
        }

        logoutBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

        profileBtn.setOnClickListener {
            Navigation.goToScreen(this, ProfileActivity::class.java)
        }

        val firebaseUser: Any? = null
        verifySession(firebaseUser)
    }

    private fun verifySession(firebaseUser: Any?) {
        if (firebaseUser == null) {
//            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }
}