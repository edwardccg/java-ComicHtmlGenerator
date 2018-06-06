package com.ComicHtmlGenerator;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class GenerateHtml {

	public static void Start(File dir) throws Exception{
				
		String comicName = dir.getName().toString();	
		Main.WriteLine("Processing... " + comicName);
		
		File[] imgFiles = dir.listFiles(getComicFilenameFilter());
		Main.WriteLine("Images Files Count= " + imgFiles.length);	
		
		try {
			//sort the imgFiles based on file name
			Arrays.sort(imgFiles, new ComicNameComparator());
			
			if(imgFiles.length > 0){
				File htmltemplate = new File(System.getProperty("user.dir"), "template.html");
				String htmlstr = FileUtils.readFileToString(htmltemplate);
				
				StringBuilder sb = new StringBuilder();
				
				for (File imgfile :imgFiles){
					sb.append(getImgHtmlTag(imgfile.getName()));
				}
				
				String htmltitle = comicName;
				String htmlbody =  sb.toString();
				
				htmlstr = htmlstr.replace("<%title%>", htmltitle);
				htmlstr = htmlstr.replace("<%body%>", htmlbody);
				
				File indexhtml = new File(dir.getPath(),"~index.html");
				FileUtils.writeStringToFile(indexhtml, htmlstr, StandardCharsets.UTF_8, false);	
				Main.WriteLine("~index.html created");

			}	
		} catch (Exception ex){
			Main.WriteExceptionMsg(ex);
			Main.WriteLine(comicName + " is not processed");
		}
		
		//recursive call when there is a sub directory 
		File[] directories = dir.listFiles(getDirectoryFilenameFilter());
		if(directories.length > 0){
			for ( File subdir : directories){
				GenerateHtml.Start(subdir);
			}
		}		
	}
	
	private static FilenameFilter getComicFilenameFilter(){
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name){
				return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") ||
						name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".bmp");
			}
		};
	}
	
	private static FilenameFilter getDirectoryFilenameFilter(){
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name){
				return new File(dir, name).isDirectory();
			}
		};
	}
		
	private static String getImgHtmlTag(String imgsrc){
		return String.format("</br><img src=\"%s\" alt=\"\" width=\"1300\" /><br>\n", imgsrc);
	}
}
