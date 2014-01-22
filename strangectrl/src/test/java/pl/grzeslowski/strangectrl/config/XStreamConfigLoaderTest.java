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

		final String xml = "<configuration>" + "<pov>" + "<N>"
				+ "<key key=\"Q\" />" + "</N>" + "<S>" + "<key key=\"W\" />"
				+ "</S>" + "<E>" + "<key key=\"E\" />" + "</E>" + "<W>"
				+ "<key key=\"R\" />" + "</W>" + "</pov>" + "</configuration>";

		// expected
		final Key keyQ = new Key("Q");
		final Key keyW = new Key("W");
		final Key keyE = new Key("E");
		final Key keyR = new Key("R");

		final NorthPov northPov = new NorthPov(keyQ);
		final SouthPov southPov = new SouthPov(keyW);
		final EastPov eastPov = new EastPov(keyE);
		final WestPov westPov = new WestPov(keyR);

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

		final String xml = "<configuration>" + "<pov>" + "<N>"
				+ "<key key=\"Q\" />" + "</N>" + "<S>" + "<key key=\"W\" />"
				+ "</S>" + "<E>" + "<key key=\"E\" />" + "</E>" + "<W>"
				+ "<key key=\"R\" />" + "</W>" + "<NE>" + "<key key=\"T\" />"
				+ "</NE>" + "<NW>" + "<key key=\"Y\" />" + "</NW>" + "<SE>"
				+ "<key key=\"U\" />" + "</SE>" + "<SW>" + "<key key=\"I\" />"
				+ "</SW>" + "</pov>" + "</configuration>";

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
}
