package com.yourname.flixelgame.examples.flxtilemap;

import org.flixel.*;
import org.flixel.FlxTilemap;
import org.flixel.event.IFlxButton;

public class PlayState extends FlxState
{
	// Tileset that works with AUTO mode (best for thin walls)
	private static String auto_tiles = "examples/flxtilemap/pack:auto_tiles";

	// Tileset that works with ALT mode (best for thicker walls)
	private static String alt_tiles = "examples/flxtilemap/pack:alt_tiles";

	// Tileset that works with OFF mode (do what you want mode)
	private static String empty_tiles = "examples/flxtilemap/pack:empty_tiles";

	// Default tilemaps. Embedding text files is a little weird.
	private static String default_auto = "examples/flxtilemap/default_auto.txt";
	private static String default_alt ="examples/flxtilemap/default_alt.txt";
	private static String default_empty = "examples/flxtilemap/default_empty.txt";

	private static String ImgSpaceman = "examples/flxtilemap/pack:spaceman";
	
	// Some static constants for the size of the tilemap tiles
	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	// The FlxTilemap we're using
	private FlxTilemap collisionMap;

	// Box to show the user where they're placing stuff
	private FlxObject highlightBox;

	// Player modified from "Mode" demo
	private FlxSprite player;

	// Some interface buttons and text
	private FlxButton autoAltBtn;
	private FlxButton resetBtn;
	private FlxText helperTxt;

	@Override
	public void create()
	{
		FlxG.setBgColor(0xff131c1b);
		
		FlxG.setFramerate(50);
		FlxG.setFlashFramerate(50);

		// Creates a new tilemap with no arguments
		collisionMap = new FlxTilemap();

		/*
		 * FlxTilemaps are created using strings of comma seperated values (csv)
		 * This string ends up looking something like this:
		 *
		 * 0,0,0,0,0,0,0,0,0,0,
		 * 0,0,0,0,0,0,0,0,0,0,
		 * 0,0,0,0,0,0,1,1,1,0,
		 * 0,0,1,1,1,0,0,0,0,0,
		 * ...
		 *
		 * Each '0' stands for an empty tile, and each '1' stands for
		 * a solid tile
		 *
		 * When using the auto map generation, the '1's are converted into the corresponding frame
		 * in the tileset.
		 */

		// Initializes the map using the generated string, the tile images, and the tile size
		collisionMap.loadMap(FlxG.loadString(default_auto), auto_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.AUTO);
		add(collisionMap);

		highlightBox = new FlxObject(0, 0, TILE_WIDTH, TILE_HEIGHT);

		setupPlayer();

		// When switching between modes here, the map is reloaded with it's own data, so the positions of tiles are kept the same
		// Notice that different tilesets are used when the auto mode is switched
		autoAltBtn = new FlxButton(4, FlxG.height - 24, "AUTO", new IFlxButton(){@Override public void callback()
		{
			switch(collisionMap.auto)
			{
				case FlxTilemap.AUTO:
					collisionMap.loadMap(FlxTilemap.arrayToCSV(collisionMap.getData(true), collisionMap.widthInTiles),
						alt_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.ALT);
					autoAltBtn.label.setText("ALT");
					break;

				case FlxTilemap.ALT:
					collisionMap.loadMap(FlxTilemap.arrayToCSV(collisionMap.getData(true), collisionMap.widthInTiles),
						empty_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.OFF);
					autoAltBtn.label.setText("OFF");
					break;

				case FlxTilemap.OFF:
					collisionMap.loadMap(FlxTilemap.arrayToCSV(collisionMap.getData(true), collisionMap.widthInTiles),
						auto_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.AUTO);
					autoAltBtn.label.setText("AUTO");
					break;
			}

		}});
		add(autoAltBtn);

		resetBtn = new FlxButton(8 + autoAltBtn.width, FlxG.height - 24, "Reset", new IFlxButton(){@Override public void callback()
		{
			switch(collisionMap.auto)
			{
				case FlxTilemap.AUTO:
					collisionMap.loadMap(FlxG.loadString(default_auto), auto_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.AUTO);
					player.x = 64;
					player.y = 220;
					break;

				case FlxTilemap.ALT:
					collisionMap.loadMap(FlxG.loadString(default_alt), alt_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.ALT);
					player.x = 64;
					player.y = 128;
					break;

				case FlxTilemap.OFF:
					collisionMap.loadMap(FlxG.loadString(default_empty), empty_tiles, TILE_WIDTH, TILE_HEIGHT, FlxTilemap.OFF);
					player.x = 64;
					player.y = 64;
					break;
			}
		}});
		add(resetBtn);

		helperTxt = new FlxText(12 + autoAltBtn.width*2, FlxG.height - 30, 150, "Click to place tiles\nShift-Click to remove tiles\nArrow keys to move");
		add(helperTxt);
	}

	@Override
	public void update()
	{
		// Tilemaps can be collided just like any other FlxObject, and flixel
		// automatically collides each individual tile with the object.
		FlxG.collide(player, collisionMap);

		highlightBox.x = (float) (Math.floor(FlxG.mouse.x / TILE_WIDTH) * TILE_WIDTH);
		highlightBox.y = (float) (Math.floor(FlxG.mouse.y / TILE_HEIGHT) * TILE_HEIGHT);

		if (FlxG.mouse.pressed())
		{
			// FlxTilemaps can be manually edited at runtime as well.
			// Setting a tile to 0 removes it, and setting it to anything else will place a tile.
			// If auto map is on, the map will automatically update all surrounding tiles.
			collisionMap.setTile((int)FlxG.mouse.x / TILE_WIDTH, (int)FlxG.mouse.y / TILE_HEIGHT, FlxG.keys.SHIFT?0:1);
		}

		updatePlayer();
		super.update();
	}

	@Override
	public void draw()
	{
		super.draw();
		highlightBox.drawDebug();
	}

	private void setupPlayer()
	{
		player = new FlxSprite(64, 220);
		player.loadGraphic(ImgSpaceman, true, true, 16);

		//bounding box tweaks
		player.width = 14;
		player.height = 14;
		player.offset.x = 1;
		player.offset.y = 1;

		//basic player physics
		player.drag.x = 640;
		player.acceleration.y = 420;
		player.maxVelocity.x = 80;
		player.maxVelocity.y = 200;

		//animations
		player.addAnimation("idle", new int[]{0});
		player.addAnimation("run", new int[]{1, 2, 3, 0}, 12);
		player.addAnimation("jump", new int[]{4});

		add(player);
	}

	private void updatePlayer()
	{
		wrap(player);

		//MOVEMENT
		player.acceleration.x = 0;
		if(FlxG.keys.LEFT)
		{
			player.setFacing(FlxObject.LEFT);
			player.acceleration.x -= player.drag.x;
		}
		else if(FlxG.keys.RIGHT)
		{
			player.setFacing(FlxObject.RIGHT);
			player.acceleration.x += player.drag.x;
		}
		if(FlxG.keys.justPressed("UP") && player.velocity.y == 0)
		{
			player.y -= 1;
			player.velocity.y = -200;
		}

		//ANIMATION
		if(player.velocity.y != 0)
		{
			player.play("jump");
		}
		else if(player.velocity.x == 0)
		{
			player.play("idle");
		}
		else
		{
			player.play("run");
		}
	}

	private void wrap(FlxObject obj)
	{
		obj.x = (obj.x + obj.width / 2 + FlxG.width) % FlxG.width - obj.width / 2;
		obj.y = (obj.y + obj.height / 2) % FlxG.height - obj.height / 2;
	}
}