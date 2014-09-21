package com.yourname.flixelgame.examples.tiledmap2;

import org.flixel.FlxCamera;
import org.flixel.FlxG;
import org.flixel.FlxGame;

/**
 * It's the playstate3 from the collision demo. This demo loads the tiles from
 * a TMX file created by Lithander.
 * 
 * @author Adam Atomic
 * @author Ka Wing Chin
 */
public class TiledMap2Demo extends FlxGame
{
	public TiledMap2Demo()
	{
		super(320, 240, PlayState.class, 2, 40, 40, false, FlxCamera.FILL_X);
		forceDebugger = true;
		FlxG.debug = true;
	}
}
