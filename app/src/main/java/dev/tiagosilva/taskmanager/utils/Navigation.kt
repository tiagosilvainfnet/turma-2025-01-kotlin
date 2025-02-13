package dev.tiagosilva.taskmanager.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class Navigation {
    companion object {
        fun goToScreen(context: Context, activity: Class<*>){
            val intent = Intent(context, activity);
            startActivity(context, intent, null);
        }
    }
}