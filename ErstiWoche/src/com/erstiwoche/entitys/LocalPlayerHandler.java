package com.erstiwoche.entitys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.Main;
import com.erstiwoche.menu.LoginWaitMenu;
import com.erstiwoche.menu.MenuHandler;
import com.erstiwoche.multiplayer.Multiplayer;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;

public class LocalPlayerHandler {
	
	public static LocalPlayer localPlayer;

	public static boolean playerLoggedIn() {
		return (localPlayer != null);
	}

	public static String userNameWanted = null;

	public static void openPlayerNameInput(byte result) {
		InputName listener = new InputName();

		String extraMessage = "Enter Password";

		if (result == WarpResponseResultCode.AUTH_ERROR) {
			extraMessage = "Already logged in";
		}
		if (result == WarpResponseResultCode.UNKNOWN_ERROR) {
			extraMessage = "Unknown Error";
		}
		
		Gdx.input.getTextInput(listener, extraMessage, "");
	}

	public static void openPlayerNameInput() {
		openPlayerNameInput(WarpResponseResultCode.SUCCESS);
	}

	public static class InputName implements TextInputListener {
		
		@Override
		public void input(String text) {
			userNameWanted = text;
			Multiplayer.goOnline(text);
		}

		@Override
		public void canceled() {
			MenuHandler.setActivMenu(new LoginWaitMenu());
			Main.getInstance().goOnlineRandomName();
		}
	}

}
