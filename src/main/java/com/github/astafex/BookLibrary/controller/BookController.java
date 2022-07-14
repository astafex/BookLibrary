package com.github.astafex.BookLibrary.controller;

import com.github.astafex.BookLibrary.model.Book;
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
    public String showBook(@PathVariable int id, Model model) {
        model.addAttribute("book", service.getBook(id));
        model.addAttribute("person", service.getBookHolder(id));
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

    @DeleteMapping("/{id}")
    public String removePerson(@PathVariable int id) {
        service.removeBook(id);
        return "redirect:/books";
    }
}