package com.example.fox.mockapi;

/**
 * Created by magicfox on 2017/5/19.
 */

public class WheelApiStrategy implements IMockApiStrategy {
    @Override
    public void onResponse(int callCount, Response out) {
        if(out == null)return;
        int step = callCount % 10;
        switch (step){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                out.state = Response.STATE_SUCCESS;
                break;
            case 5:
                out.state = Response.STATE_SERVER_ERROR;
                break;
            case 6:
                out.state = Response.STATE_SUCCESS;
                break;
            case 7:
                out.state = Response.STATE_NETWORK_ERROR;
                break;
        }
        out.delayMillis = 700;
    }
}
