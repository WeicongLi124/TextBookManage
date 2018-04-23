package com.weicong.textbookmanage.utils;

/**
 * @author: Frank
 * @time: 2018/4/13 16:56
 * @e-mail: 912220261@qq.com
 * Function: 服务器url等
 */
public class UrlValue {
    public static final int MSG_OK = 0;
    public static final int MSG_ERROR = 1;
    public static String ENCODING = "application/json; charset=utf-8";

    private static String SERVICE = "http://10.11.3.209:8080/";

    public static String REGISTER = SERVICE + "register";

    public static String LOGIN = SERVICE + "login";

    public static String GET_COURSE_LIST = SERVICE + "getCourseList";

    public static String INSERT_COURSE = SERVICE + "insertCourse";
}
