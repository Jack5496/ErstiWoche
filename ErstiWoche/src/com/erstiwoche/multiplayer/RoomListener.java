package com.erstiwoche.multiplayer;

import java.util.HashMap;

import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.menu.ListeTeamsAuf;
import com.erstiwoche.menu.MainMenu;
import com.erstiwoche.menu.MenuHandler;
import com.erstiwoche.menu.Room;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

public class RoomListener implements RoomRequestListener {

	@Override
	public void onGetLiveRoomInfoDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
//		Main.log(getClass(), "onGetLiveRoomInfoDone");

		MainMenu.updateRoomInformation(arg0);
		updateTeamListPropertie(arg0);
	}

	public void updateTeamListPropertie(LiveRoomInfoEvent arg0) {
		if (arg0.getData() != null) {
			if (arg0.getData().getId().equals(Multiplayer.teamViewID)) {
				ListeTeamsAuf.recieveTeamListProperties(arg0.getProperties());
			}
		}
	}

	public void joinRoomDone(RoomEvent event) {
		Multiplayer.activRoom = MainMenu.rooms.get(event.getData().getId());
		Multiplayer.updateRoomInformations(event.getData().getId());
		MenuHandler.setActivMenu(Multiplayer.activRoom, true);
	}

	@Override
	public void onJoinAndSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		joinRoomDone(arg0);
	}

	@Override
	public void onJoinRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
//		Main.log(getClass(), "onJoinRoomDone");
		joinRoomDone(arg0);
	}

	@Override
	public void onLeaveAndUnsubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onLeaveAndUnsubscribeRoomDone");
	}

	@Override
	public void onLeaveRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onLeaveRoomDone");
	}

	@Override
	public void onLockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onLockPropertiesDone");
	}

	@Override
	public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSetCustomRoomDataDone");
	}

	@Override
	public void onSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSubscribeRoomDone");
	}

	@Override
	public void onUnSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUnSubscribeRoomDone");
	}

	@Override
	public void onUnlockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUnlockPropertiesDone");
	}

	@Override
	public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {
//		updateTeamListPropertie(arg0);
		MainMenu.updateRoomInformation(arg0);
	}

}
