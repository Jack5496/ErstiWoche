package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.helper.Auth;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;

public class ChatRoom implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
	GUIButton activButton;

	static GUIButton answer = new GUIButton("", "chat", 0, 0, 0, 0);
	static GUIButton question = new GUIButton("", "student", 0, 0, 0, 0);

	public GUIButton chat = new GUIButton("Chat", "openChat", 0, 0, 0, 0);

	public static String QUESTIONTAG = "Q";
	public static String ANSWERTAG = "A";

	public Room room;

	public ChatRoom(Room room) {
		this.room = room;

		buttons = new ArrayList<GUIButton>();

		buttons.add(chat);
		buttons.add(back);
		buttons.add(answer);
		buttons.add(question);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	@Override
	public void renderCall() {
		MenuHandler.renderButtons(this, buttons);
	}

	public static int maxMessagesToShow = 8;

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				List<String> not = Notifications.changed.get(room.id);
				if (not != null) {
					not.remove(Notifications.CHATUPDATE);
					Notifications.changed.put(room.id, not);
				}
				MenuHandler.setActivMenu(room, false);
			}
			if (activButton == chat) {
				Gdx.input.getTextInput(new PrivateChatInput(), "Message", "");
			}
		}
	}

	public class PrivateChatInput implements TextInputListener {

		@Override
		public void input(String text) {
			if (Auth.isPlayerAdmin()) {
				Multiplayer.updateProp(ANSWERTAG, ANSWERTAG + ": " + text);
			} else {
				Multiplayer.updateProp(QUESTIONTAG, QUESTIONTAG + ": " + text);
			}
			Multiplayer.sendMessage(Notifications.SYSTEM + Notifications.REGEX + Notifications.UPDATE
					+ Notifications.REGEX + Notifications.CHATUPDATE);

		}

		@Override
		public void canceled() {

		}
	}

	public static boolean somethingCahnged(HashMap<String, Object> oldProps, HashMap<String, Object> newProps) {

		boolean change = false;

		if (oldProps != null && newProps != null) {
			if (oldProps.get(ANSWERTAG) != null && newProps.get(ANSWERTAG) != null) {
				if (!oldProps.get(ANSWERTAG).equals(newProps.get(ANSWERTAG)))
					change = true;
			}
			if (oldProps.get(QUESTIONTAG) != null && newProps.get(QUESTIONTAG) != null) {
				if (!oldProps.get(QUESTIONTAG).equals(newProps.get(QUESTIONTAG)))
					change = true;
			}
		}

		return change;
	}

	@Override
	public void clicked(int x, int y) {
		mouseMoved(x, y);
		enter();
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (activButton != null) {
			activButton.setHovered(false);
		}
		activButton = null;
		for (GUIButton button : buttons) {
			button.pressAt(x, y);
			if (button.isPressed()) {
				activButton = button;
				activButton.setHovered(true);
			}
		}
	}

}