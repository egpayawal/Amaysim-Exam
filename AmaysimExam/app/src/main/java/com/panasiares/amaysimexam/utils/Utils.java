package com.panasiares.amaysimexam.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Pattern GLOBAL_EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean isEmailValid(final String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        Matcher match = GLOBAL_EMAIL_PATTERN.matcher(email.replace(" ", ""));
        return match.matches();
    }

    private static final long  MEGABYTE = 1024L * 1024L;
    private static final long  GIGABYTE = 1024L * MEGABYTE;

    public static String megaToGigaBytes(String megabytes) {
        if (megabytes == null || megabytes.equals("null")) {
            return "not specified";
        }
        long tempMega = Long.parseLong(megabytes);
        long tempMegaToBytes = tempMega * MEGABYTE;
        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);
        return nf.format(tempMegaToBytes/GIGABYTE).concat("GB");
    }

    public static String centToDollar(String cent) {
        if (cent == null || cent.equals("null")) {
            return "not specified";
        }
        long tempCent = Long.parseLong(cent);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        return nf.format(tempCent / 100.0);
    }
}
