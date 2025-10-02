package com.github.alphameo.railways.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carriage {

    public enum ContentType {
        PASSENGER,
        CARGO;
    }

    private Long id;
    private String number;
    private ContentType contentType;
    private Long capacity;
}
