package pl.grzeslowski.strangectrl.cmd;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.grzeslowski.strangectrl.config.Key;
import pl.grzeslowski.strangectrl.config.NorthPov;
import pl.grzeslowski.strangectrl.config.PovDirection;
import pl.grzeslowski.strangectrl.config.SouthPov;

public class PovDirectionTest {
	@Test
	public void the_same_objects() throws Exception {

		// given
		final PovDirection pov1 = new NorthPov(new Key("x"));
		final PovDirection pov2 = pov1;

		// when
		final boolean equals1 = pov1.equals(pov2);
		final boolean equals2 = pov2.equals(pov1);

		// then
		assertThat(equals1).isTrue();
		assertThat(equals2).isTrue();
	}

	@Test
	public void not_the_same_objects_but_equals() throws Exception {

		// given
		final PovDirection pov1 = new NorthPov(new Key("x"));
		final PovDirection pov2 = new NorthPov(new Key("x"));

		// when
		final boolean equals1 = pov1.equals(pov2);
		final boolean equals2 = pov2.equals(pov1);

		// then
		assertThat(equals1).isTrue();
		assertThat(equals2).isTrue();
	}

	@Test
	public void the_same_class_but_not_equal() throws Exception {

		// given
		final PovDirection pov1 = new NorthPov(new Key("x"));
		final PovDirection pov2 = new NorthPov(new Key("y"));

		// when
		final boolean equals1 = pov1.equals(pov2);
		final boolean equals2 = pov2.equals(pov1);

		// then
		assertThat(equals1).isFalse();
		assertThat(equals2).isFalse();
	}

	@Test
	public void diffrent_class_but_the_same_keys() throws Exception {

		// given
		final PovDirection pov1 = new NorthPov(new Key("x"));
		final PovDirection pov2 = new SouthPov(new Key("x"));

		// when
		final boolean equals1 = pov1.equals(pov2);
		final boolean equals2 = pov2.equals(pov1);

		// then
		assertThat(equals1).isFalse();
		assertThat(equals2).isFalse();
	}

	@Test
	public void diffrent_class_diffrent_keys() throws Exception {

		// given
		final PovDirection pov1 = new NorthPov(new Key("x"));
		final PovDirection pov2 = new SouthPov(new Key("y"));

		// when
		final boolean equals1 = pov1.equals(pov2);
		final boolean equals2 = pov2.equals(pov1);

		// then
		assertThat(equals1).isFalse();
		assertThat(equals2).isFalse();
	}
}
