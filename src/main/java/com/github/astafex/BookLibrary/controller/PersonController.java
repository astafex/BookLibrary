package com.github.astafex.BookLibrary.controller;

import com.github.astafex.BookLibrary.model.Person;
import com.github.astafex.BookLibrary.service.LibraryService;
import com.github.astafex.BookLibrary.util.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class PersonController {
    private final LibraryService service;
    private final PersonValidator personValidator;

    @GetMapping
    public String showAllPeople(Model model) {
        model.addAttribute("people", service.getAllPeople());
        return "/people/all";
    }

    @GetMapping("/{id}")
    public String showPerson(Model model,
                             @PathVariable int id) {
        model.addAttribute("person", service.getPerson(id));
        model.addAttribute("books", service.getPersonBooksByID(id));
        return "/people/show";
    }

    @GetMapping("/add")
    public String showFormCreatePerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/new";
        }
        service.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showFormUpdatePerson(Model model,
                                       @PathVariable int id) {
        model.addAttribute("person", service.getPerson(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable int id,
                               @ModelAttribute("person") @Valid Person updatePerson,
                               BindingResult bindingResult) {
        Person beforePerson = service.getPerson(id);
        if (!Objects.equals(updatePerson.getFullName(), beforePerson.getFullName())) {
            personValidator.validate(updatePerson, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        service.updatePerson(id, updatePerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String removePerson(@PathVariable int id) {
        service.removePerson(id);
        return "redirect:/people";
    }
}