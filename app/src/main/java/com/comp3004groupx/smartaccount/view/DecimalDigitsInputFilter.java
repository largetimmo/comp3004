package com.comp3004groupx.smartaccount.view;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by wuguanhong on 2017-10-24.
 */

public class DecimalDigitsInputFilter implements InputFilter {
    Pattern mPattern;
    public DecimalDigitsInputFilter(int digitsAfterZero) {
        mPattern= Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }
}