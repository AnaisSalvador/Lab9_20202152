package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Deporte;
import com.example.lab9_20202152.repository.DeporteRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    //actualizar
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Deporte nombreRecibio) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (nombreRecibio.getId() != null && nombreRecibio.getId() > 0) {

            Optional<Deporte> byId = deporteRepository.findById(nombreRecibio.getId());
            if (byId.isPresent()) {
                Deporte productFromDb = byId.get();

                if (nombreRecibio.getNombreDeporte() != null)
                    productFromDb.setNombreDeporte(nombreRecibio.getNombreDeporte());

                if (nombreRecibio.getPesoDeporte() != null)
                    productFromDb.setPesoDeporte(nombreRecibio.getPesoDeporte());


                deporteRepository.save(productFromDb);
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
