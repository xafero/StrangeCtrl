package pl.grzeslowski.strangectrl.cmd;

public interface CommandNameMapper {
	boolean canMap(final String identifier, final double value);

	String map(final String identifier, final double value);
}
