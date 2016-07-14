package com.erstiwoche.helper;

public class Umlaute {
	
	static String startChar = "!&";
	static String endChar = "&!";
	
	public static String macheSafe(String name){
		name = name.replaceAll(startChar, "");
		name = name.replaceAll(endChar, "");
		
		name = name.replaceAll("ß", startChar+"ss"+endChar);
		
		name = name.replaceAll("ü", startChar+"ue"+endChar);
		name = name.replaceAll("Ü", startChar+"UE"+endChar);
		
		name = name.replaceAll("ä", startChar+"ae"+endChar);
		name = name.replaceAll("Ä", startChar+"AE"+endChar);
		
		name = name.replaceAll("ö", startChar+"oe"+endChar);
		name = name.replaceAll("Ö", startChar+"OE"+endChar);
		
		return name;
	}
	
	public static String rekonstruiere(String name){
		name = name.replaceAll(startChar+"ss"+endChar,"ß");
		
		name = name.replaceAll(startChar+"ue"+endChar,"ü");
		name = name.replaceAll(startChar+"UE"+endChar,"Ü");
		
		name = name.replaceAll(startChar+"ae"+endChar,"ä");
		name = name.replaceAll(startChar+"AE"+endChar,"Ä");
		
		name = name.replaceAll(startChar+"oe"+endChar,"ö");
		name = name.replaceAll(startChar+"OE"+endChar,"Ö");
		
		return name;
	}
}