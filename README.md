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
	<entry key="Button 0">mouseClick 1</entry>

	<!-- Button B will open a new tab -->
	<entry key="Button 1">keyCombo CONTROL T</entry>
	
	<!-- Button X will close an existing tab -->
	<entry key="Button 2">keyCombo CONTROL W</entry>
	
and start it. 

Now you are able to quickly open and close tabs in your favourite browser 
or whatever application you want to interact with.

License
=======

See License.md

CI
==
[![Build Status](https://travis-ci.org/magx2/StrangeCtrl.png?branch=master)](https://travis-ci.org/magx2/StrangeCtrl)
