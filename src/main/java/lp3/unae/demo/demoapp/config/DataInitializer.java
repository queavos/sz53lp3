/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.config;

/**
 *
 * @author queavos
 */
import lp3.unae.demo.demoapp.model.Rol;
import lp3.unae.demo.demoapp.model.Usuario;
import lp3.unae.demo.demoapp.repositories.RolRepository;
import lp3.unae.demo.demoapp.repositories.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Rol admin = crearRolSiNoExiste("ROLE_ADMIN");
        Rol stock = crearRolSiNoExiste("ROLE_STOCK");
        Rol vendedor = crearRolSiNoExiste("ROLE_VENDEDOR");

        crearUsuarioSiNoExiste("admin", "admin123", admin);
        crearUsuarioSiNoExiste("stock", "stock123", stock);
        crearUsuarioSiNoExiste("vendedor", "vendedor123", vendedor);
    }

    private Rol crearRolSiNoExiste(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseGet(() -> rolRepository.save(new Rol(nombre)));
    }

    private void crearUsuarioSiNoExiste(String username, String passwordPlano, Rol rol) {

        if (usuarioRepository.findByUsername(username).isEmpty()) {

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(passwordEncoder.encode(passwordPlano));
            usuario.setActivo(true);
            usuario.setRol(rol);

            usuarioRepository.save(usuario);
        }
    }
}
