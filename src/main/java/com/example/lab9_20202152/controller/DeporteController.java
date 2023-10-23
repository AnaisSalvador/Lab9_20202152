package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Deporte;
import com.example.lab9_20202152.repository.DeporteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/deporte")
public class DeporteController {
    final DeporteRepository deporteRepository;

    public DeporteController(DeporteRepository deporteRepository) {
        this.deporteRepository = deporteRepository;
    }

    //listar
    @GetMapping(value = {"/listar", ""})
    public List<Deporte> listaDeporte() {
        return deporteRepository.findAll();
    }

    //Crear
    @PostMapping(value = {"/registro", ""})
    public ResponseEntity<HashMap<String, Object>> guardarDeporte(
            @RequestBody Deporte deporte,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        deporteRepository.save(deporte);
        if (fetchId) {
            responseJson.put("id", deporte.getId());
        }
        responseJson.put("Deporte", "registrado-creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

}
