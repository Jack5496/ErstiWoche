package com.erstiwoche.Inputs;

import com.badlogic.gdx.Input.TextInputListener;
import com.erstiwoche.Main;

public class InputListener implements TextInputListener {
	
	
   @Override
   public void input(String text) {
	   Main.log(getClass(), "input: "+text);
   }

   @Override
   public void canceled () {
	   
   }
}