package com.erstiwoche.helper;

import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;

public class Message {

	private MyTime time;
	private String userName;
	private String roomID;
	private String message;
	
	
	public Message(ChatEvent event){
		this(event.getSender(),event.getLocationId(),event.getMessage());
	}
	
	public Message(String userName, String roomID, String message){
		this(userName,roomID,message,System.currentTimeMillis());
	}
		
	public Message(String userName, String roomID, String message, long timeReceived){
		this.userName = userName;
		this.roomID = roomID;
		this.message = message;
		this.time = new MyTime(timeReceived);
	}
	
	public String getSender(){
		return userName;
	}
	
	public String getRoomID(){
		return roomID;
	}
	
	public String getMessage(){
		return message;
	}
	
	public MyTime getTime(){
		if(time!=null){
			return time;
		}
		return new MyTime(1);
	}
}