package com.yourname.flixelgame.examples.mode;

import org.flixel.*;
import org.flixel.event.IFlxCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;

public class VictoryState extends FlxState
{
	private String ImgGibs = "examples/mode/pack:spawner_gibs";
	private String SndMenu = "examples/mode/menu_hit_2.mp3";
	private String ImgFont20 = "examples/mode/nokiafc.fnt";
	
	private float _timer;
	private boolean _fading;

	@Override
	public void create()
	{
		_timer = 0;
		_fading = false;
		FlxG.flash(0xffd8eba2);

		//Gibs emitted upon death
		FlxEmitter gibs = new FlxEmitter(0,-50);
		gibs.setSize(FlxG.width,0);
		gibs.setXSpeed();
		gibs.setYSpeed(0,100);
		gibs.setRotation(-360,360);
		gibs.gravity = 80;
		gibs.makeParticles(ImgGibs,800,32,true,0);
		add(gibs);
		gibs.start(false,0,0.005f);

		FlxText text = new FlxText(0,FlxG.height/2-35,FlxG.width,"VICTORY\n\nSCORE: "+FlxG.score);
		if(Gdx.app.getType() == ApplicationType.WebGL)
			text.setFormat(ImgFont20,20,0xd8eba2,"center");
		else
			text.setFormat(null,16,0xd8eba2,"center");
		add(text);
	}

	@Override
	public void update()
	{
		super.update();
		if(!_fading)
		{
			_timer += FlxG.elapsed;
			if((_timer > 0.35) && ((_timer > 10) || FlxG.keys.justPressed("X") || FlxG.keys.justPressed("C")))
			{
				_fading = true;
				FlxG.play(SndMenu, 1f, false, false);
				FlxG.fade(0xff131c1b,2,new IFlxCamera(){@Override public void callback(){ onPlay();}});
			}
		}
	}

	private void onPlay() 
	{
		FlxG.switchState(new PlayState());
	}
}