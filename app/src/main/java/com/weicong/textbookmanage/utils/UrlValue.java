package com.weicong.textbookmanage.utils;

/**
 * @author: Frank
 * @time: 2018/4/13 16:56
 * @e-mail: 912220261@qq.com
 * Function: 服务器url等
 */
public class UrlValue {
    public static final int MSG_OK = 1;
    public static final int MSG_ERROR = 0;
    public static String ENCODING = "application/json; charset=utf-8";

    public static String SERVICE = "http://10.11.3.209:8080/";

    /**
     * 注册
     */
    public static String REGISTER = "register";

    /**
     * 登陆
     */
    public static String LOGIN = "login";

    /**
     * 获取课程列表
     */
    public static String GET_COURSE_LIST = "getCourseList";

    /**
     * 课程录入
     */
    public static String INSERT_COURSE = "insertCourse";

    /**
     * 课程搜索
     */
    public static String SEARCH_COURSE = "searchCourse";

    /**
     * 教材录入
     */
    public static String INSERT_BOOK = "insertBook";

    /**
     * 获取教材列表
     */
    public static String SEARCH_BOOK = "searchBook";

    /**
     * 删除教材数据
     */
    public static String DELETE_BOOK = "deleteBook";

    /**
     * 修改教材数据
     */
    public static String UPDATE_BOOK = "updateBook";

    /**
     * 获取订单列表
     */
    public static String SEARCH_ORDER = "searchOrder";

    /**
     * 订单录入
     */
    public static String INSERT_ORDER = "insertOrder";
}
