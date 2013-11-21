package com.xafero.strangectrl.cmd;

public abstract class AbstractCmd implements ICommand {
	protected String[] args;

	public AbstractCmd(String rawArgs) {
		this.args = rawArgs.split(" ");
	}
}