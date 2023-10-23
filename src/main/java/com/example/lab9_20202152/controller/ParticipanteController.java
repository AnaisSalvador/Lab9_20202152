package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Participante;
import com.example.lab9_20202152.repository.ParticipanteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/participante")
public class ParticipanteController {
    final ParticipanteRepository participanteRepository;

    public ParticipanteController(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }
    //listar
    @GetMapping(value = {"/listar", ""})
    public List<Participante> listaParticipante() {
        return participanteRepository.findAll();
    }
}
