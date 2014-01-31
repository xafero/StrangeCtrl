package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class Button {
    public static String SEQUENTIAL_TYPE = "SEQUENTIAL";
    public static String COMBO_TYPE = "COMBO";

    private String value;
    private final List<Key> keys = new ArrayList<>();
    private String pressType = SEQUENTIAL_TYPE;

    Button() {
        // for XStreamLoader
    }

    public Button(final String value, final String pressType,
            final List<Key> keys) {
        this.value = value;
        this.pressType = checkNotNull(pressType);

        for (final Key key : keys) {
            this.keys.add(key);
        }
    }

    public Button(final String value, final Key... keys) {
        this(value, Arrays.asList(keys));
    }

    public Button(final String value, final List<Key> keys) {
        this(value, SEQUENTIAL_TYPE, keys);
    }

    public String getValue() {
        return value;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public String getPressType() {
        return pressType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, keys);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Button) {
            final Button button = (Button) obj;

            return Objects.equal(value, button.value)
                    && Objects.equal(keys, button.keys);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Button [value=" + value + ", keys=" + Joiner.on(",").join(keys)
                + "]";
    }

}
