package com.github.astafex.BookLibrary.dao;

import com.github.astafex.BookLibrary.model.Book;
import com.github.astafex.BookLibrary.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public Optional<Book> show(String title) {
        return jdbcTemplate.query("SELECT * FROM book WHERE title=?", new BeanPropertyRowMapper<>(Book.class), title).stream().findAny();
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?,author=?,year_of_publish=? WHERE id=?", book.getTitle(), book.getAuthor(), book.getYearOfPublish(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,year_of_publish) VALUES(?,?,?)", book.getTitle(), book.getAuthor(), book.getYearOfPublish());
    }

    public Optional<Person> getBookHolder(int id) {
        return jdbcTemplate.query("SELECT p.* FROM book b INNER JOIN person p ON p.ID=b.person_id WHERE b.id=?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public void clearHolderBook(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }

    public void addHolderBook(int id, int personID) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personID, id);
    }
}
