package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    // Guardar un Disco
    @PostMapping(
        value = "/disco", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (artistaRepository.existsById(disco.idArtista)) { 
            Disco nuevoDisco = discoRepository.save(disco);
            return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: El artista especificado no existe.", HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todos los discos
    @GetMapping(
        value = "/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // Obtener un disco por su ID
    @GetMapping(
        value = "/disco/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepository.findById(id)
            .map(disco -> new ResponseEntity<Object>(disco, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND));
    }

    // Obtener todos los discos de un artista específico
    @GetMapping(
        value = "/artista/{id}/discos", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> discosDelArtista = discoRepository.findDiscosByIdArtista(id); 
        return new ResponseEntity<>(discosDelArtista, HttpStatus.OK);
    }
}