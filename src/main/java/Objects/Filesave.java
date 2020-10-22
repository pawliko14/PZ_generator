package Objects;


import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Filesave {

	//depoloy purpose
	private static String PathToSaveHours= "//192.168.90.203/Logistyka/Bookkeeping/PZ_braki";

	
//	//testing purpose
//	private static String PathToSaveHours = "C://Users/el08/Desktop";

	private static String filename = "";
	
	public static void setFilename(String f)
	{
		filename = f;
	}
	
	
	
	public static String getPathToSaveHours(){
		return PathToSaveHours;
	}
	

	
	public static void createDirectory(){
		String path = getPath();
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		        //handle it
		    }
		}
	}

public static File createFile(){
	String path = getPath();
	SimpleDateFormat godz = new SimpleDateFormat("HH;mm");
	Calendar date = Calendar.getInstance();
	
	File f = new File(path+filename);
	if(f.exists() && !f.isDirectory()){
		f = new File(path+ godz.format(date.getTime())+" "+filename);
	}
	
	return f;
	
}

public static String getFUllFilePath()
{
	return  getPath()+ "/" + filename; 
}



public static String getPath(){
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
	String path = Filesave.getPathToSaveHours()+"/"+doNazwy.format(calendar.getTime())+"/";
	
	return path;
}





}
