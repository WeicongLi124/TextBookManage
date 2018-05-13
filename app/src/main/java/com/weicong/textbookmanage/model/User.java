package com.weicong.textbookmanage.model;

/**
 * @author: Frank
 * @time: 2018/4/22 20:53
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class User {
    public static String USER_ID = null;
    public static String USER_GRADE = null;
    public static String USER_NAME = null;
    public static String USER_STATUS = null;

    public static String[] faculty = {"计算机工程学院","土木工程学院","管理学院","外国语学院"};
    public static String[][] grade = {
            {"软件工程1班","软件工程2班","计科1班","信科1班","网络工程1班","网络工程2班"},
            {"土木工程1班","土木工程2班","水利工程1班","水利工程2班"},
            {"会计学1班","会计学2班","工商管理1班","市场营销1班"},
            {"英语专业1班","英语专业2班","日语专业1班","日语专业2班"}};
    public static String[] courseNameList = {"计算机网络","软件工程","计算机组成原理","线性代数","概率论","大学英语",
            "计算机英语","马克思主义哲学","C语言程序设计","PS设计基础","基础日语"};
    public static String[] courseIdList = {"01","02","03","04","05","06","07","08","09","10","11"};
}
