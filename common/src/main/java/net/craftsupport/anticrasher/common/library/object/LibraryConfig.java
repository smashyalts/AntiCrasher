package net.craftsupport.anticrasher.common.library.object;

import java.util.List;

public record LibraryConfig(
        List<String> repositories,
        List<Library> artifacts
) {}
