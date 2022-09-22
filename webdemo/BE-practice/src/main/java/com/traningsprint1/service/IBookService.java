package com.traningsprint1.service;

import com.traningsprint1.dto.BookDto;
import com.traningsprint1.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/** IBookService
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface IBookService {

    void save(Book book);

    Optional<Book> findBookById(Long id);

    void deleteBookById(Long id);

    void updateBook(Book book);
    
    Page<Book> findAllBook(Pageable pageable, String keyNameValue);

    /** this findAllBookByNameAndCategory function is to find page of book by name and category
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    Page<Book> findAllBookByNameAndCategory(Pageable pageable, String keyNameValue, String keyCategoryValue);

    Book findBookByName(String inputName);

    void saveBookDto(BookDto bookDto);

    void updateBookDto(BookDto bookDto);
}
