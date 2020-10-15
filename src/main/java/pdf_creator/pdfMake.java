package pdf_creator;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import Objects.Provider;
import Program.Generator;
import Program.main;





public class pdfMake {

	private String DEST = "C://Users/el08/Desktop/testowy_dokument.pdf";
	private PdfPCell table_cell1;
	private PdfPCell table_cell2;
	private   PdfPCell table_cell3;
	private  PdfPCell table_cell4;
	private PdfPCell table_cell5;

	
	public void doomething(Generator main_obj  ,  List<Provider> list ) throws FileNotFoundException, DocumentException
	{
		   Document doc  = new Document();
		   PdfWriter.getInstance(doc, new FileOutputStream(DEST));
		   doc.open();
		   doc.add(new Paragraph("RAPORT PDF PRACOWNIKOW",FontFactory.getFont(FontFactory.TIMES_ROMAN,11, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("\n"));
		   doc.add(new Paragraph("\n"));
		   
		  for(int i  = 0 ; i < list.size() ; i++)
		  {
		      PdfPTable table = new PdfPTable(5);
		        float widths[] = new float[] { 6, 6, 6, 6, 6};


		        // header row:
		        table.addCell("Dzien");
		        table.addCell("wejscie");
		        table.addCell("wyjscie");
		        table.addCell("czas w firmie");
		        table.addCell("Komentarz");
		        table.setHeaderRows(1);
		        
		        doc.add(table);
		      
		        add_cell(list.get(i).getProviderName(),String.valueOf(list.get(i).getProvideID()),"-","12","12",doc,table,1);
		        
		        
		  }     
		        doc.close();
	}


public void  add_cell(String d, String we, String wy, String r, String k, Document doc, PdfPTable table, int zmienna)
{		
	// jezeli zmienna == 0 - czyli normalnie, to kolor bialy neutralny,
	// jezeli zmienna == 1 - czyli co 2gi row, to kolor szarawy - dla odroznienia
	int c1= 255, c2=255, c3=255;
	if(zmienna ==1)   	{
		c1 = 226;
		c2 = 226;
		c3= 226;
	}
	

	table_cell1 = new PdfPCell(new Phrase(d));
	table_cell1.setBackgroundColor(new BaseColor(c1, c2, c3)); 

    table.addCell(table_cell1);
    

    table_cell2 = new PdfPCell(new Phrase(we));
	table_cell2.setBackgroundColor(new BaseColor(c1, c2, c3)); 

    table.addCell(table_cell2);
      
    
    table_cell3= new PdfPCell(new Phrase(wy));
	table_cell3.setBackgroundColor(new BaseColor(c1, c2, c3)); 

    table.addCell(table_cell3);
    
    
    table_cell4= new PdfPCell(new Phrase(wy));
 	table_cell4.setBackgroundColor(new BaseColor(c1, c2, c3)); 

     table.addCell(table_cell4);
     
     table_cell5= new PdfPCell(new Phrase(wy));
 	table_cell5.setBackgroundColor(new BaseColor(c1, c2, c3)); 

     table.addCell(table_cell5);
     


    
    try {
		doc.add(table);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}