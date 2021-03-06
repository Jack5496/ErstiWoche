package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.erstiwoche.Main;
import com.erstiwoche.helper.Auth;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.multiplayer.Notifications;
import com.erstiwoche.uiElements.GUIButton;

public class Bier implements MenuInterface {

	List<GUIButton> buttons;
	GUIButton back = new GUIButton("Back", "exit", 0, 0, 0, 0);
	GUIButton activButton;

	public static GUIButton genug = new GUIButton("Genug", "bier", 0, 0, 0, 0);
	public static GUIButton zuWenig = new GUIButton("Zu Wenig", "bierZuWenig", 0, 0, 0, 0);
	public static GUIButton zuViel = new GUIButton("Zu Viel", "bier", 0, 0, 0, 0);
	public static GUIButton aufdemWeg = new GUIButton("Wird geliefert", "bierWirdGeliefert", 0, 0, 0, 0);
	
	public Room room;

	public Bier(Room room) {
		this.room = room;
		
		buttons = new ArrayList<GUIButton>();

		buttons.add(zuWenig);
		buttons.add(zuViel);
		buttons.add(genug);

		if (Auth.isPlayerAdmin()) {
			buttons.add(aufdemWeg);
		}

		buttons.add(back);
		buttons = MenuHandler.setButtonPositions(buttons);
	}

	

	@Override
	public void renderCall() {
		MenuHandler.renderButtons(this,buttons);
	}

	public void enter() {
		
		
		if (activButton != null) {
			if (activButton == back) {
				List<String> not = Notifications.changed.get(room.id);
				if(not!=null){
					not.remove(Notifications.BIERUPDATE);
				}
				MenuHandler.setActivMenu(room,false);
			}
			else{
				Multiplayer.updateProp(getClass().getSimpleName(), activButton.label);
				MenuHandler.setActivMenu(Multiplayer.activRoom,true);
				Multiplayer.sendMessage(Notifications.SYSTEM+Notifications.REGEX+Notifications.UPDATE+Notifications.REGEX+Notifications.BIERUPDATE);
			}
		}
	}
	
	public static boolean somethingCahnged(HashMap<String, Object> oldProps, HashMap<String, Object> newProps) {

		boolean change = false;

		if (oldProps != null && newProps != null) {
			if (oldProps.get(Notifications.BIERUPDATE) != null && newProps.get(Notifications.BIERUPDATE) != null) {
				if (!oldProps.get(Notifications.BIERUPDATE).equals(newProps.get(Notifications.BIERUPDATE)))
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