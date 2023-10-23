package com.example.lab9_20202152.controller;
import com.example.lab9_20202152.entity.Deporte;
import com.example.lab9_20202152.entity.Participante;
import com.example.lab9_20202152.entity.Partido;
import com.example.lab9_20202152.repository.ParticipanteRepository;
import com.example.lab9_20202152.repository.PartidoRepository;
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
@RequestMapping("/partido")
public class PartidoController {
    final PartidoRepository partidoRepository;
    private final ParticipanteRepository participanteRepository;

    public PartidoController(PartidoRepository partidoRepository,
                             ParticipanteRepository participanteRepository) {
        this.partidoRepository = partidoRepository;
        this.participanteRepository = participanteRepository;
    }
    //listar
    @GetMapping(value = {"/listar", ""})
    public List<Partido> listaPartido() {
        return partidoRepository.findAll();
    }
    //Crear
    @PostMapping(value = {"/registro", ""})
    public ResponseEntity<HashMap<String, Object>> guardarPartido(
            @RequestBody Partido partido,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        partidoRepository.save(partido);
        if (fetchId) {
            responseJson.put("id", partido.getId());
        }
        responseJson.put("Partido", "registrado-creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Partido nombreRecibio) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (nombreRecibio.getId() != null && nombreRecibio.getId() > 0) {

            Optional<Partido> byId = partidoRepository.findById(nombreRecibio.getId());
            if (byId.isPresent()) {
                Partido productFromDb = byId.get();

                if (nombreRecibio.getEquipoA() != null)
                    productFromDb.setEquipoA(nombreRecibio.getEquipoA());

                if (nombreRecibio.getEquipoB() != null)
                    productFromDb.setEquipoB(nombreRecibio.getEquipoB());

                if (nombreRecibio.getScoreA() != null)
                    productFromDb.setScoreA(nombreRecibio.getScoreA());

                if (nombreRecibio.getScoreB() != null)
                    productFromDb.setScoreB(nombreRecibio.getScoreB());

                partidoRepository.save(productFromDb);
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

    //get participantes
    @GetMapping(value = "/getparticipantes")
    public ResponseEntity<HashMap<String, Object>> buscarParticipantes(@PathVariable("id") String idStr) {


        try {
            int id = Integer.parseInt(idStr);
            Optional<Participante> byId = participanteRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {
                respuesta.put("resultado", "ok");
                respuesta.put("participante", byId.get());
            } else {
                respuesta.put("resultado", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    //get historial
    @GetMapping(value = "/gethistorialpartidos")
    public ResponseEntity<HashMap<String, Object>> buscarHistorialPartidos(@PathVariable("id") String idStr) {


        try {
            int id = Integer.parseInt(idStr);
            Optional<Partido> byId = partidoRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {
                respuesta.put("resultado", "ok");
                respuesta.put("participante", byId.get());
            } else {
                respuesta.put("resultado", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }








    //----
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionException(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Se produjo un error");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
