/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PymeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import model.Pyme;

@RestController
@RequestMapping("/pymes")
public class PymeController {

    @Autowired
    private PymeRepository pymeRepository;

    @GetMapping
    public List<Pyme> listarPymes() {
        return pymeRepository.findAll();
    }

    @PostMapping("/registro")
    public Pyme registrarPyme(@RequestBody Pyme pyme) {
        return pymeRepository.save(pyme);
    }
}
