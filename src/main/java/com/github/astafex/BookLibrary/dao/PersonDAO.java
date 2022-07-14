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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new BeanPropertyRowMapper<>(Person.class), fullName).stream().findAny();
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=? WHERE id=?", person.getFullName(), person.getYearOfBirth(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void add(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth) VALUES(?,?)", person.getFullName(), person.getYearOfBirth());
    }

    public List<Book> getBooksByID(int id) {
        return jdbcTemplate.query("SELECT b.* FROM person p INNER JOIN book b ON p.id=b.person_id WHERE p.id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }
}
