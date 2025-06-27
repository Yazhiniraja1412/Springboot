package com.example.bookapi.service;

import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return repository.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }

    public Book updateBook(String isbn, Book book) {
        Book existing = getBookByIsbn(isbn);
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublicationYear(book.getPublicationYear());
        return repository.save(existing);
    }

    public void deleteBook(String isbn) {
        repository.deleteById(isbn);
    }
}
