package com.yourname.flixelgame;

import org.flixel.FlxGame;

public class FlixelGame extends FlxGame
{
	public FlixelGame()
	{
		super(432, 240, PlayState.class, 3, 50, 50, false);
	}
}
