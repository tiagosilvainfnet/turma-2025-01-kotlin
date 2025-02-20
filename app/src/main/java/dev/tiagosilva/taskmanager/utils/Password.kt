package dev.tiagosilva.taskmanager.utils

import android.content.res.Resources

class Password {
    companion object {
        val regexLetterAndNumber1 = Regex("^(?=.*[a-z])(?=.*\\d).+$")
        val regexLetterAndNumber2 = Regex("^(?=.*[A-Z])(?=.*\\d).+$")
        val regexLetterLowerAndUpperAndNumber = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
        val regexLetterLowerAndUpperAndNumberAndSpecial = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>]).+$")


        fun verifyPasswordDificult(text: String): Int {
            if(text.length == 0) {
                return 0
            }
            else if(text.length < 8) {
                return 2
            }
            else if(regexLetterLowerAndUpperAndNumberAndSpecial.matches(text)) {
                return 5;
            }
            else if(regexLetterLowerAndUpperAndNumber.matches(text)) {
                return 4;
            }
            else if(regexLetterAndNumber1.matches(text) || regexLetterAndNumber2.matches(text)) {
                return 3;
            }
            else {
                return 1;
            }

        }
    }
}