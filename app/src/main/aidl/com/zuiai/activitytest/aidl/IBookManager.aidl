// IBookManager.aidl
package com.zuiai.activitytest.aidl;

// Declare any non-default types here with import statements

import com.zuiai.activitytest.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
