package dev.tiagosilva.taskmanager

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.tiagosilva.taskmanager.utils.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        val logoutBtn = findViewById<ImageView>(R.id.logout);
        val profileBtn = findViewById<ImageView>(R.id.profile)

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