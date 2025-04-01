package dev.tiagosilva.taskmanager.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.util.Locale

class ErrorHandle {
    companion object {
        val uid = FirebaseAuth.getInstance().currentUser?.uid;

        fun configureCrashlytics(page: String): FirebaseCrashlytics {
            // Configure Crashlytics
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCustomKey("AppVersion", "0.0.1")


            val currentLocale = Locale.getDefault().toString()
            crashlytics.setCustomKey("AppLocale", currentLocale);
            crashlytics.setCustomKey("UserId", uid.toString());

            crashlytics.setUserId(uid.toString())
            crashlytics.setCustomKey("page", page)
            return crashlytics;
        }

        fun handleException(page: String, message: String) {
            val crashlytics = configureCrashlytics(page)
            crashlytics.recordException(Exception(message))
            crashlytics.log(message)
        }
    }
}