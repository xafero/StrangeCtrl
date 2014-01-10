package pl.grzeslowski.strangectrl.config;

public class Pov {
    private NorthPov northPov;
    private SouthPov southPov;
    private EastPov eastPov;
    private WestPov westPov;
    private NorthEastPov northEastPov;
    private NorthWestPov northWestPov;
    private SouthEastPov southEastPov;
    private SouthWestPov southWestPov;

    public Pov() {
    }

    public Pov(final NorthPov northPov, final SouthPov southPov,
            final EastPov eastPov,
            final WestPov westPov) {
        this.northPov = northPov;
        this.southPov = southPov;
        this.eastPov = eastPov;
        this.westPov = westPov;
    }

    public Pov(final NorthPov northPov, final SouthPov southPov,
            final EastPov eastPov,
            final WestPov westPov, final NorthEastPov northEastPov,
            final NorthWestPov northWestPov, final SouthEastPov southEastPov,
            final SouthWestPov southWestPov) {
        this(northPov, southPov, eastPov, westPov);
        this.northEastPov = northEastPov;
        this.northWestPov = northWestPov;
        this.southEastPov = southEastPov;
        this.southWestPov = southWestPov;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (eastPov == null ? 0 : eastPov.hashCode());
        result = prime * result
                + (northEastPov == null ? 0 : northEastPov.hashCode());
        result = prime * result
                + (northPov == null ? 0 : northPov.hashCode());
        result = prime * result
                + (northWestPov == null ? 0 : northWestPov.hashCode());
        result = prime * result
                + (southEastPov == null ? 0 : southEastPov.hashCode());
        result = prime * result
                + (southPov == null ? 0 : southPov.hashCode());
        result = prime * result
                + (southWestPov == null ? 0 : southWestPov.hashCode());
        result = prime * result + (westPov == null ? 0 : westPov.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pov other = (Pov) obj;
        if (eastPov == null) {
            if (other.eastPov != null) {
                return false;
            }
        } else if (!eastPov.equals(other.eastPov)) {
            return false;
        }
        if (northEastPov == null) {
            if (other.northEastPov != null) {
                return false;
            }
        } else if (!northEastPov.equals(other.northEastPov)) {
            return false;
        }
        if (northPov == null) {
            if (other.northPov != null) {
                return false;
            }
        } else if (!northPov.equals(other.northPov)) {
            return false;
        }
        if (northWestPov == null) {
            if (other.northWestPov != null) {
                return false;
            }
        } else if (!northWestPov.equals(other.northWestPov)) {
            return false;
        }
        if (southEastPov == null) {
            if (other.southEastPov != null) {
                return false;
            }
        } else if (!southEastPov.equals(other.southEastPov)) {
            return false;
        }
        if (southPov == null) {
            if (other.southPov != null) {
                return false;
            }
        } else if (!southPov.equals(other.southPov)) {
            return false;
        }
        if (southWestPov == null) {
            if (other.southWestPov != null) {
                return false;
            }
        } else if (!southWestPov.equals(other.southWestPov)) {
            return false;
        }
        if (westPov == null) {
            if (other.westPov != null) {
                return false;
            }
        } else if (!westPov.equals(other.westPov)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("POV[%s | %s | %s | %s | %s | %s | %s | %s]",
                northPov, southPov, eastPov, westPov, northEastPov,
                northWestPov, southEastPov, northEastPov);
    }
}
