package co.prime.jpa.repository;

import co.prime.jpa.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {

    List<Book> findByTitleIsContainingIgnoreCase(String title);
    List<Book> findByTitleIsStartingWithIgnoreCase(String title);

    @Query("select b from e_book b where b.author =:name")
    List<Book> selectByAuthorName(String name);

}
