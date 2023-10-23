package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Deporte;
import com.example.lab9_20202152.entity.Participante;
import com.example.lab9_20202152.repository.ParticipanteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    //Crear
    @PostMapping(value = {"/registro", ""})
    public ResponseEntity<HashMap<String, Object>> guardarParticipante(
            @RequestBody Participante participante,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        participanteRepository.save(participante);
        if (fetchId) {
            responseJson.put("id", participante.getId());
        }
        responseJson.put("Deporte", "registrado-creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

}
