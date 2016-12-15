package com.example.gyx.jixiezulin.Common;

import android.content.Context;
import android.media.MediaCodec;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.gyx.jixiezulin.R;
import com.example.gyx.jixiezulin.widget.SoftKeyboardStateHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by gyx on 2016-12-10.
 */

public class CommonUtil {

    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context,View view){
        if (context!=null&&view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     *验证手机号是否合理
     */
    public static boolean isMobileNum(String input){
        String regex="1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

}
