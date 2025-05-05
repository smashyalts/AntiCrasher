package net.craftsupport.anticrasher.api.check;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckInfo {

    private final String name;
    private final String description;
    private final String type; // A, B, C, D

}
