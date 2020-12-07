package Objects;


import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Filesave {


	private  String PathToSaveHours;
	private  String filename;
	private  String fullFilePathAndName;
	
	
	
	public Filesave(String path, String fileName) {
		this.PathToSaveHours = path;
		this.filename = fileName;
		
		this.createDirectory();
		this.createFile();
		
	}	
	
	public String getFullFilePathAndName() {
		return this.fullFilePathAndName;
	}
	
	
	public  String getPathToSaveHours(){
		return PathToSaveHours;
	}
	

	
	public  void createDirectory(){
		String path = this.getPath();
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		        System.out.println(se);
		       
		    }
		}
	}

	public  File createFile(){
		String path = this.getPath();
		SimpleDateFormat godz = new SimpleDateFormat("HH;mm");
		Calendar date = Calendar.getInstance();
		
		File f = new File(this.getPath()+"/"+filename);
		
		this.fullFilePathAndName = this.getPath() + godz.format(date.getTime())+" "+filename;
		
		if(f.exists() && !f.isDirectory()){
			f = new File(this.fullFilePathAndName);
		}
		
		return f;
		
	}



public  String getPath() {
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
	String path = this.PathToSaveHours +"/" + doNazwy.format(calendar.getTime()) +"/";
	
	return path;
}






}
