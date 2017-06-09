package com.fox.datepicker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fox.datepicker.picker.DateTimePicker;
import com.fox.datepicker.picker.LinkagePicker;
import com.fox.datepicker.picker.SinglePicker;
import com.fox.datepicker.picker.WheelView;
import com.fox.datepicker.picker.entity.Area;
import com.fox.datepicker.picker.entity.City;
import com.fox.datepicker.picker.entity.Province;
import com.fox.datepicker.utils.ExpandUtils;
import com.fox.datepicker.utils.GenericUtil;
import com.fox.datepicker.utils.ShowDateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView) findViewById(R.id.tvText);
        findViewById(R.id.btnClickDateTime).setOnClickListener(onClickListener);
        findViewById(R.id.btnClickDate).setOnClickListener(onClickListener);
        findViewById(R.id.btnClickAddress).setOnClickListener(onClickListener);
        findViewById(R.id.btnClickSingleData).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnClickDateTime:
                    showDateTime();
                    break;
                case R.id.btnClickDate:
                    ShowDateUtil.getInstance().show(MainActivity.this, tvText);
                    break;
                case R.id.btnClickAddress:
                    showThree();
                    break;
                case R.id.btnClickSingleData:
                    showSingle();
                    break;
            }
        }
    };



    private List<Province> getProvince(){

        List<Area> areaList = new ArrayList<>();
        Area area =new Area();
        area.id = "1111";
        area.name = "area111";
        area.type = "1111";
        areaList.add(area);

        area =new Area();
        area.id = "1122";
        area.name = "area122";
        area.type = "1122";
        areaList.add(area);

        List<City> cityList =new ArrayList<>();
        City city =new City();
        city.id = "11";
        city.name = "city111";
        city.type = "11";
        city.ares = areaList;
        cityList.add(city);

        city =new City();
        city.id = "22";
        city.name = "city122";
        city.type = "22";
        city.ares = areaList;
        cityList.add(city);


        List<Province> provinceList = new ArrayList<>();
        Province province = new Province();
        province.id = "1";
        province.name = "province111";
        province.type = "1";
        province.city = cityList;
        provinceList.add(province);
        return provinceList;
    }

    private void showSingle(){
        SinglePicker<Province> singlePicker = new SinglePicker<Province>(this,getProvince());

        singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<Province>() {
            @Override
            public void onItemPicked(int index, Province item) {
                if(item == null){
                    return;
                }
                tvText.setText(String.format("%s=%s",item.getName(),index));
            }
        });
        singlePicker.show();
    }

    private void showDateTime(){
        Calendar calendar=Calendar.getInstance();
        final DateTimePicker picker = new DateTimePicker(this, DateTimePicker.YEAR_MONTH_DAY,DateTimePicker.HOUR_24);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);

        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setTopPadding(SizeUtils.dp2px(this,20));
        picker.setDateRangeEnd(calendar.get(Calendar.YEAR)+50, calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setDateRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),1,1);

        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tvText.setText(String.format("%s-%s-%s:%s %s",year,month,day,hour,minute));
            }

        });
        picker.setDividerColor(ExpandUtils.getColor(this, android.R.color.holo_green_dark));
        picker.setTextColor(ExpandUtils.getColor(this, android.R.color.black));
        picker.setCancelTextColor(ExpandUtils.getColor(this, android.R.color.black));
        picker.setSubmitTextColor(ExpandUtils.getColor(this, android.R.color.black));
        picker.setOnWheelListener(new DateTimePicker.OnWheelListener() {
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

            @Override
            public void onHourWheeled(int index, String hour) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay()+"-"+hour+" "+picker.getSelectedMinute());
            }

            @Override
            public void onMinuteWheeled(int index, String minute) {

                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay()+"-"+picker.getSelectedHour()+" "+minute);
            }
        });
        picker.show();
    }

    private void showThree(){

        final List<Province> provinceList = getProvince();
        LinkagePicker.Provider<Province,City,Area> provider = new LinkagePicker.Provider<Province,City,Area>() {

            @Override
            public boolean isOnlyTwo() {
                return false;
            }

            @NonNull
            @Override
            public List<Province> initFirstData() {
                return provinceList;
            }

            @NonNull
            @Override
            public List<City> linkageSecondData(int firstIndex) {

                return GenericUtil.isEmpty(provinceList)?null:firstIndex>provinceList.size()?null:provinceList.get(firstIndex)==null?null:provinceList.get(firstIndex).city;
            }

            @NonNull
            @Override
            public List<Area> linkageThirdData(int firstIndex, int secondIndex) {

                return GenericUtil.isEmpty(provinceList)?null:
                        firstIndex>provinceList.size()?null:
                                provinceList.get(firstIndex)==null?null:
                                        provinceList.get(firstIndex).getSeconds()==null?null:
                                                secondIndex > provinceList.get(firstIndex).getSeconds().size()?null:
                                                        provinceList.get(firstIndex).getSeconds().get(secondIndex) == null?null:
                                                                provinceList.get(firstIndex).getSeconds().get(secondIndex).getThirds();
            }
        };
        LinkagePicker<Province,City,Area> cityAreaLinkagePicker = new LinkagePicker<Province, City, Area>(this,provider);
        cityAreaLinkagePicker.show();;
    }

}
