package com.id.colombiancars.controller;

import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.request.CellRequest;
import com.id.colombiancars.request.UserRequest;
import com.id.colombiancars.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cell")
@CrossOrigin(origins = "*")
public class CellController {

    @Autowired
    private CellService cellService;


    @GetMapping(value = "/findAllCells")
    public List<Cell> findAllCells(){return cellService.findAllCells();
    }

    @GetMapping(value = "/findCellById/{id}")
    public ResponseEntity<Cell> findCellById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cellService.findCellById(id));
    }

    @PostMapping("/saveCell")
    ResponseEntity<Cell> saveCell(@RequestBody CellRequest cellRequest){
        return new ResponseEntity<>(cellService.saveCell(cellRequest), HttpStatus.CREATED);
    }

    // Update
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Cell> updateCell (@PathVariable Long id, @RequestBody CellRequest cellRequest) {
        return new ResponseEntity<>(cellService.updateCell(id, cellRequest), HttpStatus.OK);

    }

}
