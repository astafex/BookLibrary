package com.github.astafex.BookLibrary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class Person {
    private int id;

    @Size(max = 100)
    @NotEmpty
    @Pattern(regexp = "^([A-ZА-Я][a-zа-я]+) ([A-ZА-Я][a-zа-я]+) ([A-ZА-Я][a-zа-я]+)$")
    private String fullName;

    @Min(value = 1901, message = "Год рождения должен быть больше 1900!")
    @Max(value = 2022, message = "Год рождения должен быть меньше или равен 2022!")
    private int yearOfBirth;
}
