package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;

public class MenuHandler {

	private static MenuInterface activMenu;

	public MenuHandler() {
		activMenu = new LoginWaitMenu();
	}

	public static void setActivMenu(MenuInterface menu) {
		activMenu = menu;
	}

	static GUIButton fps = new GUIButton("", null, 90, 90, 10, 10);
	static GUIButton logo = new GUIButton("", "logo", 10, 90, 10, 10);

	public static void render(SpriteBatch batch) {
		activMenu.render(batch);

		fps.label = Gdx.graphics.getFramesPerSecond() + "";
//		fps.render(batch);
//		logo.render(batch);
	}

	public static void mouseMoved(int x, int y) {
		int xpos = (int) (((float) x / (float)Main.getInstance().getWidth())*100);
		int ypos = (int) (((float) y / (float)Main.getInstance().getHeight())*100);

		activMenu.mouseMoved(xpos, ypos);
	}

	public static void clicked(int x, int y) {
		int xpos = (int) (((float) x / (float)Main.getInstance().getWidth())*100);
		int ypos = (int) (((float) y / (float)Main.getInstance().getHeight())*100);

		activMenu.clicked(xpos, ypos);
	}
	
	public static List<GUIButton> setButtonPositions(List<GUIButton> buttons) {
		List<GUIButton> toChange = new ArrayList<GUIButton>();

		int rowAmount = (int) Math.sqrt(buttons.size()) + 1;
		if (rowAmount == Math.sqrt(buttons.size()) + 1) {
			rowAmount--;
		}

		for (int i = 0; i < buttons.size(); i++) {

			float width = 100f / rowAmount;
			float height = 100f / rowAmount;

			float xpos = i / rowAmount * width + width / 2;
			float ypos = i % rowAmount * height + height / 2;

			GUIButton b = buttons.get(i);
			b.centerPercentX = xpos;
			b.centerPercentY = ypos;
			b.percentWidth = width;
			b.percentHeight = height;
			toChange.add(b);
		}
		return toChange;
	}

}