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
}
