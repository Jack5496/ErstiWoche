package com.erstiwoche.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.multiplayer.Multiplayer;
import com.erstiwoche.uiElements.GUIButton;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class AdminMenu{

	public static final String ADMINNAME = "Admin";

	public static boolean isPlayerAdmin() {
		return LocalPlayerHandler.localPlayer.name.equals(ADMINNAME);
	}

}