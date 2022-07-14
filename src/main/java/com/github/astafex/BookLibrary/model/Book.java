package com.github.astafex.BookLibrary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class Book {
    private int id;

    @Size(max = 100)
    @NotEmpty
    private String title;

    @Size(max = 100)
    @NotEmpty
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+ ([A-ZА-Я]\\.){2}")
    private String author;

    @Min(value = 0, message = "Год не может быть отрицательным!")
    @Max(value = 2022, message = "Год рождения должен быть меньше или равен 2022!")
    private int yearOfPublish;
}
