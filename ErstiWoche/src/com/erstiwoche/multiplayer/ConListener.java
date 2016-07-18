package com.erstiwoche.multiplayer;

import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.menu.LoginWaitMenu;
import com.erstiwoche.menu.MainMenu;
import com.erstiwoche.menu.MenuHandler;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConListener implements ConnectionRequestListener {

	@Override
	public void onConnectDone(ConnectEvent event) {
		Main.log(getClass(), "Connection done: " + event.getResult());

		if (event.getResult() == WarpResponseResultCode.SUCCESS) {
			LocalPlayerHandler.localPlayer = new LocalPlayer(LocalPlayerHandler.userNameWanted);
			MenuHandler.setActivMenu(new MainMenu(),true);
		}
		if (event.getResult() == WarpResponseResultCode.AUTH_ERROR) {
			LoginWaitMenu.error("Auth Error");
		}
		if (event.getResult() == WarpResponseResultCode.CONNECTION_ERROR) {
			LoginWaitMenu.error("No Internet");
			Main.log(getClass(), "Connection Error");
		}
	}

	@Override
	public void onDisconnectDone(ConnectEvent event) {
		Main.log(getClass(), "On Disconnected invoked");
	}

	@Override
	public void onInitUDPDone(byte arg0) {
		// TODO Auto-generated method stub

	}
}