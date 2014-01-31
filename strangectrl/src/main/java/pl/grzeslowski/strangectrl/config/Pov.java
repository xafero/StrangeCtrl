package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class Pov {
    private final List<Button> povDirections = new ArrayList<>();

    private Pov() {
    }

    private Button getButton(final String identifier) {
        for (final Button pov : povDirections) {
            if (equal(pov.getValue(), identifier)) {
                return pov;
            }
        }

        return null;
    }

    public Button getNorthPov() {
        return getButton("NP");
    }

    public Button getSouthPov() {
        return getButton("SP");
    }

    public Button getEastPov() {
        return getButton("EP");
    }

    public Button getWestPov() {
        return getButton("WP");
    }

    public Button getNorthEastPov() {
        return getButton("NEP");
    }

    public Button getNorthWestPov() {
        return getButton("NWP");
    }

    public Button getSouthEastPov() {
        return getButton("SEP");
    }

    public Button getSouthWestPov() {
        return getButton("SWP");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(povDirections);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Pov) {
            final Pov pov = (Pov) obj;

            return povDirections.containsAll(pov.povDirections)
                    && pov.povDirections.containsAll(povDirections);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("POV[%s]", Joiner.on(", ").join(povDirections));
    }

    public static class PovBuilder {

        private Button northPov;
        private Button southPov;
        private Button eastPov;
        private Button westPov;
        private Button northEastPov;
        private Button northWestPov;
        private Button southEastPov;
        private Button southWestPov;

        public Pov build() {
            final Pov pov = new Pov();

            add(pov, northPov);
            add(pov, southPov);
            add(pov, eastPov);
            add(pov, westPov);
            add(pov, northEastPov);
            add(pov, northWestPov);
            add(pov, southEastPov);
            add(pov, southWestPov);

            return pov;
        }

        private void add(final Pov pov, final Button Button) {
            if (Button != null) {
                pov.povDirections.add(Button);
            }
        }

        public PovBuilder northPov(final Button northPov) {
            this.northPov = northPov;
            return this;
        }

        public PovBuilder southPov(final Button southPov) {
            this.southPov = southPov;
            return this;
        }

        public PovBuilder eastPov(final Button eastPov) {
            this.eastPov = eastPov;
            return this;
        }

        public PovBuilder westPov(final Button westPov) {
            this.westPov = westPov;
            return this;
        }

        public PovBuilder northEastPov(final Button northEastPov) {
            this.northEastPov = northEastPov;
            return this;
        }

        public PovBuilder northWestPov(final Button northWestPov) {
            this.northWestPov = northWestPov;
            return this;
        }

        public PovBuilder southEastPov(final Button southEastPov) {
            this.southEastPov = southEastPov;
            return this;
        }

        public PovBuilder southWestPov(final Button southWestPov) {
            this.southWestPov = southWestPov;
            return this;
        }
    }
}
