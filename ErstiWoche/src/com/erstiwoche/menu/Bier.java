package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.helper.Message;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class Bier implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "test", 0, 0, 0, 0);
	GUIButton activButton;

	public GUIButton genug = new GUIButton("Genug", "test", 0, 0, 0, 0);
	public GUIButton zuWenig = new GUIButton("Zu Wenig", "test", 0, 0, 0, 0);
	public GUIButton zuViel = new GUIButton("Zu Viel", "test", 0, 0, 0, 0);
	public GUIButton aufdemWeg = new GUIButton("Wird geliefert", "test", 0, 0, 0, 0);
	
	public Room room;

	public Bier(Room room) {
		this.room = room;
		
		buttons = new ArrayList<GUIButton>();

		buttons.add(zuWenig);
		buttons.add(zuViel);
		buttons.add(genug);

		if (AdminMenu.isPlayerAdmin()) {
			buttons.add(aufdemWeg);
		}

		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	

	@Override
	public void render(SpriteBatch batch) {
		for (GUIButton button : buttons) {
			button.render(batch);
		}
	}

	public void enter() {
		if (activButton != null) {
			if (activButton == back) {
				List<String> not = Notifications.changed.get(room.id);
				if(not!=null){
					not.remove(Notifications.BIERUPDATE);
				}
				MenuHandler.setActivMenu(room);
			}
			else{
				Multiplayer.updateProp(getClass().getSimpleName(), activButton.label);
				MenuHandler.setActivMenu(Multiplayer.activRoom);
				Multiplayer.sendMessage(Notifications.SYSTEM+Notifications.REGEX+Notifications.UPDATE+Notifications.REGEX+Notifications.BIERUPDATE);
			}
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