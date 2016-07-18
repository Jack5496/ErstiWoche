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
		Main.log(getClass(), "onGetLiveRoomInfoDone");

		MainMenu.roomNameRecieved(arg0.getData().getId(), arg0.getData().getName());
		updateTeamListPropertie(arg0);

		Room room = Multiplayer.activRoom;

		if (room != null) {
			if (room.id.equals(arg0.getData().getId())) {
				if (arg0.getJoinedUsers() != null) {
					room.setJoinedUsers(arg0.getJoinedUsers());
				}
				HashMap<String, Object> prop = arg0.getProperties();
				if (prop != null) {
					room.updateProperties(arg0.getData().getId(), prop);
				}
			}
		}
	}

	public void updateTeamListPropertie(LiveRoomInfoEvent arg0) {
		if (arg0.getData() != null) {
			if (arg0.getData().getId().equals(Multiplayer.teamViewID)) {
				ListeTeamsAuf.recieveTeamListProperties(arg0.getProperties());
			}
		}
	}

	public void joinRoomDone(RoomEvent event) {
		Multiplayer.activRoom = new Room(event.getData());
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
		Main.log(getClass(), "onJoinRoomDone");
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
		Main.log(getClass(), LocalPlayerHandler.localPlayer.name + ": onUpdatePropertyDone");

		updateTeamListPropertie(arg0);

		HashMap<String, Object> prop = arg0.getProperties();
		Room room = Multiplayer.activRoom;

		if (room != null) {
			if (prop != null) {
				room.updateProperties(arg0.getData().getId(), prop);
			}
		}
	}

}
