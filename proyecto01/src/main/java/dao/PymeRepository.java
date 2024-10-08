/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Pyme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PymeRepository extends JpaRepository<Pyme, Long> {
}
