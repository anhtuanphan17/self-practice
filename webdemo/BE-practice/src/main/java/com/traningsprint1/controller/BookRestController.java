package com.traningsprint1.controller;

import com.traningsprint1.dto.BookDto;
import com.traningsprint1.models.Book;
import com.traningsprint1.models.Category;
import com.traningsprint1.models.ResponseObject;
import com.traningsprint1.service.IBookService;
import com.traningsprint1.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    IBookService iBookService;

    @Autowired
    ICategoryService iCategoryService;


    @GetMapping(value = "/list")
    public ResponseEntity<Page<Book>> listBook(@PageableDefault(value = 12)Pageable pageable, @RequestParam Optional<String> keyName, @RequestParam Optional<String> keyCategory){

            String keyNameValue = keyName.orElse("");
            String keyCategoryValue = keyCategory.orElse("");

            Page<Book> bookPage = this.iBookService.findAllBookByNameAndCategory(pageable,keyNameValue,keyCategoryValue);
            if(bookPage.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bookPage,HttpStatus.OK);
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getListCategory(){
        List<Category> categoryList = this.iCategoryService.getAllCategory();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseObject> createBook(@Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();

        if (bindingResult.hasFieldErrors()) {
            bindingResult
                    .getFieldErrors()
                    .stream()
                    .forEach(f -> errorMap.put(f.getField(), f.getDefaultMessage()));

            return new ResponseEntity<>(new ResponseObject<>(false, "failed!", errorMap, new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        Double price = Double.valueOf(bookDto.getPrice());
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
        Category category =  new Category();
        BeanUtils.copyProperties(bookDto.getCategoryDto(),category);
        book.setCategory(category);
        book.setPrice(price);
        book.setDeleteFlag(false);

        this.iBookService.save(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> book = this.iBookService.findBookById(id);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<ResponseObject> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto, BindingResult bindingResult){

        Map<String,String> errorMap = new HashMap<>();
        if(!this.iBookService.findBookById(id).isPresent()){
            return new ResponseEntity<>(new ResponseObject(false, "id is not exist", errorMap, new ArrayList<>()),  HttpStatus.BAD_REQUEST);
        }
        if(bindingResult.hasErrors()){
            bindingResult
                    .getFieldErrors()
                    .stream()
                    .forEach(f -> errorMap.put(f.getField(),f.getDefaultMessage()));

            return new ResponseEntity<>(new ResponseObject(false,"failed!",errorMap,new ArrayList()),HttpStatus.BAD_REQUEST);
        }
        Double price = Double.valueOf(bookDto.getPrice());
        Book book = new Book();
        BeanUtils.copyProperties(bookDto,book);
        Category category =  new Category();
        BeanUtils.copyProperties(bookDto.getCategoryDto(),category);
        book.setCategory(category);
        book.setPrice(price);
        this.iBookService.updateBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        Optional<Book> book = this.iBookService.findBookById(id);
        if (book.isPresent()){
           this.iBookService.deleteBookById(id);
           return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
