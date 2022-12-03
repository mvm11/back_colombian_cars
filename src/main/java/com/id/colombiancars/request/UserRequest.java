package com.id.colombiancars.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String lastname;
    private String dni;

}
