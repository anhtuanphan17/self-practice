package com.traningsprint1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookDto implements Validator {
    private Long id;
    @NotBlank(message = "Book 's name could not be blank")
    @Length(max = 250)
    private String name;
    @NotBlank(message = "price could not be blank")
    @Pattern(regexp = "^('0'*[1-9][0-9]*)|([1-9][0-9]*)$", message = "Price must be greater than 0 and not be a negative number")
    private String price;
    private String image;
    private String description;
    private CategoryDto categoryDto;


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
