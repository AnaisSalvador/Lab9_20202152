package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Participante;
import com.example.lab9_20202152.repository.ParticipanteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    //actualizar
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Participante nombreRecibio) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (nombreRecibio.getId() != null && nombreRecibio.getId() > 0) {

            Optional<Participante> byId = participanteRepository.findById(nombreRecibio.getId());
            if (byId.isPresent()) {
                Participante productFromDb = byId.get();

                if (nombreRecibio.getEquipo() != null)
                    productFromDb.setEquipo(nombreRecibio.getEquipo());

                if (nombreRecibio.getCarrera() != null)
                    productFromDb.setCarrera(nombreRecibio.getCarrera());

                if (nombreRecibio.getCodigo() != null)
                    productFromDb.setCodigo(nombreRecibio.getCodigo());

                if (nombreRecibio.getTipoParticipante() != null)
                    productFromDb.setTipoParticipante(nombreRecibio.getTipoParticipante());

                participanteRepository.save(productFromDb);
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
