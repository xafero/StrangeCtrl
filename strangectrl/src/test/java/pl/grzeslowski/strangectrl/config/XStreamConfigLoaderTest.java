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

        final String xml = "<configuration>" + "<button value=\"A\">"
                + "<key key=\"Q\" />" + "</button>" + "</configuration>";

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
    public void configuration_with_combo_button_with_key() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        // @formatter:off
        final String xml = "<configuration>"
                + "<button value=\"A\"  pressType=\"COMBO\">"
                + "  <key key=\"Q\" />"
                + "</button>"
                + "</configuration>";
        // @formatter:on

        // expected
        final Key key = new Key("Q");
        final Button button = new Button("A", Button.COMBO_TYPE, key);
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

        final String xml = "<configuration>" + "<button value=\"A\">"
                + "<key key=\"Q\" />" + "<key key=\"W\" />" + "</button>"
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

        final String xml = "<configuration>" + "<button value=\"A\">"
                + "<key key=\"Q\" />" + "<key key=\"W\" />" + "</button>"
                + "<button value=\"B\">" + "<key key=\"E\" />"
                + "<key key=\"W\" />" + "</button>" + "</configuration>";

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

        // @formatter:off
        final String xml = "<configuration>"
                + "<pov>"
                + "<button value=\"NP\"><key key=\"Q\" /></button>"
                + "<button value=\"SP\"><key key=\"W\" /></button>"
                + "<button value=\"EP\"><key key=\"E\" /></button>"
                + "<button value=\"WP\"><key key=\"R\" /></button>"
                + "</pov>"
                + "</configuration>";
        // @formatter:on

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Key keyE = new Key("E");
        final Key keyR = new Key("R");

        final Button northPov = new Button("NP", keyQ);
        final Button southPov = new Button("SP", keyW);
        final Button eastPov = new Button("EP", keyE);
        final Button westPov = new Button("WP", keyR);

        final Pov pov = new Pov.PovBuilder().northPov(northPov)
                .southPov(southPov).eastPov(eastPov).westPov(westPov).build();

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

        // @formatter:off
        final String xml = "<configuration>"
                + "<pov>"
                + "<button value=\"NP\"><key key=\"Q\" /></button>"
                + "<button value=\"SP\"><key key=\"W\" /></button>"
                + "<button value=\"EP\"><key key=\"E\" /></button>"
                + "<button value=\"WP\"><key key=\"R\" /></button>"
                + "<button value=\"NEP\"><key key=\"T\" /></button>"
                + "<button value=\"NWP\"><key key=\"Y\" /></button>"
                + "<button value=\"SEP\"><key key=\"U\" /></button>"
                + "<button value=\"SWP\"><key key=\"I\" /></button>"
                + "</pov>"
                + "</configuration>";
        // @formatter:on

        // expected
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");
        final Key keyE = new Key("E");
        final Key keyR = new Key("R");
        final Key keyT = new Key("T");
        final Key keyY = new Key("Y");
        final Key keyU = new Key("U");
        final Key keyI = new Key("I");

        final Button northPov = new Button("NP", keyQ);
        final Button southPov = new Button("SP", keyW);
        final Button eastPov = new Button("EP", keyE);
        final Button westPov = new Button("WP", keyR);
        final Button northEastPov = new Button("NEP", keyT);
        final Button northWestPov = new Button("NWP", keyY);
        final Button southEastPov = new Button("SEP", keyU);
        final Button southWestPov = new Button("SWP", keyI);

        final Pov pov = new Pov.PovBuilder().northPov(northPov)
                .southPov(southPov).eastPov(eastPov).westPov(westPov)
                .northEastPov(northEastPov).northWestPov(northWestPov)
                .southEastPov(southEastPov).southWestPov(southWestPov).build();

        final Configuration expected = new Configuration(pov);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test
    public void load_setup() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>" + "<setup>"
                + "<maxMouseMove>10</maxMouseMove>"
                + "<timeRefresher>100</timeRefresher>"
                + "<checkNewControllersDelay>500</checkNewControllersDelay>"
                + "<scrollLines>2</scrollLines>" + "</setup>"
                + "</configuration>";

        // expected
        final Setup setup = new Setup(10, 100, 500, 2);
        final Configuration expected = new Configuration(setup);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test
    public void load_default_setup() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration></configuration>";

        // expected
        final Setup setup = Setup.getDefaultSetup();
        final Configuration expected = new Configuration(setup);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }

    @Test
    public void load_only_part_setup() throws Exception {

        // given
        final XStreamConfigLoader loader = new XStreamConfigLoader();

        final String xml = "<configuration>" + "<setup>"
                + "<checkNewControllersDelay>500</checkNewControllersDelay>"
                + "<scrollLines>2</scrollLines>" + "</setup>"
                + "</configuration>";

        // expected
        final Setup setup = new Setup(15, 10, 500, 2);
        final Configuration expected = new Configuration(setup);

        // when
        final Configuration loadXml = loader.loadXml(xml);

        // then
        assertThat(loadXml).isEqualTo(expected);
    }
}
