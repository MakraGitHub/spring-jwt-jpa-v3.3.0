package co.prime.jpa.repository;

import co.prime.jpa.entity.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {

    List<Book> findByTitleIsContainingIgnoreCase(String title);
    List<Book> findByTitleIsStartingWithIgnoreCase(String title);

    @Query("select b from e_book b where b.author =?1")
    List<Book> selectByAuthorName(String name);

    @Query("select b from e_book b where b.uuid = ?1")
    Optional<Book> selectByPrimary(UUID primaryKey);

    //Make sure with transaction(Modifying)
    @Modifying
    @Query("delete from e_book  b where b.status = ?1")
    void deleteByStatus(Boolean status);
}
