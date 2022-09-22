package com.traningsprint1.repository;

import com.traningsprint1.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * IBookRepository
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO book (`name`,`price`,`image`,`description`,`delete_flag`,`category_id`) " +
            "VALUES (:#{#book.name}, :#{#book.price}, :#{#book.image}, :#{#book.description}, :#{#book.deleteFlag}, :#{#book.category.id});", nativeQuery = true)
    void createBook(Book book);

    @Query(value = "SELECT * FROM book WHERE delete_flag = 0 and id = :id", nativeQuery = true)
    Optional<Book> findBookById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book SET delete_flag = 1 WHERE book.id = :id", nativeQuery = true)
    void deleteBookById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book SET name = :#{#book.name}, price = :#{#book.price}, image = :#{#book.image}, description = :#{#book.description}, category_id = :#{#book.category.id}" +
            " WHERE id = :#{#book.id}", nativeQuery = true)
    void updateBook(Book book);

    @Query(value = "SELECT id,`name`,price,image,`description`,category_id,delete_flag FROM book" +
            " WHERE delete_flag = 0 and `name` like concat ('%',:name,'%')",
            countQuery = "SELECT id,`name`,price,image,`description`,category_id,delete_flag FROM book" +
                    " WHERE delete_flag = 0 and `name` like concat ('%',:name,'%')", nativeQuery = true)
    Page<Book> findAllBook(Pageable pageable, @Param("name") String keyNameValue);

    /** this findAllBookByNameAndCategory function is to find page of book by name and category
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Query(value = "SELECT book.id,`description`,image,`name`, price,book.category_id, book.delete_flag FROM book\n" +
            "join category on book.category_id = category.id\n" +
            "where category_name like concat('%',:category,'%') and `name` like concat('%',:name,'%') and delete_flag = 0 order by id asc",
            countQuery = "SELECT book.id,`description`,image,`name`, price,book.category_id, book.delete_flag FROM book\n" +
                    "join category on book.category_id = category.id\n" +
                    "where category_name like concat('%',:category,'%') and `name` like concat('%',:name,'%') and delete_flag = 0 order by id asc"
            , nativeQuery = true)
    Page<Book> findAllByNameAndCategory(Pageable pageable, @Param("name") String keyNameValue, @Param("category") String keyCategoryValue);

    Book findBookByName(String inputName);
}



