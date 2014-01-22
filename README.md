StrangeCtrl
===========

A very strange control for your desktop as it simulates mouse and keyboard input.

Why?
====

* You got a gamepad (an XBOX controller for example) and an old game not recognizing it
* Due to some reason, you are not capable of using a keyboard and/or a mouse

Quickstart
==========

Just configure it like this (see config.xml), for example:

	<!-- Button A means now left mouse click -->
	<button value="RB">
		<key key="LEFT_MOUSE" />
	</button>

	<!-- Button B will open a new tab -->
	<button value="A">
		<key key="ENTER" />
	</button>
	
	<!-- Button X will close an existing tab -->
	<button value="BACK">
		<key key="CONTROL" />
		<key key="SHIFT" />
		<key key="T" />
	</button>
	
and start it. 

Now you are able to quickly open and close tabs in your favourite browser 
or whatever application you want to interact with.

config.xml for browsing internet
================================

	<configuration>
		<button value="A">
			<key key="ENTER" />
		</button>
		<button value="B">
			<key key="BACK_SPACE" />
		</button>
		<button value="X">
			<key key="ESCAPE" />
		</button>
		<button value="Y">
			<key key="ALT" />
			<key key="TAB" />
		</button>
		<button value="RB">
			<key key="LEFT_MOUSE" />
		</button>
		<button value="LB">
			<key key="RIGHT_MOUSE" />
		</button>
		<button value="START">
			<key key="CONTROL" />
			<key key="T" />
		</button>
		<button value="BACK">
			<key key="CONTROL" />
			<key key="SHIFT" />
			<key key="T" />
		</button>
		<button value="RS">
			<key key="CENTER_MOUSE" />
		</button>
		<button value="LS">
			<key key="CONTROL" />
			<key key="W" />
		</button>
		<pov>
			<N>
				<key key="UP" />
			</N>
			<S>
				<key key="DOWN" />
			</S>
			<E>
				<key key="RIGHT" />
			</E>
			<W>
				<key key="LEFT" />
			</W>
			<NE>
				<key key="UP" />
				<key key="RIGHT" />
			</NE>
			<NW>
				<key key="UP" />
				<key key="LEFT" />
			</NW>
			<SE>
				<key key="DOWN" />
				<key key="RIGHT" />
			</SE>
			<SW>
				<key key="DOWN" />
				<key key="LEFT" />
			</SW>
		</pov>
	</configuration>

License
=======

See License.md

CI
==
[![Build Status](https://travis-ci.org/magx2/StrangeCtrl.png?branch=master)](https://travis-ci.org/magx2/StrangeCtrl)
