package com.traningsprint1.controller;

import com.traningsprint1.dto.BookDto;
import com.traningsprint1.models.Book;
import com.traningsprint1.models.Category;
import com.traningsprint1.models.ResponseObject;
import com.traningsprint1.service.IBookService;
import com.traningsprint1.service.ICategoryService;
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

/** BookRestController is the class use for receiving request and sending respond data relating watch list book, create, edit, delete book
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */

@RestController
@CrossOrigin("*")
@RequestMapping("/api/book")
public class BookRestController {
    @Autowired
    IBookService iBookService;

    @Autowired
    ICategoryService iCategoryService;

    /**
     * This showListBook function is to show list of books and searched books
     * @param  keyName
     * @param  keyCategory
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @GetMapping(value = "/list")
    public ResponseEntity<Page<Book>> showListBook(@PageableDefault(value = 12)Pageable pageable,
                                                   @RequestParam Optional<String> keyName,
                                                   @RequestParam Optional<String> keyCategory){
            String keyNameValue = keyName.orElse("");
            String keyCategoryValue = keyCategory.orElse("");
            Page<Book> bookPage = this.iBookService.findAllBookByNameAndCategory(pageable,keyNameValue,keyCategoryValue);
            if(bookPage.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bookPage,HttpStatus.OK);
    }

    /**
     * This getListCategory function is to show list of all categories
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getListCategory(){
        List<Category> categoryList = this.iCategoryService.getAllCategory();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    /**
     * This createBook function is to show list of books and searched books
     * @param  bookDto
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseObject> createBook(@Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
            Map<String, String> errorMap = new HashMap<>();
            BookDto bookDtoErrors = new BookDto();
            bookDtoErrors.setIBookService(iBookService);
            bookDtoErrors.validate(bookDto, bindingResult);
            if (bindingResult.hasFieldErrors()) {
                bindingResult
                        .getFieldErrors()
                        .stream()
                        .forEach(f -> errorMap.put(f.getField(), f.getDefaultMessage()));

                return new ResponseEntity<>(new ResponseObject<>(false, "failed!", errorMap, new ArrayList<>()), HttpStatus.BAD_REQUEST);
            }

            this.iBookService.saveBookDto(bookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This findBookById function is to find book by id
     * @Param  id
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> book = this.iBookService.findBookById(id);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    /**
     * This updateBook function is to update edited book
     * @Param  id
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<ResponseObject> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto, BindingResult bindingResult){
        Map<String,String> errorMap = new HashMap<>();
//       check if book is not found in database with given param id
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
        this.iBookService.updateBookDto(bookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This deleteBookById function is to delete book by id
     * @Param  id
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
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
