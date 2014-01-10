package pl.grzeslowski.strangectrl.config;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class XStreamConfigLoaderTest {
    @Test
    public void empty_configuration() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration></configuration>";

        // expected
        final Configuration expected = new Configuration();

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test(expected = NullPointerException.class)
    public void null_in_parameter() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        // when
        loader.loadXml(null);
    }

    @Test
    public void configuration_with_button_with_key() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>"
                + "<button value=\"A\">"
                + "<key>"
                + "<value>Q</value>"
                + "</key>"
                + "</button>"
                + "</configuration>";

        // expected
        final Key key = new Key("Q");
        final Button button = new Button("A", key);
        final Configuration expected = new Configuration(button);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test
    public void configuration_with_button_with_2_keys() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>"
                + "<button value=\"A\">"
                + "<key>"
                + "<value>Q</value>"
                + "</key>"
                + "<key>"
                + "<value>W</value>"
                + "</key>"
                + "</button>"
                + "</configuration>";

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Button button = new Button("A", keyQ, keyW);
        final Configuration expected = new Configuration(button);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    public void configuration_with_2_buttons_with_2_keys() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>"
                + "<button value=\"A\">"
                + "<key>"
                + "<value>Q</value>"
                + "</key>"
                + "<key>"
                + "<value>W</value>"
                + "</key>"
                + "</button>"
                + "<button value=\"B\">"
                + "<key>"
                + "<value>E</value>"
                + "</key>"
                + "<key>"
                + "<value>W</value>"
                + "</key>"
                + "</button>"
                + "</configuration>";

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Key keyE = new Key("E");
        final Button buttonA = new Button("A", keyQ, keyW);
        final Button buttonB = new Button("A", keyE, keyW);
        final Configuration expected = new Configuration(buttonA, buttonB);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }
}
