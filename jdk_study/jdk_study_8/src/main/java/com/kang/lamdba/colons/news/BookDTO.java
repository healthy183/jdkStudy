package com.kang.lamdba.colons.news;

/**
 * User:
 * Description:
 * Date: 2022-09-10
 * Time: 16:43
 */
public class BookDTO {

    private  String bookName;

    public BookDTO(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookName='" + bookName + '\'' +
                '}';
    }
}
