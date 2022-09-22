package com.traningsprint1.dto;

import com.traningsprint1.models.Book;
import com.traningsprint1.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookDto implements Validator {
    private Long id;
    @NotBlank(message = "Book's name could not be blank")
    @Length(max = 250)
    private String name;
    @NotBlank(message = "price could not be blank")
    @Pattern(regexp = "^('0'*[1-9][0-9]*)|([1-9][0-9]*)$", message = "Price must be greater than 0 and not be a negative number")
    private String price;
    private String image;
    private String description;
    private CategoryDto categoryDto;
    private IBookService iBookService;

    /**
     * This Override validate function is to validate the case of client input same name which already exist in database.
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    /**
     * This Override validate function is to validate the case of client input same name which already exist in database.
     * @Param  target
     * @Param  errors
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @Override
    public void validate(Object target, Errors errors) {
        BookDto bookDto  = (BookDto) target;
        String inputName =  bookDto.getName();
        Book book = this.iBookService.findBookByName(inputName);
        if(book != null){
            if(book.getName().equalsIgnoreCase(inputName)){
                errors.rejectValue("name", "repeatedName","Book's name have already exists");
            }
        }

    }
}
