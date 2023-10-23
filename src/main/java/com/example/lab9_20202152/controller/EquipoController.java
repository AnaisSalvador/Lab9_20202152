package com.example.lab9_20202152.controller;

import com.example.lab9_20202152.entity.Equipo;
import com.example.lab9_20202152.repository.EquipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    //actualizar
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Equipo nombreRecibio) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (nombreRecibio.getId() != null && nombreRecibio.getId() > 0) {

            Optional<Equipo> byId = equipoRepository.findById(nombreRecibio.getId());
            if (byId.isPresent()) {
                Equipo productFromDb = byId.get();

                if (nombreRecibio.getNombreEquipo() != null)
                    productFromDb.setNombreEquipo(nombreRecibio.getNombreEquipo());

                if (nombreRecibio.getColorEquipo() != null)
                    productFromDb.setColorEquipo(nombreRecibio.getColorEquipo());

                if (nombreRecibio.getMascota() != null)
                    productFromDb.setMascota(nombreRecibio.getMascota());

                equipoRepository.save(productFromDb);
                rpta.put("resultado", "ok");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("resultado", "error");
                rpta.put("msg", "El ID escrito no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        } else {
            rpta.put("resultado", "error");
            rpta.put("msg", "Enviar correctamente los datos");
            return ResponseEntity.badRequest().body(rpta);
        }
    }

}
