package com.github.astafex.BookLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {
    @GetMapping("/")
    public String welcomePage() {
        return "/library/welcome";
    }
}
