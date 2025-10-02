package com.github.alphameo.railways.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainComposition {

    private Long id;
    private Long train_id;
    private Long locomotive_id;
}
