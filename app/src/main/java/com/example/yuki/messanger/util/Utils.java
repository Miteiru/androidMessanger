package com.example.yuki.messanger.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    //Hide virtual keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static int SIGN_IN_REQUEST_CODE = 1;
    public static final String EXTRA_DATA = "extraData";


}
