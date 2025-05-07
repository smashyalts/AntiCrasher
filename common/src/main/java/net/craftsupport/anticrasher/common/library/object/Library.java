package net.craftsupport.anticrasher.common.library.object;

public record Library(
        String groupId,
        String artifactId,
        String version,
        boolean parseTransitive
) {}
