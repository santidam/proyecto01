package com.proyecto01.proyecto01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@ComponentScan(basePackages = {"com.proyecto01.proyecto01", "model", "dao", "controller", "com.proyecto01.proyecto01.exceptions"})
@EnableJpaRepositories(basePackages = {"dao"})
@EntityScan(basePackages = {"model"})  // Asegúrate de incluir este escaneo de entidades
public class Proyecto01Application {
    public static void main(String[] args) {
        SpringApplication.run(Proyecto01Application.class, args);
    }
    @GetMapping("/prueba")
public ResponseEntity<String> prueba() {
    return new ResponseEntity<>("El servidor funciona", HttpStatus.OK);
}

}

