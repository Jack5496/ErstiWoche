package com.erstiwoche.helper;

public class Umlaute {
	
	static String startChar = "!&";
	static String endChar = "&!";
	
	public static String macheSafe(String name){
		name = name.replaceAll(startChar, "");
		name = name.replaceAll(endChar, "");
		
		name = name.replaceAll("�", startChar+"ss"+endChar);
		
		name = name.replaceAll("�", startChar+"ue"+endChar);
		name = name.replaceAll("�", startChar+"UE"+endChar);
		
		name = name.replaceAll("�", startChar+"ae"+endChar);
		name = name.replaceAll("�", startChar+"AE"+endChar);
		
		name = name.replaceAll("�", startChar+"oe"+endChar);
		name = name.replaceAll("�", startChar+"OE"+endChar);
		
		return name;
	}
	
	public static String rekonstruiere(String name){
		name = name.replaceAll(startChar+"ss"+endChar,"�");
		
		name = name.replaceAll(startChar+"ue"+endChar,"�");
		name = name.replaceAll(startChar+"UE"+endChar,"�");
		
		name = name.replaceAll(startChar+"ae"+endChar,"�");
		name = name.replaceAll(startChar+"AE"+endChar,"�");
		
		name = name.replaceAll(startChar+"oe"+endChar,"�");
		name = name.replaceAll(startChar+"OE"+endChar,"�");
		
		return name;
	}
}