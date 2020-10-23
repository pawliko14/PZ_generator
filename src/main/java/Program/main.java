package Program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.DocumentException;

import Objects.Filesave;
import Objects.Generator_bestelling;


public class main {

	//	private  static List<Provider> copy_of_list;
	
	public static void main(String[] args) throws SQLException, DocumentException, IOException {

		

//        Generator prog = new Generator("2020-09-01", "2020-09-30");
//        prog.run();
//		
//		
//        copy_of_list = prog.getMainList();
//        
//		pdfMake pdf = new pdfMake();
//		pdf.doomething(prog , copy_of_list, "2020-09-01", "2020-09-30");
		
		
		
/*
 *  glowny program
 */
		Generator_bestelling gen  = new Generator_bestelling(getCurrdateMinus30Days(), getCurdate());
		setPrintStream();

		gen.run();
		
		gen.show_bestelling_list();
		

		System.out.println("program finished");
		
		System.exit(0);
		
	}



	private static void setPrintStream() throws FileNotFoundException {

		PrintStream fileOut = new PrintStream(Filesave.getPath()+ "/out.txt" );
		System.setOut(fileOut);
		System.setErr(fileOut);
		
	}



	private static String getCurrdateMinus30Days() {
		
		    SimpleDateFormat df  = new SimpleDateFormat("YYYY-MM-dd");
		    Calendar c1 = Calendar.getInstance();

		    // now add 30 day in Calendar instance 
		    c1.add(Calendar.DAY_OF_YEAR, -30);
		    df = new SimpleDateFormat("yyyy-MM-dd");
		    Date resultDate = c1.getTime();
		    String dueDate = df.format(resultDate);

		   
		   return dueDate;
		
	}



	private static String getCurdate() {
			Date date = new Date();
		    SimpleDateFormat df  = new SimpleDateFormat("YYYY-MM-dd");
		    String currentDate = df.format(date);// get current date here


		    return currentDate;
	}

}
