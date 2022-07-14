package com.github.astafex.BookLibrary.util;

import com.github.astafex.BookLibrary.dao.BookDAO;
import com.github.astafex.BookLibrary.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Override
    public boolean supports(Class<?> currentClass) {
        return Book.class.equals(currentClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;
        if (bookDAO.show(book.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием уже есть!");
        }
    }
}
