package com.picpaysimplificado.controllers;

import com.picpaysimplificado.dtos.UpdatePasswordDTO;
import com.picpaysimplificado.dtos.UsuarioDTO;
import com.picpaysimplificado.models.Usuario;
import com.picpaysimplificado.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Validated
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;


    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> findUsuarioById(@PathVariable Long id) throws Exception {
        Usuario obj = this.usuarioService.findUsuarioById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<Usuario> findUsuarioByDocument(@PathVariable String document) throws Exception {
        Usuario obj = this.usuarioService.findUsuarioByDocument(document);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsuarios() {
        List<Usuario> objs = this.usuarioService.findAllUsuarios();
        return new ResponseEntity<>(objs, HttpStatus.OK);
    }

    // Valida do DTO antes de qualquer processamento ou interação com o banco de dados.
    @Validated
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioDTO usuarioData) throws Exception {
        Usuario usuario = this.usuarioService.createUsuario(usuarioData);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Validated
    @PutMapping("/id/{id}")
    public ResponseEntity<Void> updateUsuario(@Valid @RequestBody UpdatePasswordDTO passwordData, @PathVariable Long id) throws Exception {
        this.usuarioService.updateUsuario(passwordData, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) throws Exception {
        this.usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
