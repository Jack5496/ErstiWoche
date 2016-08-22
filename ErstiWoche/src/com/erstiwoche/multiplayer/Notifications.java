package com.erstiwoche.multiplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.erstiwoche.Main;
import com.erstiwoche.SoundHandler;
import com.erstiwoche.entitys.LocalPlayerHandler;
import com.erstiwoche.helper.Message;
import com.erstiwoche.menu.ChatRoom;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

public class Notifications implements NotifyListener {

	static List<Message> messages;
	static int maxMessagesHolding = ChatRoom.maxMessagesToShow;

	public static String UPDATE = "UPDATE";
	public static String SYSTEM = "GAME";
	public static String REGEX = " ";

	public static String STATUSUPDATE = "STATUSUPDATE";
	public static String BIERUPDATE = "BIERUPDATE";
	public static String TEAMERUPDATE = "TEAMERUPDATE";
	public static String CHATUPDATE = "CHATUPDATE";

	public Notifications() {
		resetMessages();
	}

	public static void resetMessages() {
		messages = new ArrayList<Message>();
	}

	public static List<Message> getMessages() {
		return messages;
	}

	public static void addMessage(Message m) {
		if (messages.size() >= maxMessagesHolding) {
			messages.remove(0);
		}
		messages.add(m);
	}

	public static HashMap<String, List<String>> changed = new HashMap<String, List<String>>();

	public void systemMessage(Message m) {
		String[] functions = m.getMessage().split(REGEX);

		String what = functions[1];

		if (what.equals(UPDATE)) {
			if (m.getRoomID().equals("0")) {
				Multiplayer.updateRoomInformations(functions[2]);
			} else {
				List<String> changes = changed.get(m.getRoomID());
				if (changes == null) {
					changes = new ArrayList<String>();
				}
				changed.put(m.getRoomID(), changes);
			}
		}
	}
	
	public static void addChange(String roomID, String change){
		List<String> changes = changed.get(roomID);
		if (changes == null) {
			changes = new ArrayList<String>();
		}
		if (!changes.contains(change)) {
			changes.add(change);
		}
		changed.put(roomID, changes);
	}

	public boolean isSystemMessage(String message) {
		return message.startsWith(SYSTEM);
	}

	@Override
	public void onChatReceived(ChatEvent arg0) {
//		Main.log(getClass(), "onChatReceived");
		Message m = new Message(arg0);
		if (isSystemMessage(m.getMessage())) {
			systemMessage(m);
		} else {
			addMessage(m);
		}
	}

	@Override
	public void onGameStarted(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGameStarted");
	}

	@Override
	public void onGameStopped(String arg0, String arg1) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGameStopped");
	}

	@Override
	public void onMoveCompleted(MoveEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onMoveCompleted");
	}

	@Override
	public void onNextTurnRequest(String arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onNextTurnRequest");
	}

	@Override
	public void onPrivateChatReceived(String arg0, String arg1) {
		Main.log(getClass(), "onPrivateChatReceived");
		Message m = new Message(arg0, null, arg1);
		if (isSystemMessage(m.getMessage())) {
			systemMessage(m);
		} else {
			addMessage(m);
		}
	}

	@Override
	public void onPrivateUpdateReceived(String arg0, byte[] arg1, boolean arg2) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onPrivateUpdateReceived");
	}

	@Override
	public void onRoomCreated(RoomData arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onRoomCreated");
	}

	@Override
	public void onRoomDestroyed(RoomData arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onRoomDestroyed");
	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUpdatePeersReceived");
	}

	@Override
	public void onUserChangeRoomProperty(RoomData arg0, String arg1, HashMap<String, Object> arg2,
			HashMap<String, String> arg3) {
		// TODO Auto-generated method stub
		Multiplayer.updateRoomInfromations();
	}

	@Override
	public void onUserJoinedLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		// Main.log(getClass(), "onUserJoinedLobby");
	}

	@Override
	public void onUserJoinedRoom(RoomData arg0, String userName) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUserJoinedRoom");
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUserLeftLobby");
	}

	@Override
	public void onUserLeftRoom(RoomData arg0, String userName) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUserLeftRoom");
	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUserPaused");
	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onUserResumed");
	}

}
