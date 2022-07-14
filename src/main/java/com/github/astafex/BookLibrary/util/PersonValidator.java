package com.github.astafex.BookLibrary.util;

import com.github.astafex.BookLibrary.dao.PersonDAO;
import com.github.astafex.BookLibrary.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> currentClass) {
        return Person.class.equals(currentClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Person person = (Person) object;
        if (personDAO.show(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Такое имя уже существует!");
        }
    }
}
