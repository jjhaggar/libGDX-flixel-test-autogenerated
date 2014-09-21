package com.yourname.flixelgame.examples.mode;

import org.flixel.FlxCamera;
import org.flixel.FlxGame;

/**
 * A fully featured game, with enemies and procedural level generation.
 * 
 * @author Adam Atomic
 * @author Thomas Weston
 */
public class Mode extends FlxGame
{
	public Mode()
	{
		super(320, 240, MenuState.class, 2, 50, 50, false, FlxCamera.FILL_X);
	}
}
