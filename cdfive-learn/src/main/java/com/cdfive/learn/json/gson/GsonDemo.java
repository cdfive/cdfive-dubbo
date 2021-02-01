package com.cdfive.learn.json.gson;

import com.google.gson.Gson;

/**
 * @author cdfive
 */
public class GsonDemo {

    public static void main(String[] args) {
        Gson gson = new Gson();

        System.out.println(gson.toJson(null)); // null
        System.out.println("null".equals(gson.toJson(null))); // true

        String s = null;
        System.out.println(gson.toJson(s)); // null
        System.out.println("null".equals(gson.toJson(s))); // true

        s = "test";
        System.out.println(gson.toJson(s)); // "test"
        System.out.println("test".equals(gson.toJson(s))); // false
        System.out.println("\"test\"".equals(gson.toJson(s))); // true

        System.out.println("null".equals(null)); // false
    }
}
