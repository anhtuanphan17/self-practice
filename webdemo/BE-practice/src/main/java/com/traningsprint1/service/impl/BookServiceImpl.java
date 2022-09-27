package com.traningsprint1.service.impl;

import com.traningsprint1.dto.BookDto;
import com.traningsprint1.models.Book;
import com.traningsprint1.models.Category;
import com.traningsprint1.repository.IBookRepository;
import com.traningsprint1.service.IBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** BookServiceImpl
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
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

    /** this findAllBookByNameAndCategory function is find page of book by name and category
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    public Page<Book> findAllBookByNameAndCategory(Pageable pageable, String keyNameValue,String keyCategoryValue) {
        return this.iBookRepository.findAllByNameAndCategory(pageable,keyNameValue,keyCategoryValue);
    }

    /** this findBookByName function is find book by given param name
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    public Book findBookByName(String inputName) {
        return this.iBookRepository.findBookByName(inputName);
    }

    /** this saveBookDto function is to convert object bookDto become object book and save object book to database
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    public void saveBookDto(BookDto bookDto) {
        Book book = new Book();
        Double price = Double.valueOf(bookDto.getPrice());
        BeanUtils.copyProperties(bookDto, book);
        Category category = new Category();
        BeanUtils.copyProperties(bookDto.getCategoryDto(), category);
        book.setCategory(category);
        book.setPrice(price);
        book.setDeleteFlag(false);
        iBookRepository.save(book);
    }

    /** this updateBookDto function is to convert object bookDto become object book and update object book to database
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    public void updateBookDto(BookDto bookDto) {
        Book book = new Book();
        Double price = Double.valueOf(bookDto.getPrice());
        BeanUtils.copyProperties(bookDto,book);
        Category category =  new Category();
        BeanUtils.copyProperties(bookDto.getCategoryDto(),category);
        book.setCategory(category);
        book.setPrice(price);
        this.iBookRepository.updateBook(book);
    }


}
