package co.prime.jpa.service;

import co.prime.jpa.entity.Book;
import co.prime.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository repository;
    @Override
    public void deleteBookByStatus(Boolean status) {
        repository.deleteByStatus(status);
    }
    @Override
    public List<Book> findAll() {
        return (List<Book>) repository.findAll();
    }
}
