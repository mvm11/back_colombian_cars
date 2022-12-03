package com.id.colombiancars.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    private String ownerDni;
    private String licensePlate;
    private String type;

}
