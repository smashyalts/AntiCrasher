package net.craftsupport.anticrasher.api.check;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckInfo {

    /**
     * The name of the check.
     * This is typically quite vague but self-explanatory, e.g. Item, or Window
     */
    private final String name;
    /**
     * The description of the check.
     * This should give a slightly more in-depth explanation of what the check does.
     */
    private final String description;
    /**
     * The type of the check.
     * This is used to determine what type of check this is, e.g. A, B, C, D
     */
    private final String type;

}
