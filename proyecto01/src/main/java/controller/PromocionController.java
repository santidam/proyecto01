package controller;

import com.proyecto01.proyecto01.exceptions.ResourceNotFoundException;
import model.Promocion;
import model.Pyme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dao.PromocionRepository;
import dao.PymeRepository;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;

@RestController
@RequestMapping("/pymes/{pymeId}/promociones")
public class PromocionController {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private PymeRepository pymeRepository;

    @PostMapping
    public ResponseEntity<?> crearPromocion(@PathVariable Long pymeId, @Valid @RequestBody Promocion promocion, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errores);
        }

        Pyme pyme = pymeRepository.findById(pymeId)
            .orElseThrow(() -> new ResourceNotFoundException("PYME no encontrada con id: " + pymeId));

        promocion.setPyme(pyme);
        Promocion nuevaPromocion = promocionRepository.save(promocion);
        return ResponseEntity.ok(nuevaPromocion);
    }

    @GetMapping
    public ResponseEntity<List<Promocion>> listarPromociones(@PathVariable Long pymeId) {
        Pyme pyme = pymeRepository.findById(pymeId)
            .orElseThrow(() -> new ResourceNotFoundException("PYME no encontrada con id: " + pymeId));

        List<Promocion> promociones = promocionRepository.findByPyme(pyme);
        return ResponseEntity.ok(promociones);
    }
}
