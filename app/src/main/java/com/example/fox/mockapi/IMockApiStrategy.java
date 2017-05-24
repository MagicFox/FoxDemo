package com.example.fox.mockapi;

/**
 * Created by magicfox on 2017/5/19.
 */

/**
 * Define the way to give Response for "one by one" requests,
 * Mock the real network access.
 */
public interface IMockApiStrategy {
    /**
     * Give mock response result.
     * @param callCount the count of request (DataApi  method) have been called, during once app running.
     * @param out the result, for performance....
     */
    void onResponse(int callCount,Response out);
    /**
     * Presents kinds of "Response results".
     */
    public static class Response{
        public static final int STATE_NETWORK_ERROR = 1;
        public static final int STATE_SERVER_ERROR = 2;
        public static final int STATE_SUCCESS = 3;

        public int state = STATE_SUCCESS;
        public int delayMillis = 600;
    }
}
