package co.prime.jpa.service;

import co.prime.jpa.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    void deleteBookByStatus(Boolean status);
}
