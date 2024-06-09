package co.prime.jpa;

import co.prime.jpa.entity.Book;
import co.prime.jpa.repository.BookRepository;
import co.prime.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJwtJpaApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtJpaApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(userRepository.findByEmail("makaraDev@gmail.com"));

        Book book = new Book();
        book.setTitle("The lord of the Ring");
        book.setAuthor("LukaMo");
        book.setStatus(true);
        bookRepository.save(book);

    }
}
