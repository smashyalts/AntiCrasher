package net.craftsupport.anticrasher.common.library.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LibraryConfig {
    public List<String> repositories;
    public List<Library> artifacts;
}
