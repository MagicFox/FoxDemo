package com.fox.datepicker.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;


import com.blankj.utilcode.utils.SizeUtils;
import com.fox.datepicker.picker.DatePicker;
import com.fox.datepicker.picker.WheelView;

import java.util.Calendar;


/**
 * Created by MagicFox on 2017/4/4.
 */

public class ShowDateUtil {
    static class ShowDateUtilHolder {
        static ShowDateUtil instance = new ShowDateUtil();
    }

    Calendar calendar;

    private ShowDateUtil() {
        calendar = Calendar.getInstance();
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public static ShowDateUtil getInstance() {
        return ShowDateUtilHolder.instance;
    }

    public void show(Activity context,final TextView textView){
        show(context,textView,"");
    }

    public void show(Activity context, final TextView textView,String titleText){

        final DatePicker picker = new DatePicker(context);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        if(!TextUtils.isEmpty(titleText)){
            picker.setTitleText(titleText);
        }
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setTopPadding(SizeUtils.dp2px(context,20));
        picker.setRangeEnd(calendar.get(Calendar.YEAR)+50, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                textView.setText(String.format("%s-%s-%s",year,month,day));
            }
        });
        picker.setDividerColor(ExpandUtils.getColor(context, android.R.color.holo_green_dark));
        picker.setTextColor(ExpandUtils.getColor(context, android.R.color.black));
        picker.setCancelTextColor(ExpandUtils.getColor(context, android.R.color.black));
        picker.setSubmitTextColor(ExpandUtils.getColor(context, android.R.color.black));
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();

    }

}
