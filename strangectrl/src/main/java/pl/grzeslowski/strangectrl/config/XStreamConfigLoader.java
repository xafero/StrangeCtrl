package pl.grzeslowski.strangectrl.config;

import static com.google.common.base.Preconditions.checkNotNull;

import com.thoughtworks.xstream.XStream;

public class XStreamConfigLoader implements ConfigLoader {

    private final XStream xstream = new XStream();

    public XStreamConfigLoader() {

        // creating aliases
        xstream.alias("configuration", Configuration.class);
        xstream.alias("button", Button.class);
        xstream.alias("key", Key.class);
        xstream.alias("setup", Setup.class);

        xstream.alias("pov", Pov.class);
        xstream.alias("pov_button", Button.class);

        // creating implicit collections
        xstream.addImplicitCollection(Configuration.class, "buttons");
        xstream.addImplicitCollection(Pov.class, "povDirections");
        xstream.addImplicitCollection(Button.class, "keys", "key", Key.class);

        // attributes
        xstream.useAttributeFor(Button.class, "value");
        xstream.useAttributeFor(Button.class, "pressType");
        xstream.useAttributeFor(Key.class, "key");
    }

    @Override
    public String createXml(final Configuration configuration) {
        throw new UnsupportedOperationException("Not yet impl!");
    }

    @Override
    public Configuration loadXml(final String xml) {
        checkNotNull(xml);
        return (Configuration) xstream.fromXML(xml);
    }
}
