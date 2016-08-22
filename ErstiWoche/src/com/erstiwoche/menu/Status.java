package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.List;
import com.erstiwoche.helper.Auth;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;

public class Status implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
	GUIButton activButton;

	public GUIButton pause = new GUIButton("Kaffe Pause", "stationPause", 0, 0, 0, 0);
	public GUIButton geschlossen = new GUIButton("Geschlossen", "stationGeschlossen", 0, 0, 0, 0);
	public static GUIButton offen = new GUIButton("Offen", "stationOffen", 0, 0, 0, 0);

	public Room room;

	public Status(Room room) {
		this.room = room;

		buttons = new ArrayList<GUIButton>();

		buttons.add(geschlossen);
		buttons.add(offen);
		buttons.add(pause);

		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
		Multiplayer.joinLobby();
	}

	@Override
	public void renderCall() {
		MenuHandler.renderButtons(this, buttons);
	}

	public void enter() {
		if (activButton != null) {
			if (activButton != back) {
				Multiplayer.updateProp(room.id,getClass().getSimpleName(), activButton.texture);
				Multiplayer.updateRoomStatus();
			}
			Multiplayer.joinRoom(room.id);	//selbes wie setActivMenu
//			MenuHandler.setActivMenu(room, false);	
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