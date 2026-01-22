package org.iplacex.proyectos.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    // Crear artista
    @PostMapping(
        value = "/artista", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        try {
            Artista nuevoArtista = artistaRepository.save(artista);
            return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el registro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los artistas
    @GetMapping(
        value = "/artistas", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    // Obtener un artista por ID
    @GetMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        return artistaRepository.findById(id)
            .map(artista -> new ResponseEntity<Object>(artista, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND));
    }

    // Actualizar artista
    @PutMapping(
        value = "/artista/{id}", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    ) 
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (artistaRepository.existsById(id)) {
            artista._id = id;
            Artista actualizado = artistaRepository.save(artista);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: El artista no existe", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar artista
    @DeleteMapping(
        value = "/artista/{id}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return new ResponseEntity<>("Registro eliminado con éxito", HttpStatus.OK);
        } 
        return new ResponseEntity<>("Error: No se pudo eliminar, el artista no existe", HttpStatus.NOT_FOUND);
    }
}