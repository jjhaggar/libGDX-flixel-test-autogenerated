package com.yourname.flixelgame;
import org.flixel.FlxDesktopApplication;

import com.yourname.flixelgame.examples.bomberplanet.ESC;

public class BomberPlanetDemo
{
	public static void main(String[] args) 
	{   
		new FlxDesktopApplication(new ESC(), 512, 512);
	}

}
