package com.id.colombiancars.service;

import com.id.colombiancars.common.NotFoundException;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.gateway.CellGateway;
import com.id.colombiancars.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CellService implements CellGateway {

    @Autowired
    private CellRepository cellRepository;


    @Override
    public Cell findCellById(Long cellId) {
        return getCell(cellId);
    }

    private Cell getCell(Long cellId) {
        return cellRepository.findById(cellId)
                .orElseThrow(() -> new NotFoundException("Cell", "id", cellId));
    }

    @Override
    public List<Cell> findAllCells() {
        return cellRepository.findAll();
    }

    @Override
    public Cell saveCell(Cell cell) {
        return cellRepository.save(cell);
    }


    @Override
    public Cell updateCellAvailable(Long cellId) {
        Cell cellFound = getCell(cellId);
        cellFound.setOccupied(false);
        cellRepository.save(cellFound);
        return cellFound;
    }

    @Override
    public void deleteCell(Long cellId) {
        Cell cellFound = getCell(cellId);
        cellRepository.delete(cellFound);
    }
}
