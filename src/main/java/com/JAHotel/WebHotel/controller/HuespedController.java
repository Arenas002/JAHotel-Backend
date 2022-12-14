package com.JAHotel.WebHotel.controller;


import com.JAHotel.WebHotel.model.domain.Huesped;
import com.JAHotel.WebHotel.model.service.HuespedService;
import com.JAHotel.WebHotel.utilities.MyResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*")
public class HuespedController {
    @Autowired
    private MyResponseUtility response;

    @Autowired
    private HuespedService huespedService;

    @GetMapping(path = "/obtenerHuesped")
    public ResponseEntity<MyResponseUtility> obtenerHuesped() {
        response.data = huespedService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/obtenerHuesped/{id}")
    public ResponseEntity<MyResponseUtility> obtenerHuespedPorId(@PathVariable(value = "id") Integer id) {
        try {
            response.data = huespedService.getById(id);
        }catch (DataAccessException e){
            response.message= ("Error al realizar la consulta en la base de datos");
            response.descriptionError = (Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(((Optional<?>)response.data).isEmpty()){
            response.message = ("El Huesped con` Id: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path ="/createHuesped")
    public ResponseEntity<MyResponseUtility>createHuesped(@RequestBody Huesped huesped){
        response.data = huespedService.create(huesped);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path ="/ActualizarHuesped/{id}")
    public ResponseEntity<MyResponseUtility>ActualizarHuespedPorId(@PathVariable(value = "id") Integer id,@RequestBody Huesped huesped){
        response.data = huespedService.update(id,huesped);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/eliminarHuesped/{id}")
    public ResponseEntity<MyResponseUtility>eliminarHuesped(@PathVariable(value = "id") Integer id) {
        response.data = huespedService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }








}
