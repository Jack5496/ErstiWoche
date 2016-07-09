package com.erstiwoche.multiplayer;

import com.erstiwoche.Main;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.LobbyRequestListener;

public class LobbyListener implements LobbyRequestListener {

	@Override
	public void onGetLiveLobbyInfoDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "oneGetLiveLobbyInfoDone");
	}

	@Override
	public void onJoinLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onJoinLobbyDone");
	}

	@Override
	public void onLeaveLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onLeaveLobbyDone");
	}

	@Override
	public void onSubscribeLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSubscribeLobbyDone");
	}

	@Override
	public void onUnSubscribeLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUnSubscribeLobbyDone");
	}
	
}

