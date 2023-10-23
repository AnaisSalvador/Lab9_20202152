package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Partido;
import com.example.lab9_20202152.repository.PartidoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/partido")
public class PartidoController {
    final PartidoRepository partidoRepository;

    public PartidoController(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }
    //listar
    @GetMapping(value = {"/listar", ""})
    public List<Partido> listaPartido() {
        return partidoRepository.findAll();
    }

}
