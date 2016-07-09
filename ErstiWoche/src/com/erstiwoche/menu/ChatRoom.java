package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.helper.Message;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class ChatRoom implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "test", 0, 0, 0, 0);
	GUIButton activButton;

	static GUIButton answer = new GUIButton("", null, 0, 0, 0, 0);
	static GUIButton question = new GUIButton("", null, 0, 0, 0, 0);
	
	public GUIButton chat = new GUIButton("Chat", "test", 0, 0, 0, 0);
	
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
	public void render(SpriteBatch batch) {
		for (GUIButton button : buttons) {
			button.render(batch);
		}
	}

	public static int maxMessagesToShow = 8;


	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				Notifications.changed.get(room.id).remove(Notifications.CHATUPDATE);
				MenuHandler.setActivMenu(room);
			}
			if (activButton == chat) {
				Gdx.input.getTextInput(new PrivateChatInput(), "Message", "");
			}
		}
	}

	public class PrivateChatInput implements TextInputListener {

		@Override
		public void input(String text) {
			if (AdminMenu.isPlayerAdmin()) {
				Multiplayer.updateProp(ANSWERTAG, ANSWERTAG+": " + text);
			} else {
				Multiplayer.updateProp(QUESTIONTAG, QUESTIONTAG+": " + text);
			}
			Multiplayer.sendMessage(Notifications.SYSTEM+Notifications.REGEX+Notifications.UPDATE+Notifications.REGEX+Notifications.CHATUPDATE);

		}

		@Override
		public void canceled() {

		}
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