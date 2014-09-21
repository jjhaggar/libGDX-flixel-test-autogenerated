package com.yourname.flixelgame.examples.userinterface;

import org.flixel.FlxCamera;
import org.flixel.FlxGame;

/**
 *
 * @author Ka Wing Chin
 */
public class UserInterfaceDemo extends FlxGame
{
	public UserInterfaceDemo()
	{
		super(320, 480, Test.class, 1, 30, 30, true, FlxCamera.FILL_Y);
		forceDebugger = true;
	}
}

