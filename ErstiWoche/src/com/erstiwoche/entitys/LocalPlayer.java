package com.erstiwoche.entitys;

import java.util.ArrayList;
import java.util.List;

public class LocalPlayer{

	public String name;
	
	public LocalPlayer(String name) {
		this.name = name;
		handCards = new ArrayList<String>();
	}
	
	public void takeCard(String card){
		handCards.add(card);
	}
	
	public String getCard(int pos){
		return handCards.get(pos);
	}
	
	public String removeCard(){
		return handCards.remove(0);
	}
	
	public List<String> handCards;
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!LocalPlayer.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final LocalPlayer other = (LocalPlayer) obj;
	    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
	        return false;
	    }
	    return true;
	}

	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
	    return hash;
	}
	
}
