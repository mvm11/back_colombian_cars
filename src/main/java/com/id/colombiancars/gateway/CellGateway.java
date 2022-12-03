package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.Cell;
import com.id.colombiancars.request.CellRequest;


import java.util.List;

public interface CellGateway {

    Cell findCellById(Long cellId);
    List<Cell> findAllCells();
    Cell saveCell(CellRequest cellRequest);
    Cell updateCell(Long cellId, CellRequest cellRequest);
}
