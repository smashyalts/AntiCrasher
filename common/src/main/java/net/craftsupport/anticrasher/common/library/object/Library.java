package net.craftsupport.anticrasher.common.library.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Library {
    private String groupId;
    private String artifactId;
    private String version;
    private boolean parseTransitive;
}
