package net.craftsupport.anticrasher.api.util.objects;

public record Tuple<F, S>(F first, S second) {
    public static <F, S> Tuple<F, S> of(F first, S second) {
        return new Tuple<>(first, second);
    }
}