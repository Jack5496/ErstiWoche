package com.erstiwoche.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface MenuInterface {

	public void render(SpriteBatch batch);
	public void clicked(int x, int y);
	public void mouseMoved(int x, int y);
}