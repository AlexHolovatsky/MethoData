package org.example.metho.services;

import org.example.metho.models.ProtectedArea;
import org.example.metho.repositories.ProtectedAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProtectedAreaService {

    @Autowired
    private ProtectedAreaRepository protectedAreaRepository;

    public List<ProtectedArea> getAllReserves() {
        return protectedAreaRepository.findAll();
    }
}
