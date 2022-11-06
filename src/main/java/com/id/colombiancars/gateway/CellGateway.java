package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.Cell;


import java.util.List;

public interface CellGateway {

    Cell findCellById(Long cellId);
    List<Cell> findAllCells();
    Cell saveCell(Cell cell);
    Cell updateCellAvailable(Long cellId);
    void deleteCell(Long cellId);
}
