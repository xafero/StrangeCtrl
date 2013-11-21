package com.xafero.strangectrl.cmd;

import java.awt.GraphicsDevice;
import java.awt.Robot;

public interface ICommand {

	void execute(Robot rbt, GraphicsDevice dev, float value);
}