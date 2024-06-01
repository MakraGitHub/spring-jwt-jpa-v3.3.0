package co.prime.jpa;

import co.prime.jpa.entity.Book;
import co.prime.jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringJwtJpaApplication implements CommandLineRunner {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book();
        book.setTitle("Java");
        bookRepository.save(book);
    }
}
