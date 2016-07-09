package com.erstiwoche.multiplayer;

import java.util.HashMap;
import java.util.List;

import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.helper.Message;
import com.erstiwoche.menu.Room;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class Multiplayer {

	private static WarpClient warpClient = null;

	private static ChatListener chatListener;
	
	public static String teamViewID = "1096573283";

	public static Room activRoom;

	public Multiplayer() {

		WarpClient.initialize("2adae33df7d430836bcefcc14744911e7861567d38c5088c244df795b41aa91c",
				"798d28671f78f01651340bb5c3d40f5e3a0f313dc5a90df28508f8791b3c1ea6");

		try {
			warpClient = WarpClient.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addListeners();
	}

	public void addListeners() {
		chatListener = new ChatListener();
		warpClient.addChatRequestListener(chatListener);

		warpClient.addConnectionRequestListener(new ConListener());
		warpClient.addLobbyRequestListener(new LobbyListener());
		warpClient.addNotificationListener(new Notifications());
		warpClient.addRoomRequestListener(new RoomListener());
		warpClient.addTurnBasedRoomListener(new TurnListener());
		warpClient.addUpdateRequestListener(new UpdateListener());
		warpClient.addZoneRequestListener(new ZoneListener());
	}

	public static void goOnline(String name) {
		warpClient.connectWithUserName(name);
	}

	public static void joinRoom(String roomID) {
			warpClient.joinAndSubscribeRoom(roomID);
	}

	public static void getAllRooms() {
		warpClient.getAllRooms();
	}
	
	public static void updateRoomInfromations(){
		if(Multiplayer.activRoom!=null){
			updateRoomInformations(activRoom.id);
		}
	}
	
	public static void subcribeRoom(String roomID){
		warpClient.subscribeRoom(roomID);
	}

	public static void updateRoomInformations(String roomID) {
		warpClient.getLiveRoomInfo(roomID);
	}

	/**
	 * Nur nutzen in besonderen fällen! Nutze vorher lieber chatInRoom() oder
	 * sendPrivateMessage()
	 * 
	 * @param message
	 */
	public static void sendMessage(String message) {
		warpClient.sendChat(message);
	}

	/**
	 * Nur nutzen in besonderen fällen!
	 * 
	 * @param message
	 */
	public static void sendPrivateMessage(String message, LocalPlayer p) {
		Notifications.addMessage(new Message(LocalPlayerHandler.localPlayer.name, Multiplayer.activRoom.id, message));
		warpClient.sendPrivateChat(p.name, "PRIVAT: " + message);
	}
	
	public static void updateProp(String type, Object value){
		updateProp(Multiplayer.activRoom.id,type,value);
	}
	
	public static void updateProp(String roomID, String type, Object value){
		HashMap<String,Object> table = new HashMap<String,Object>();
		table.put(type, value);
		warpClient.updateRoomProperties(roomID, table, null);
	}
	
	public static void deleteProp(String roomID, List<String> keys){
		warpClient.updateRoomProperties(roomID, null, keys.toArray(new String[0]));
	}

	public static void chatInRoom() {
		chatListener.chat();
	}

	public static void chatToPlayer(LocalPlayer p) {
		chatListener.privateChat(p);
	}

	public static void leaveRoom() {
		warpClient.leaveAndUnsubscribeRoom(activRoom.id);
		activRoom = null;
	}

	public static void goOffline() {
		warpClient.disconnect();
		LocalPlayerHandler.localPlayer = null;
	}

	public void printAllRooms() {
		warpClient.getAllRooms();
	}

}
