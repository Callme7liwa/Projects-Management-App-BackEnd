package com.pfe.projectsmanagements.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFromApi <T>{

    private  T  data ;
    private String message ;
}
