package com.fox.datepicker.picker.entity;


import com.fox.datepicker.picker.WheelItem;

/**
 *
 */
interface LinkageItem extends WheelItem {

    /**
     * 唯一标识，用于判断两个条目是否相同
     */
    Object getId();

}
