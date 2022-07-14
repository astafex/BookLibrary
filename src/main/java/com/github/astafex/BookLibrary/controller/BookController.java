package com.github.astafex.BookLibrary.controller;

import com.github.astafex.BookLibrary.model.Book;
import com.github.astafex.BookLibrary.model.Person;
import com.github.astafex.BookLibrary.service.LibraryService;
import com.github.astafex.BookLibrary.util.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final LibraryService service;
    private final BookValidator bookValidator;

    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
        return "/books/all";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("book", service.getBook(id));
        model.addAttribute("personHolder", service.getBookHolder(id));
        model.addAttribute("people", service.getAllPeople());
        return "/books/show";
    }

    @GetMapping("/add")
    public String showFormCreateBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        service.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showFormUpdateBook(Model model,
                                     @PathVariable int id) {
        model.addAttribute("book", service.getBook(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable int id,
                             @ModelAttribute("book") @Valid Book updateBook,
                             BindingResult bindingResult) {
        Book beforeBook = service.getBook(id);
        if (!Objects.equals(beforeBook.getTitle(), updateBook.getTitle())) {
            bookValidator.validate(updateBook, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        service.updateBook(id, updateBook);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable int id) {
        service.returnBookToLibrary(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/take")
    public String takeBook(@PathVariable int id,
                           @ModelAttribute("person") Person person) {
        service.takeBookFromLibrary(id, person.getId());
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String removePerson(@PathVariable int id) {
        service.removeBook(id);
        return "redirect:/books";
    }
}