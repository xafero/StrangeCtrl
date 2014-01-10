package com.xafero.strangectrl.cmd;

import java.awt.GraphicsDevice;

public interface ICommand {

	void execute(final GraphicsDevice graphicsDevice, final double value);
}