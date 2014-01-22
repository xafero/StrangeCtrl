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

	private Pov() {
	}

	public NorthPov getNorthPov() {
		return northPov;
	}

	public SouthPov getSouthPov() {
		return southPov;
	}

	public EastPov getEastPov() {
		return eastPov;
	}

	public WestPov getWestPov() {
		return westPov;
	}

	public NorthEastPov getNorthEastPov() {
		return northEastPov;
	}

	public NorthWestPov getNorthWestPov() {
		return northWestPov;
	}

	public SouthEastPov getSouthEastPov() {
		return southEastPov;
	}

	public SouthWestPov getSouthWestPov() {
		return southWestPov;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (eastPov == null ? 0 : eastPov.hashCode());
		result = prime * result
				+ (northEastPov == null ? 0 : northEastPov.hashCode());
		result = prime * result + (northPov == null ? 0 : northPov.hashCode());
		result = prime * result
				+ (northWestPov == null ? 0 : northWestPov.hashCode());
		result = prime * result
				+ (southEastPov == null ? 0 : southEastPov.hashCode());
		result = prime * result + (southPov == null ? 0 : southPov.hashCode());
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

	public static class PovBuilder {

		private NorthPov northPov;
		private SouthPov southPov;
		private EastPov eastPov;
		private WestPov westPov;
		private NorthEastPov northEastPov;
		private NorthWestPov northWestPov;
		private SouthEastPov southEastPov;
		private SouthWestPov southWestPov;

		public Pov build() {
			final Pov pov = new Pov();

			pov.northPov = northPov;
			pov.southPov = southPov;
			pov.eastPov = eastPov;
			pov.westPov = westPov;
			pov.northEastPov = northEastPov;
			pov.northWestPov = northWestPov;
			pov.southEastPov = southEastPov;
			pov.southWestPov = southWestPov;

			return pov;
		}

		public PovBuilder northPov(final NorthPov northPov) {
			this.northPov = northPov;
			return this;
		}

		public PovBuilder southPov(final SouthPov southPov) {
			this.southPov = southPov;
			return this;
		}

		public PovBuilder eastPov(final EastPov eastPov) {
			this.eastPov = eastPov;
			return this;
		}

		public PovBuilder westPov(final WestPov westPov) {
			this.westPov = westPov;
			return this;
		}

		public PovBuilder northEastPov(final NorthEastPov northEastPov) {
			this.northEastPov = northEastPov;
			return this;
		}

		public PovBuilder northWestPov(final NorthWestPov northWestPov) {
			this.northWestPov = northWestPov;
			return this;
		}

		public PovBuilder southEastPov(final SouthEastPov southEastPov) {
			this.southEastPov = southEastPov;
			return this;
		}

		public PovBuilder southWestPov(final SouthWestPov southWestPov) {
			this.southWestPov = southWestPov;
			return this;
		}
	}
}
