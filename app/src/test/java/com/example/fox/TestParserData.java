package com.example.fox;

import com.example.fox.mode.DataList;
import com.example.fox.mode.ListData;
import com.example.fox.mode.TestDetail;
import com.example.fox.model.DataResult;
import com.example.fox.utils.GenericUtil;
import com.example.fox.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by magicfox on 2017/5/24.
 */

public class TestParserData extends BasePrint{

    @Test
    public void testParserSingleModel(){
        final String result = "{code:\"123\",message:\"hello\",detail:{code:\"test\",name=\"数据\"}}";
        DataResult<TestDetail> data = JsonUtil.fromJson(result,new TypeToken<DataResult<TestDetail>>(){}.getType());
        Assert.assertNotNull(data);
        Assert.assertNotNull(data.detail);
        println(data.detail.toString());

        DataResult dataResult = JsonUtil.fromJson2(result,DataResult.class);
        Assert.assertNotNull(dataResult);

        DataResult<TestDetail> dataResult1 = JsonUtil.fromJson2(result,DataResult.class);
        Assert.assertNotNull(dataResult1);
    }


    @Test
    public void testParserListModel(){
        final String result = "{code:\"123\",message:\"hello\",detail:[{code:\"test\"},{code:\"test1\"}]}";
        DataResult<List<TestDetail>> data = JsonUtil.fromJson(result,new TypeToken<DataResult<List<TestDetail>>>(){}.getType());
        Assert.assertNotNull(data);
        println(data.message);
        Assert.assertNotNull(data.detail);

        DataResult data2 = JsonUtil.fromJson2(result,DataResult.class);
        Assert.assertNotNull(data2);
        println(data2.code);

        DataResult<List<TestDetail>> data3 = JsonUtil.fromJson2(result,DataResult.class);
        Assert.assertNotNull(data3);
        Assert.assertNotNull(data3.detail);
    }

    @Test
    public void testParserListModel2(){
        final String result = "{code:\"123\",message:\"hello\",detail:{dataList:[{code:\"test\",name=\"数据\"},{code:\"test1\",name=\"数据2\"}]}}";
        DataResult<ListData> data1 =  JsonUtil.fromJson(result, new TypeToken<DataResult<ListData>>(){}.getType());
        Assert.assertNotNull(data1);

        DataResult<DataList<List<TestDetail>>> data = JsonUtil.fromJson(result, new TypeToken<DataResult<DataList<List<TestDetail>>>>(){}.getType());
        Assert.assertNotNull(data);
        Assert.assertNotNull(data.detail);
        Assert.assertNotNull(data.detail.dataList);
        if(!GenericUtil.isEmpty(data.detail.dataList)){
            println(data.detail.dataList.get(0).toString());
        }

    }

}
