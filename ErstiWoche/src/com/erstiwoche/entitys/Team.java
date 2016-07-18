package com.erstiwoche.entitys;

import com.erstiwoche.uiElements.GUIButton;

public class Team implements Comparable<Team>{
	
	public String name;
	public String nameDisplayName;
	public int points;
	public GUIButton b;

	public Team(String name, String nameDisplayName, int points,GUIButton b) {
		this.name = name;
		this.nameDisplayName = nameDisplayName;
		this.points = points;
		this.b = b;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!Station.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Station other = (Station) obj;
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

	@Override
	public int compareTo(Team o) {
		if(this.points<o.points) return 1;
		if(this.points>o.points) return -1;
		return 0;
	}

}
