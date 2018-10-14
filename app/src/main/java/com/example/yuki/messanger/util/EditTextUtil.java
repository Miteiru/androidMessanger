package com.example.yuki.messanger.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextUtil {

    public static boolean isEmpty(String text){
        if (text.length() == 0 || text == null){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
