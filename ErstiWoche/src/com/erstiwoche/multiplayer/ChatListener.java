package com.erstiwoche.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.Main;
import com.erstiwoche.entitys.LocalPlayer;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;

public class ChatListener implements ChatRequestListener {

	ChatInput publicListener;
	PrivateChatInput privateListener;

	
	public ChatListener() {
		publicListener = new ChatInput();
	}

	@Override
	public void onSendChatDone(byte arg0) {
		// TODO Auto-generated method stub
//		Main.log(getClass(), "onSendChatDone");
	}

	@Override
	public void onSendPrivateChatDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSendPricateChatDone");
	}
	
	public void chat() {
		Gdx.input.getTextInput(publicListener, "Send Message", "");
	}
	
	public void privateChat(LocalPlayer p){
		privateListener = new PrivateChatInput(p);
		Gdx.input.getTextInput(privateListener, "Private Message", "");
	}

	public class ChatInput implements TextInputListener {
		@Override
		public void input(String text) {
			Multiplayer.sendMessage(text);
		}

		@Override
		public void canceled() {

		}
	}
	
	public class PrivateChatInput implements TextInputListener {
		
		LocalPlayer sendTo;
		
		public PrivateChatInput(LocalPlayer p){
			sendTo = p;
		}
		
		@Override
		public void input(String text) {
			Multiplayer.updateProp("Chat", text);
//			Multiplayer.sendPrivateMessage(text, sendTo);
		}

		@Override
		public void canceled() {

		}
	}

}
