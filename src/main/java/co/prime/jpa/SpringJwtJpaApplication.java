package co.prime.jpa;

import co.prime.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJwtJpaApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtJpaApplication.class, args);
    }


  /*  private BookRepository bookRepository;
    private BookService service;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Autowired
    public void setService(BookService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtJpaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        *//*Book book = new Book();
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
        bookRepository.save(book3);*//*

       *//*List<Book> bookList = bookRepository.findByTitleIsContainingIgnoreCase("A");
       bookList.forEach(book -> System.out.println(bookList));*//*

        *//*List<Book> bookList = bookRepository.findByTitleIsStartingWithIgnoreCase("A");
        for (Book book : bookList) {
            System.out.println(book);
        }*//*

     *//*   var bookList = bookRepository.selectByAuthorName("Makara");
        bookList.forEach(book -> System.out.println(bookList));*//*

        Book bookKey = bookRepository.selectByPrimary(UUID
                        .fromString("ddf19e8b-e9ef-4d9e-b09f-9876c7a0daf1")).orElseThrow();
        System.out.println(bookKey);


        service.deleteBookByStatus(false);*/


}
