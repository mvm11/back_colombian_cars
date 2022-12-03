package com.id.colombiancars.service;

import com.id.colombiancars.common.NotFoundException;
import com.id.colombiancars.common.ParkingException;
import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.gateway.CellGateway;
import com.id.colombiancars.repository.CellRepository;
import com.id.colombiancars.request.CellRequest;
import com.id.colombiancars.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    public Cell saveCell(CellRequest cellRequest) {
        validateCellFields(cellRequest);
        Cell cell = buildCell(cellRequest);
        return cellRepository.save(cell);
    }

    private final Map<String, Function<CellRequest, ?>> cellRequestValidations = Map.of(
            "cellName", CellRequest::getCellName
    );

    private void validateCellFields(CellRequest cellRequest) {
        cellRequestValidations.entrySet().stream()
                .filter(entry -> entry.getValue().apply(cellRequest) == null)
                .map(Map.Entry::getKey)
                .map(field -> String.format("The cell's %s cannot be null", field))
                .map(s -> new ParkingException(HttpStatus.BAD_REQUEST, "Null values are not accepted"))
                .findFirst()
                .ifPresent(e -> {
                    throw e;
                });
    }

    private static Cell buildCell(CellRequest cellRequest) {
        return Cell.builder()
                .cellName(cellRequest.getCellName())
                .isOccupied(false)
                .hasVehicle(false)
                .build();
    }


    @Override
    public Cell updateCell(Long cellId, CellRequest cellRequest) {
        validateCellFields(cellRequest);
        Cell cellFound = getCell(cellId);
        cellFound.setCellName(cellRequest.getCellName());
        cellRepository.save(cellFound);
        return cellFound;
    }
}
