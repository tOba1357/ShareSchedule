package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tatsuya Oba
 */
public enum State {
    BUSY("忙しい"),
    FREE("暇"),
    WELL("微妙"),
    UNDECIDED("未定");

    private final String name;

    State(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static State fromInt(final Integer num) {
        if (values().length > num) {
            return values()[num];
        }
        return null;
    }

    public static Map<String, Object> toJson() {
        final Map<String, Object> jsonValues = new HashMap<>();
        for (final State state : values()) {
            final Map<String, Object> jsonValue = new HashMap<>();
            jsonValue.put("id", state.ordinal());
            jsonValue.put("name", state.getName());
            jsonValues.put(state.toString() ,jsonValue);
        }
        return jsonValues;
    }
}
