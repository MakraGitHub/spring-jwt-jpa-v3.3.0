package co.prime.jpa;

import co.prime.jpa.entity.Book;
import co.prime.jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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
    public void run(String... args) {
        /*Book book = new Book();
        book.setTitle("Java");
        bookRepository.save(book);

        Book book1 = new Book();
        book1.setTitle("JavaScript");
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Spring");
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Cloud");
        bookRepository.save(book3);*/

       /*List<Book> bookList = bookRepository.findByTitleIsContainingIgnoreCase("A");
       bookList.forEach(book -> System.out.println(bookList));*/

        /*List<Book> bookList = bookRepository.findByTitleIsStartingWithIgnoreCase("A");
        for (Book book : bookList) {
            System.out.println(book);
        }*/

        var bookList = bookRepository.selectByAuthorName("Makara");
        bookList.forEach(book -> System.out.println(bookList));

    }
}
