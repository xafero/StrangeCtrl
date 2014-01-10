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

    @Test
    public void configuration_with_pov_4_directions() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>"
                + "<pov>"
                + "<N><key><value>Q</value></key></N>"
                + "<S><key><value>W</value></key></S>"
                + "<E><key><value>E</value></key></E>"
                + "<W><key><value>R</value></key></W>"
                + "</pov>"
                + "</configuration>";

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Key keyE = new Key("E");
        final Key keyR = new Key("R");

        final NorthPov northPov = new NorthPov(keyQ);
        final SouthPov southPov = new SouthPov(keyW);
        final EastPov eastPov = new EastPov(keyE);
        final WestPov westPov = new WestPov(keyR);

        final Pov pov = new Pov(northPov, southPov, eastPov, westPov);

        final Configuration expected = new Configuration(pov);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test
    public void configuration_with_pov_8_directions() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>"
                + "<pov>"
                + "<N><key><value>Q</value></key></N>"
                + "<S><key><value>W</value></key></S>"
                + "<E><key><value>E</value></key></E>"
                + "<W><key><value>R</value></key></W>"
                + "<NE><key><value>T</value></key></NE>"
                + "<NW><key><value>Y</value></key></NW>"
                + "<SE><key><value>U</value></key></SE>"
                + "<SW><key><value>I</value></key></SW>"
                + "</pov>"
                + "</configuration>";

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Key keyE = new Key("E");
        final Key keyR = new Key("R");
        final Key keyT = new Key("T");
        final Key keyY = new Key("Y");
        final Key keyU = new Key("U");
        final Key keyI = new Key("I");

        final NorthPov northPov = new NorthPov(keyQ);
        final SouthPov southPov = new SouthPov(keyW);
        final EastPov eastPov = new EastPov(keyE);
        final WestPov westPov = new WestPov(keyR);
        final NorthEastPov northEastPov = new NorthEastPov(keyT);
        final NorthWestPov northWestPov = new NorthWestPov(keyY);
        final SouthEastPov southEastPov = new SouthEastPov(keyU);
        final SouthWestPov southWestPov = new SouthWestPov(keyI);

        final Pov pov = new Pov(northPov, southPov, eastPov, westPov,
                northEastPov, northWestPov, southEastPov, southWestPov);

        final Configuration expected = new Configuration(pov);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }
}
