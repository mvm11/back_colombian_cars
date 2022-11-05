package com.id.colombiancars.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryRequest {

    private String ownerName;

    private String ownerLastname;

    private String ownerDni;

    private String licensePlate;

    private String type;

}
