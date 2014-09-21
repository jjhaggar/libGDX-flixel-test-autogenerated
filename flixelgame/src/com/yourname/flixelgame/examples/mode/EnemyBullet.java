package com.yourname.flixelgame.examples.mode;

import org.flixel.*;

public class EnemyBullet extends FlxSprite
{
	private String ImgBullet = "examples/mode/pack:bot_bullet";
	private String SndHit = "examples/mode/jump.mp3";
	private String SndShoot = "examples/mode/enemy.mp3";
	
	private FlxSound _sfxHit;
	private FlxSound _sfxShoot;
	
	public float speed;

	public EnemyBullet()
	{
		super();
		loadGraphic(ImgBullet,true);
		addAnimation("idle",new int[]{0, 1}, 50);
		addAnimation("poof",new int[]{2, 3, 4}, 50, false);
		speed = 120;
		
		_sfxHit = new FlxSound().loadEmbedded(SndHit, false, false, FlxSound.SFX);
		_sfxShoot = new FlxSound().loadEmbedded(SndShoot, false, false, FlxSound.SFX);
	}

	@Override
	public void update()
	{
		if(!alive)
		{
			if(finished)
				exists = false;
		}
		else if(touching > NONE)
			kill();
	}

	@Override
	public void kill()
	{
		if(!alive)
			return;
		velocity.x = 0;
		velocity.y = 0;
		if(onScreen())
			_sfxHit.play(true);
		alive = false;
		setSolid(false);
		play("poof");
	}

	public void shoot(FlxPoint Location, float Angle)
	{
		_sfxShoot.play(true);
		super.reset(Location.x-width/2,Location.y-height/2);
		FlxU.rotatePoint(0,(int) speed,0,0,Angle,_point);
		velocity.x = _point.x;
		velocity.y = _point.y;
		setSolid(true);
		play("idle");
	}
}