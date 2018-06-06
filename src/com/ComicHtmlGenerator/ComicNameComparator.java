package com.ComicHtmlGenerator;

import java.io.File;
import java.util.Comparator;

public class ComicNameComparator implements Comparator<File> {
	@Override 
	public int compare (File o1, File o2){
		
		int o1Num = extractNumber(o1.getName());
		int o2Num = extractNumber(o2.getName());
		
		if(o1Num == 0 && o1Num == 0){
			try{
				return (o1.getName().compareTo(o2.getName()));
			}catch(Exception ex){
				Main.WriteExceptionMsg(ex);
				return 1;				
			}						
		}else{
			return (o1Num - o2Num);
		}
	}
	
	private int extractNumber(String filename){
		int i = 0;
		try{
			int start = filename.lastIndexOf("(")+1;
			int end = filename.lastIndexOf(".")-1;
			String num = filename.substring(start, end);
			i = Integer.parseInt(num);
		}catch(Exception ex){
			i = 0;
		}	
		return i;			
	}
}
