package com.yourname.flixelgame.examples.thousandparticles;

import org.flixel.FlxEmitter;
import org.flixel.FlxG;
import org.flixel.FlxState;


/**
 * A demo that test your device. It should be a piece of cake, because there is no collision.
 * 
 * @author Ka Wing Chin
 */
public class PlayState extends FlxState
{
	public String ImgColors = "examples/thousandparticles/pack:colors";

	@Override
	public void create()
	{
		FlxG.setBgColor(FlxG.BLACK);
		
		FlxEmitter dispenser = new FlxEmitter((FlxG.width - 240) / 2, -64, 1000);
		dispenser.gravity = 20;
		dispenser.setSize(240, 0);
		dispenser.setXSpeed(-50,50);
		dispenser.setYSpeed(5,20);
		dispenser.setRotation(-720,720);
		dispenser.makeParticles(ImgColors,1000,16,true, 0.5f);
		dispenser.start(false,20,0.005f);
		add(dispenser);
	}
}
