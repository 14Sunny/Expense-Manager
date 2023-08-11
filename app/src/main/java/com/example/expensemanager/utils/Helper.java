package com.example.expensemanager.utils;

import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {
    public static String  formatDate(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM, YYYY");
        return dateFormat.format(date);
    }

    public static String  formatByMonth(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM, YYYY");
        return dateFormat.format(date);
    }

    public static String formatRupee(Double value){

        String rs= NumberFormat.getCurrencyInstance(new Locale("en","in")).format(value);
        return rs;
    }

}
