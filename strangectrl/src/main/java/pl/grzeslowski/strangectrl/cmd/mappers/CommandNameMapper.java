package pl.grzeslowski.strangectrl.cmd.mappers;

public interface CommandNameMapper {
    boolean canMap(final String identifier, final double value);

    String map(final String identifier, final double value);
}
