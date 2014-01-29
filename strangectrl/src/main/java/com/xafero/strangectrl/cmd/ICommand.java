package com.xafero.strangectrl.cmd;

import java.awt.GraphicsDevice;

public interface ICommand {

	void execute(final GraphicsDevice graphicsDevice, final double value);

	void executePeriodCommand(final GraphicsDevice graphicsDevice,
			final double value);

	boolean isPeriodCommand();
}