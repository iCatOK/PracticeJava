package com.study.booklibrary.service;

import com.study.booklibrary.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static ArrayList<Book> books = new ArrayList<>();
    private static int currentBook = 1;

    public static List<Book> getAllBooks(){
        return books;
    }

    public static int addBook(Book book){
        int id = currentBook++;
        book.setId(id);
        books.add(book);
        return id;
    }

    public static int addBook(String name, int year){
        Book book = new Book();
        int id = currentBook++;
        book.setId(id);
        book.setName(name);
        book.setYear(year);
        books.add(book);
        return id;
    }

}
