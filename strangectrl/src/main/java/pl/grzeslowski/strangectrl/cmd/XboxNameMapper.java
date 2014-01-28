package pl.grzeslowski.strangectrl.cmd;


public class XboxNameMapper implements CommandNameMapper {

	@Override
	public String map(final String identifier) {
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
			return identifier;
		}
	}

}
