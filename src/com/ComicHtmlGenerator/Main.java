package com.ComicHtmlGenerator;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {
	public static boolean useterminal = false;
	
	public static void main (String[] args) {	
		
		Console console  = System.console();
		
		if(console == null){
			useterminal = true;
		}
		
		while (true){
			WriteLine("Please enter the directory: ");		
			
			try{
				String searchdir = ReadLine();
				File dir = new File(searchdir);
				
				if(!dir.isDirectory()){
					WriteLine("Directory is not valid");
				}else{
					try{
						GenerateHtml.Start(dir);
					}
					catch(Exception ex){
						WriteExceptionMsg(ex);
					}
				}
			}
			catch(Exception ex){
				WriteExceptionMsg(ex);
			}
			
			WriteLine("Process Done");
			WriteLine("");
		}
	}
	
	public static void WriteLine(String str){
		if(useterminal){
			System.out.println(str);
		}else{
			System.console().writer().println(str);
		}
		
	}
	
	public static String ReadLine() throws Exception{
		try{
			if(useterminal){
				InputStreamReader ir = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(ir);
				return br.readLine();
			} else {
				return System.console().readLine();
			}
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public static void WriteExceptionMsg(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		
		WriteLine(ex.getMessage());
		WriteLine(sw.toString());
		WriteLine("");
	}
}
