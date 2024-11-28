package org.example.metho.controllers;

import org.example.metho.models.ProtectedArea;
import org.example.metho.services.ProtectedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ProtectedAreaController {
    @Autowired
    private ProtectedAreaService protectedAreaService;

    @GetMapping("/api/reserves")
    public List<ProtectedArea> getReserves() {
        return protectedAreaService.getAllReserves();
    }
}
