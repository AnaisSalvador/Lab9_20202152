package com.example.lab9_20202152.controller;

import com.example.lab9_20202152.entity.Equipo;
import com.example.lab9_20202152.repository.EquipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    //Crear
    @PostMapping(value = {"/registro", ""})
    public ResponseEntity<HashMap<String, Object>> guardarEquipo(
            @RequestBody Equipo equipo,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        equipoRepository.save(equipo);
        if (fetchId) {
            responseJson.put("id", equipo.getId());
        }
        responseJson.put("Equipo", "registrado-creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }


}
