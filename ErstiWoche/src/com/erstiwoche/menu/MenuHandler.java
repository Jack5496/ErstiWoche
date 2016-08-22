package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.SoundHandler;
import com.erstiwoche.uiElements.GUIButton;

public class MenuHandler {

	public static SpriteBatch batch;

	private static MenuInterface activMenu;
	private static MenuInterface lastMenu;
	private static MenuInterface nextMenu;

	public MenuHandler(SpriteBatch batch) {
		MenuHandler.batch = batch;
		activMenu = new LoginWaitMenu();
		lastMenu = activMenu;
		nextMenu = activMenu;
	}

	public static void setActivMenu(MenuInterface menu, boolean slideToLeft) {
		SoundHandler.playMenuSwitchSound();
		slideLeft = slideToLeft;
		lastMenu = activMenu;
		nextMenu = menu;
		activMenu = null;
		transitStart = System.currentTimeMillis();
	}

	public static void render() {
		if (activMenu != null) {
			activMenu.renderCall();
		} else {
			calcTransitPercent();
			if (lastMenu != null)
				lastMenu.renderCall();
			if (nextMenu != null)
				nextMenu.renderCall();
			checkTransitEnd();
		}
	}

	static float transitSeconds = 0.5f;
	static long transitTime = (long) (transitSeconds * 1000);
	static long transitStart = System.currentTimeMillis();
	static float transitPercent = calcTransitPercent();

	static boolean slideLeft = true;

	public static void renderButtons(MenuInterface menu, List<GUIButton> buttons) {
		batch.begin();

		float lastOffset = 0;
		float nextOffset = 100;
		int offset = (int) (transitPercent * 100);
		if (slideLeft) {
			nextOffset = 100;
			offset = (int) (transitPercent * 100);
		} else {
			nextOffset = -100;
			offset = -offset;
		}

		if (activMenu != null) {
			for (GUIButton button : buttons) {
				button.render(batch);
			}
		} else {

			if (menu == lastMenu) {
				for (GUIButton button : buttons) {
					button.offsetPercentX = lastOffset - offset;
					button.render(batch);
					button.offsetPercentX = 0;
				}
			} else if (menu == nextMenu) {
				for (GUIButton button : buttons) {
					button.offsetPercentX = nextOffset - offset;
					button.render(batch);
					button.offsetPercentX = 0;
				}
			}
		}

		batch.end();
	}

	static float max = 100f;
	static float k = -0.08f;
	static float c = 100;

	private static float calcTransitPercent() {
		long passedTime = System.currentTimeMillis() - transitStart;
		float percent = (float) passedTime / transitTime;
		percent *= 100;
		float part1 = (float) (max / (1f + c * Math.pow(Math.E, k * percent)));

		transitPercent = part1 / 100f;

		if (transitPercent > 0.99f) {
			transitPercent = 1;
		}

		return 0.5f;
	}

	private static void checkTransitEnd() {
		if (transitPercent >= 1) {
			slideLeft = true;
			activMenu = nextMenu;
			nextMenu = null;
			lastMenu = null;
		}
	}

	public static void mouseMoved(int x, int y) {
		int xpos = (int) (((float) x / (float) Main.getInstance().getWidth()) * 100);
		int ypos = (int) (((float) y / (float) Main.getInstance().getHeight()) * 100);

		if (activMenu != null)
			activMenu.mouseMoved(xpos, ypos);
	}

	public static void clicked(int x, int y) {
		int xpos = (int) (((float) x / (float) Main.getInstance().getWidth()) * 100);
		int ypos = (int) (((float) y / (float) Main.getInstance().getHeight()) * 100);

		if (activMenu != null)
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