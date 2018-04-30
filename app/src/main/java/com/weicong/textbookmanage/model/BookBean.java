package com.weicong.textbookmanage.model;

import java.io.Serializable;

/**
 * @author: Frank
 * @time: 2018/4/27 19:36
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class BookBean implements Serializable {
    private String bookISBN;
    private String bookName;
    private String bookPress;
    private String bookAuthor;
    private double bookPrice;

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
}
