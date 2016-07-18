package com.erstiwoche.helper;

import com.erstiwoche.entitys.LocalPlayerHandler;

public class Auth{

	public static final String ADMINNAME = "Admin";

	public static boolean isPlayerAdmin() {
		return LocalPlayerHandler.localPlayer.name.equals(ADMINNAME);
	}

}