package Program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.text.DocumentException;

import Objects.Generator_bestelling;
import Objects.Provider;
import Objects.Sumator;
import pdf_creator.pdfMake;

public class main {

		private  static List<Provider> copy_of_list;
	
	public static void main(String[] args) throws SQLException, DocumentException, IOException {
		// TODO Auto-generated method stub


//        Generator prog = new Generator("2020-09-01", "2020-09-30");
//        prog.run();
//		
//       System.out.println("sum: " + Sumator.getSUm());
//		
//        copy_of_list = prog.getMainList();
//        
//		pdfMake pdf = new pdfMake();
//		pdf.doomething(prog , copy_of_list, "2020-09-01", "2020-09-30");
		
		Generator_bestelling gen  = new Generator_bestelling("2020-09-01", "2020-09-30");
		gen.run();
		
		gen.show_bestelling_list();
		
	}

}
