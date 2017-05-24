package com.example.fox.model;

/**
 * Created by elson on 2017/5/4
 */

public class DataResult<T> {

    /**
     * RUN_ERROR: 运行时出现异常
     * BIZ_ERROR：业务状态出现冲突，导致操作不成功！
     * OK：执行成功
     * CHECK_ERROR：检查输入进来的参数出现问题
     */
    public String code; //RUN_ERROR
    public String message;
    public String count;//数据的条数
    public T detail;

    @Override
    public String toString() {
        return "DataResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", count='" + count + '\'' +
                ", detail=" + detail +
                '}';
    }
}
