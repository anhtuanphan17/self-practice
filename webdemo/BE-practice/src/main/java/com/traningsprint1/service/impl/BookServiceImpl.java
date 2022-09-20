package com.traningsprint1.service.impl;

import com.traningsprint1.models.Book;
import com.traningsprint1.repository.IBookRepository;
import com.traningsprint1.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookRepository iBookRepository;

    @Override
    public void save(Book book) {
        this.iBookRepository.createBook(book);
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return this.iBookRepository.findBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        this.iBookRepository.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        this.iBookRepository.updateBook(book);
    }

    @Override
    public Page<Book> findAllBook(Pageable pageable, String keyNameValue) {
        return this.iBookRepository.findAllBook(pageable,keyNameValue);
    }

    @Override
    public Page<Book> findAllBookByNameAndCategory(Pageable pageable, String keyNameValue,String keyCategoryValue) {
        return this.iBookRepository.findAllByNameAndCategory(pageable,keyNameValue,keyCategoryValue);
    }


}
