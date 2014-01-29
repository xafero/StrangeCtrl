package pl.grzeslowski.strangectrl.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: it can be refactored as PovNameMapper - one abstract class!
public class XboxNameMapper implements CommandNameMapper {
	private static final Pattern VALID_IDENTIFIER = Pattern.compile("\\d");

	@Override
	public boolean canMap(final String identifier, final double value) {
		final Matcher matcher = VALID_IDENTIFIER.matcher(identifier);

		return matcher.matches();
	}

	@Override
	public String map(final String identifier, final double value) {
		switch (identifier) {
		case "0":
			return "A";
		case "1":
			return "B";
		case "2":
			return "X";
		case "3":
			return "Y";
		case "4":
			return "LB";
		case "5":
			return "RB";
		case "6":
			return "BACK";
		case "7":
			return "START";
		case "8":
			return "LS";
		case "9":
			return "RS";
		default:
			throw new RuntimeException("Cannot map this {" + identifier + "}!");
		}
	}

}
