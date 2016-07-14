package com.erstiwoche.multiplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.erstiwoche.Main;
import com.erstiwoche.menu.AdminMenu;
import com.erstiwoche.menu.MainMenu;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class ZoneListener implements ZoneRequestListener {
	public static ZoneListener instance;

	public ZoneListener() {
		instance = this;
	}

	@Override
	public void onCreateRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onCreateRoomDone");
	}

	@Override
	public void onDeleteRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onDeleteRoomDone");
	}

	@Override
	public void onGetAllRoomsCountDone(AllRoomsEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetAllRoomsCountDone");
	}

	@Override
	public void onGetAllRoomsDone(AllRoomsEvent arg0) {
		List<String> rooms = new ArrayList<String>(Arrays.asList(arg0.getRoomIds()));
		MainMenu.allRoomsRecieved(rooms);
		Main.log(getClass(), "onGetAllRoomsDone");
	}

	@Override
	public void onGetLiveUserInfoDone(LiveUserInfoEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetLiveUserInfoDone");
	}

	@Override
	public void onGetMatchedRoomsDone(MatchedRoomsEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetMatchedRoomsDone");
	}

	@Override
	public void onGetOnlineUsersCountDone(AllUsersEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetOnlineUsersCountDone");
	}

	@Override
	public void onGetOnlineUsersDone(AllUsersEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetOnlineUsersDone");
	}

	@Override
	public void onGetUserStatusDone(LiveUserInfoEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetUserStatusDone");
	}

	@Override
	public void onSetCustomUserDataDone(LiveUserInfoEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSetCustomUserDataDone");
	}

}
