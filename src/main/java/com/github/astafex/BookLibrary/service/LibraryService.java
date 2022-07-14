package com.github.astafex.BookLibrary.service;

import com.github.astafex.BookLibrary.dao.BookDAO;
import com.github.astafex.BookLibrary.dao.PersonDAO;
import com.github.astafex.BookLibrary.model.Book;
import com.github.astafex.BookLibrary.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LibraryService {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    public List<Person> getAllPeople() {
        return personDAO.getAll();
    }

    public Person getPerson(int id) {
        return personDAO.show(id);
    }

    public void addPerson(Person person) {
        personDAO.add(person);
    }

    public void updatePerson(int id, Person updatePerson) {
        personDAO.update(id, updatePerson);
    }

    public void removePerson(int id) {
        personDAO.remove(id);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAll();
    }

    public List<Book> getPersonBooksByID(int id) {
        return personDAO.getBooksByID(id);
    }

    public Book getBook(int id) {
        return bookDAO.show(id);
    }

    public void addBook(Book book) {
        bookDAO.add(book);
    }

    public void updateBook(int id, Book book) {
        bookDAO.update(id, book);
    }

    public void removeBook(int id) {
        bookDAO.remove(id);
    }

    public Optional<Person> getBookHolder(int id) {
        return bookDAO.getBookHolder(id);
    }
}
