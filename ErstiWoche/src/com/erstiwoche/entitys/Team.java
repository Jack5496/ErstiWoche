package com.erstiwoche.entitys;

public class Team {

	public String name;

	public Team(String name) {
		this.name = name;
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

}
