package net.craftsupport.anticrasher.fabric.library.object;

public record Library(
        String groupId,
        String artifactId,
        String version,
        boolean parseTransitive
) {}
