package com.github.alphameo.railways.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Locomotive {

    private Long id;
    private String number;
    private String model;
}
