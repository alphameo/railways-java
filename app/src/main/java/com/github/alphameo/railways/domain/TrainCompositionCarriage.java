package com.github.alphameo.railways.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainCompositionCarriage {

    private Long carriageId;
    private Long trainCompositionId;
    private Integer position;
}
