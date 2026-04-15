package com.Smillio.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Smillio.app.dtos.LoginRequest;
import com.Smillio.app.dtos.LoginResponse;
import com.Smillio.app.dtos.RegistroRequest;
import com.Smillio.app.entities.Rol;
import com.Smillio.app.entities.Usuarios;
import com.Smillio.app.repositories.usuariosRepo;

@Service
public class AuthService {

    @Autowired
    private usuariosRepo usuariosRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        Usuarios usuario = usuariosRepo
                .findByCorreoElectronico(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getHashContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        if (!usuario.getEstaActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getCorreoElectronico(),
                usuario.getRol().name()
        );
    }

    public Long register(RegistroRequest request) {

        if (usuariosRepo.findByCorreoElectronico(request.getCorreo()).isPresent()) {
            throw new RuntimeException("Correo ya registrado");
        }

        Usuarios usuario = new Usuarios();
        usuario.setCorreoElectronico(request.getCorreo());
        usuario.setHashContrasena(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.valueOf(request.getTipo()));
        usuario.setEstaActivo(true);

        Usuarios guardado = usuariosRepo.save(usuario);
        return guardado.getId();
    }
} 