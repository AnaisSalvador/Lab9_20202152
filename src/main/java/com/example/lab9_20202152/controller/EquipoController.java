package com.example.lab9_20202152.controller;

import com.example.lab9_20202152.entity.Equipo;
import com.example.lab9_20202152.repository.EquipoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

     final EquipoRepository equipoRepository;

    public EquipoController(
                            EquipoRepository equipoRepository) {

        this.equipoRepository = equipoRepository;
    }
    //listar
    @GetMapping(value = {"/listar", ""})
    public List<Equipo> listaEquipo() {
        return equipoRepository.findAll();
    }
}
