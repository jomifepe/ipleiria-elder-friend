package com.example.tsngapp.ui.chart;

import android.annotation.SuppressLint;

import com.example.tsngapp.helpers.Constants;
import com.example.tsngapp.helpers.DateUtil;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAxisFormatter extends ValueFormatter {

    @SuppressLint("SimpleDateFormat")

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
//        Date date = new Date();
//        try {
//            String strDate = String.valueOf(value);
//            date = DateUtil.getDateFromString(strDate, "HHmmss");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return DateUtil.getStringFromDate(date, Constants.FULL_TIME_FORMAT);

        return String.valueOf(value);
    }
}
