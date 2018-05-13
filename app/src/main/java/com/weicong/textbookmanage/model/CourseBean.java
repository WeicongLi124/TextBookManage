package com.weicong.textbookmanage.model;

/**
 * @author: Frank
 * @time: 2018/4/21 19:48
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class CourseBean {
    private String courseId;
    private String courseName;
    private String courseType;
    private int courseCredit;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }
}
