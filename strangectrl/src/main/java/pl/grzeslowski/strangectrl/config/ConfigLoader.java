package pl.grzeslowski.strangectrl.config;

public interface ConfigLoader {
    String createXml(final Configuration configuration);

    Configuration loadXml(final String xml);
}
