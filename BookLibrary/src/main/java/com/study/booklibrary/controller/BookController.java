package com.study.booklibrary.controller;

import com.study.booklibrary.domain.Book;
import com.study.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookRepository repository;

    @GetMapping("/books")
    public List<Book> list() {
        Iterable<Book> bookIterable = repository.findAll();
        return (List<Book>) bookIterable;
    }

    @PostMapping("/books")
    public int add(@RequestBody Book book) {
        Book responseBook = repository.save(book);
        return responseBook.getId();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBook(@PathVariable int id){
        Optional<Book> optionalBook = repository.findById(id);
        if(!optionalBook.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalBook, HttpStatus.OK);
    }
}
