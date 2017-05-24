package com.example.fox.http;



/**
 * Created by MagicFox on 2017/3/23.
 */

public interface Api {

    String HOST = "http://172.16.224.246:8088/";
    String ImageUrl = String.format("%s%s",HOST,"JointDelivery");
    String BASE_URL = String.format("%s%s",HOST,"JointDelivery/trucker/");
    // 登录
    String LOGIN = "login/load";


    // 查询司机银行卡列表
    String QUERY_BANKCARD_LIST = "bankCard/queryBankCardList";
}
