package pl.grzeslowski.strangectrl.cmd;

public class PovNameMapper implements CommandNameMapper {

	private static final String RELEASE_POV = "RELEASE_POV";

	@Override
	public String map(final String identifier) {
		switch (identifier) {
		case "0":
			return RELEASE_POV;
		case "0.125":
			return "NWP";
		case "0.25":
			return "NP";
		case "0.375":
			return "NEP";
		case "0.5":
			return "EP";
		case "0.625":
			return "SEP";
		case "0.75":
			return "SP";
		case "0.875":
			return "SWP";
		case "1":
			return "WP";
		default:
			return identifier;
		}
	}

}
