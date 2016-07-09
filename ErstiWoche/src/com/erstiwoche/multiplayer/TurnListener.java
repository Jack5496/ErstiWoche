package com.erstiwoche.multiplayer;

import com.erstiwoche.Main;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.TurnBasedRoomListener;

public class TurnListener implements TurnBasedRoomListener {

	@Override
	public void onGetMoveHistoryDone(byte arg0, MoveEvent[] arg1) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onGetMoveHistoryDone");
	}

	@Override
	public void onSendMoveDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSendMoveDone: "+arg0);
	}

	@Override
	public void onSetNextTurnDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onSetNextTurnDone: "+arg0);
	}

	@Override
	public void onStartGameDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onStartGameDone: "+arg0);
	}

	@Override
	public void onStopGameDone(byte arg0) {
		// TODO Auto-generated method stub
		Main.log(getClass(), "onStopGameDone: "+arg0);
	}
	

}
