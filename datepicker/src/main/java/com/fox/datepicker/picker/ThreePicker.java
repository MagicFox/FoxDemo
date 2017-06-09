package com.fox.datepicker.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


import com.fox.datepicker.R;
import com.fox.datepicker.utils.ExpandUtils;
import com.fox.datepicker.utils.GenericUtil;

import java.util.List;

/**
 * Created by magicfox on 2017/6/7.
 */

public abstract class ThreePicker<T extends WheelItem> extends WheelPicker {


    OnThreeWheelListener onThreeWheelListener;
    public void setOnThreeWheelListener(OnThreeWheelListener onThreeWheelListener){
        this.onThreeWheelListener = onThreeWheelListener;
    }

    OnThreeWheelPickListener onThreeWheelPickListener;
    public void setOnSubmit(OnThreeWheelPickListener onSubmit) {
        this.onThreeWheelPickListener = onSubmit;
    }

    List<T> provinceList, cityList, areList;
    WheelView provinceWheelView, cityWheelView, areWheelView;
    int provinceSelectIndex, citySelectIndex, areSelectIndex;
    private boolean useWeight = false;

    public ThreePicker(Activity activity) {
        super(activity);
    }

    protected abstract List<T> getProvinceList();

    protected abstract List<T> getCityList(int selected,String code);

    protected abstract List<T> getAreList(int areSelectIndex,String code);

    @NonNull
    @Override
    protected View makeCenterView() {
        provinceList = getProvinceList();
        if (!GenericUtil.isEmpty(provinceList)) {
            cityList = getCityList(provinceSelectIndex,provinceList.get(0).getKeyCode());
        }
        if (!GenericUtil.isEmpty(cityList)) {
            areList = getAreList(citySelectIndex,cityList.get(0).getKeyCode());
        }
        int textSize = 12;
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        final WheelView provinceWheelView = createWheelView();
        final WheelView cityWheelView = createWheelView();
        final WheelView areWheelView = createWheelView();

        provinceWheelView.setGravity(Gravity.CENTER);
        cityWheelView.setGravity(Gravity.CENTER);
        areWheelView.setGravity(Gravity.CENTER);
        int colorNormal = ExpandUtils.getColor(activity,R.color.light_grey_line);
        int colorSelected = ExpandUtils.getColor(activity, R.color.black);

        provinceWheelView.setTextColor(colorNormal,colorSelected);
        cityWheelView.setTextColor(colorNormal,colorSelected);
        areWheelView.setTextColor(colorNormal,colorSelected);

        provinceWheelView.setTextSize(textSize);
        cityWheelView.setTextSize(textSize);
        areWheelView.setTextSize(textSize);

        provinceWheelView.setUseWeight(useWeight);
        cityWheelView.setUseWeight(useWeight);
        areWheelView.setUseWeight(useWeight);


        provinceWheelView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
        provinceWheelView.setItems(provinceList, provinceSelectIndex);
        provinceWheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                provinceSelectIndex = index;
                citySelectIndex = 0;
                areSelectIndex = 0;
                if(GenericUtil.isEmpty(provinceList) || provinceSelectIndex > provinceList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onFirstWheel(index, getSelectedFirst());
                }

                cityList = getCityList(provinceSelectIndex,provinceList.get(provinceSelectIndex).getKeyCode());
                cityWheelView.setItems(cityList, citySelectIndex);
                if(GenericUtil.isEmpty(cityList) || citySelectIndex > cityList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onSecondWheel(citySelectIndex, getSelectedSecond());
                }

                changeThird(citySelectIndex,cityList.get(citySelectIndex).getKeyCode());
                areWheelView.setItems(areList, areSelectIndex);
                if(GenericUtil.isEmpty(areList) || areSelectIndex > cityList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onThirdWheel(areSelectIndex, getSelectedThird());
                }
            }
        });
        layout.addView(provinceWheelView);


        cityWheelView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
        cityWheelView.setItems(cityList, citySelectIndex);
        cityWheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                citySelectIndex = index;
                areSelectIndex = 0;
                if(GenericUtil.isEmpty(cityList) || citySelectIndex > cityList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onSecondWheel(index, getSelectedSecond());
                }

                changeThird(citySelectIndex,cityList.get(citySelectIndex).getKeyCode());
                areWheelView.setItems(areList, areSelectIndex);
                if(GenericUtil.isEmpty(areList) || areSelectIndex > areList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onThirdWheel(areSelectIndex, getSelectedThird());
                }
            }
        });
        layout.addView(cityWheelView);

        areWheelView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
        areWheelView.setItems(areList, areSelectIndex);
        areWheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                areSelectIndex = index;
                if(GenericUtil.isEmpty(areList) || index > areList.size()){
                    return;
                }
                if (onThreeWheelListener != null) {
                    onThreeWheelListener.onThirdWheel(index, getSelectedThird());
                }
            }
        });
        layout.addView(areWheelView);

        return layout;
    }

    /**
     * 是否使用比重来平分布局
     */
    public void setUseWeight(boolean useWeight) {
        this.useWeight = useWeight;
    }

    public T getFirstSelectedItem(){
        return GenericUtil.isEmpty(provinceList)?null:provinceSelectIndex > provinceList.size() ? null : provinceList.get(provinceSelectIndex);
    }

    public T getSecondSelectedItem(){
        return GenericUtil.isEmpty(cityList) ? null : citySelectIndex > cityList.size()? null : cityList.get(citySelectIndex);
    }

    public T getThirdSelectedItem(){
        return GenericUtil.isEmpty(areList) ? null : areSelectIndex > areList.size()? null : areList.get(areSelectIndex);
    }

    public String getSelectedFirst(){
        return getFirstSelectedItem() == null?"":getFirstSelectedItem().getName();
    }

    public String getSelectedSecond(){
        return getSecondSelectedItem() == null?"":getSecondSelectedItem().getName();
    }

    public String getSelectedThird(){
        return getThirdSelectedItem() == null?"":getThirdSelectedItem().getName();
    }

    @Override
    protected void onSubmit() {
        if(onThreeWheelPickListener!=null){
            onThreeWheelPickListener.submit(getFirstSelectedItem(),getSecondSelectedItem(),getThirdSelectedItem());
        }
    }

    private void changeThird(int selectedCityIndex, String keyCode) {
        areList = getAreList(selectedCityIndex,keyCode);
    }

    public interface OnThreeWheelListener {
        void onFirstWheel(int select, String t);
        void onSecondWheel(int select, String t);
        void onThirdWheel(int select, String t);
    }

    public interface OnThreeWheelPickListener<T extends WheelItem> {
        void submit(T first, T second, T third);
    }

}
