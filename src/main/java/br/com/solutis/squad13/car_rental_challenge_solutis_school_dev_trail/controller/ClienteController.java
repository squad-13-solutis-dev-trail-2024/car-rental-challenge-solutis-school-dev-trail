package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.controller;

import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.dto.MotoristaDTO;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.entity.Motorista;
import br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    private final UserService userService;

    public ClienteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(name = "{email}")
    public ResponseEntity<MotoristaDTO> findByEmail(@PathVariable String email ){
        Motorista motorista = userService.findByEmail(email);
        MotoristaDTO motoristaDTO = userService.convertToDTO(motorista);
        return ResponseEntity.ok().body(motoristaDTO);
    }
}
