package org.example.metho.controllers;

import org.example.metho.models.ProtectedArea;
import org.example.metho.repositories.ProtectedAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class LandingPage {

    @Autowired
    private ProtectedAreaRepository protectedAreaRepository;

    // Цей метод повертає HTML сторінку (landingpage)
    @GetMapping("/")
    public String main(Model model) {
        return "landingpage";  // Це ім'я вашого HTML шаблону
    }

}
