package com.traningsprint1.service;

import com.traningsprint1.dto.IBookDto;
import com.traningsprint1.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    void save(Book book);

    Optional<Book> findBookById(Long id);

    void deleteBookById(Long id);

    void updateBook(Book book);


    Page<Book> findAllBook(Pageable pageable, String keyNameValue);


    Page<Book> findAllBookByNameAndCategory(Pageable pageable, String keyNameValue, String keyCategoryValue);
}
