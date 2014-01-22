package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Objects.equal;

import com.google.common.base.Objects;

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
		return Objects.hashCode(northPov, southPov, eastPov, westPov,
				northEastPov, northWestPov, southEastPov, southWestPov);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Pov) {
			final Pov pov = (Pov) obj;

			return equal(northPov, pov.northPov)
					&& equal(southPov, pov.southPov)
					&& equal(eastPov, pov.eastPov)
					&& equal(westPov, pov.westPov)
					&& equal(northEastPov, pov.northEastPov)
					&& equal(northWestPov, pov.northWestPov)
					&& equal(southEastPov, pov.southEastPov)
					&& equal(southWestPov, pov.southWestPov);
		} else {
			return false;
		}
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
