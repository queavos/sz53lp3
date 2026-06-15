/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.unae.demo.demoapp.config;

/**
 *
 * @author queavos
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()

                .requestMatchers("/", "/home").authenticated()

                .requestMatchers("/usuarios", "/usuarios/**").hasRole("ADMIN")

                .requestMatchers("/categorias", "/categorias/", "/categorias/**")
                    .hasAnyRole("ADMIN", "STOCK")

                .requestMatchers("/productos", "/productos/", "/productos/**")
                    .hasAnyRole("ADMIN", "STOCK")

                .requestMatchers("/clientes", "/clientes/**")
                    .hasAnyRole("ADMIN", "VENDEDOR")

                .requestMatchers("/notas", "/notas/**")
                    .hasAnyRole("ADMIN", "STOCK")

                .requestMatchers("/ventas", "/ventas/**")
                    .hasAnyRole("ADMIN", "VENDEDOR")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}