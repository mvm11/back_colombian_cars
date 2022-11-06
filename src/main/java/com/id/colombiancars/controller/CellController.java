package com.id.colombiancars.controller;

import com.id.colombiancars.entity.Cell;
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
    ResponseEntity<Cell> saveCell(@RequestBody Cell cell){
        return new ResponseEntity<>(cellService.saveCell(cell), HttpStatus.CREATED);
    }

    @PutMapping("/updateCellAvailable/{id}")
    ResponseEntity<Cell> updateCellAvailable(@PathVariable Long id){
        return new ResponseEntity<>(cellService.updateCellAvailable(id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCell/{id}")
    ResponseEntity<String> deleteCell(@PathVariable Long id){
        cellService.deleteCell(id);
        return new ResponseEntity<>("Cell deleted successful",HttpStatus.NO_CONTENT);
    }
}
