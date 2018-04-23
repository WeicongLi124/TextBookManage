package com.weicong.textbookmanage.model;

/**
 * @author: Frank
 * @time: 2018/4/21 19:48
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class CourseBean {
    private String grade;
    private String courseName;
    private String teacherName;
    private String type;
    private int credit;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
