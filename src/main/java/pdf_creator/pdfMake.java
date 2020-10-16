package pdf_creator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.itextpdf.text.pdf.BaseFont;

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

	
	public void doomething(Generator main_obj  ,  List<Provider> list ,String data_od, String data_do) throws DocumentException, IOException
	{
		   Document doc  = new Document();
		   doc.setPageSize(PageSize.A3.rotate());
		   PdfWriter.getInstance(doc, new FileOutputStream(DEST));
		   doc.open();
		   doc.add(new Paragraph("Raport kosztow przyjecia zewnetrznego ",FontFactory.getFont(FontFactory.TIMES_ROMAN,11, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("Data :  "+data_od+"  do   "+data_do+"",FontFactory.getFont(FontFactory.TIMES_ROMAN,11, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("\n"));
		   doc.add(new Paragraph("\n"));
		   
		   float general_price = 0;
		   
		  for(int i  = 0 ; i < list.size() ; i++)
		  {
		      PdfPTable table = new PdfPTable(2);
		      //  float widths[] = new float[] { 6, 6};


		        // header row:
		        table.addCell("Company Name");
		        table.addCell("Company ID(hacosoft)");
		        table.setHeaderRows(1);
		        
		        doc.add(table);
		      
		      
		        
		       // add_cell(list.get(i).getProviderName(),String.valueOf(list.get(i).getProvideID()),"","12","12",doc,table,1);
		       
		        add_cell(list.get(i).getProviderName(),String.valueOf(list.get(i).getProvideID()),doc,table,1);
		        
		        float sum_prijs = 0;      
		  	for(int x = 0 ; x <list.get(i).getFacturiate_list().size() ;x ++)	        
		  	{
		  	  String id_facturaite = "";
		        
		        id_facturaite = String.valueOf(list.get(i).getFacturiate_list().get(x).getFv_number());
		  		
		       PdfPTable table2 = new PdfPTable(1);
		       table2.addCell("facturaite  : "  + id_facturaite);
		        doc.add(table2);
		        

		        BaseFont bf = BaseFont.createFont(
                        BaseFont.TIMES_ROMAN,
                        BaseFont.CP1252,
                        BaseFont.EMBEDDED);
		        
                Font font = new Font(bf, 12);
                
                
                PdfPTable table3 = new PdfPTable(5);
			       table3.addCell(new PdfPCell(new Phrase("ORDERNUMMER",font)));
			       table3.addCell(new PdfPCell(new Phrase("FACTURIRIATEDATUM",font)));
			       table3.addCell(new PdfPCell(new Phrase("REGISTRATIEDATUM",font)));
			       table3.addCell(new PdfPCell(new Phrase("CFPROJECTNUMMER",font)) );
			       table3.addCell(new PdfPCell(new Phrase("NettoPrijs",font)) );
			       
			   
		        for(int j = 0 ; j < list.get(i).getFacturiate_list().get(x).getInvoiceList().size() ; j++)
		        {		      
				       table3.addCell(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getORDERNUMMER());
				       table3.addCell(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getFACTURIATEDATUM());
				       table3.addCell(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getREGISTRATIEDATUM());
				       table3.addCell(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getCFPROJECTNUMMER());
				       table3.addCell(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getNETTOPRIJS());

				       sum_prijs = sum_prijs + Float.parseFloat(list.get(i).getFacturiate_list().get(x).getInvoiceList().get(j).getNETTOPRIJS());
				        doc.add(table3);
				        table3.flushContent();
		        }
		        
		        
		  	} 
		  	
		    BaseFont bf = BaseFont.createFont(
                    BaseFont.TIMES_ROMAN,
                    BaseFont.CP1252,
                    BaseFont.EMBEDDED);
	        
            Font font = new Font(bf, 12);
            PdfPTable table0 = new PdfPTable(2);
            table0.addCell(new PdfPCell(new Phrase("SUMMARY",font)));
            table0.addCell(new PdfPCell(new Phrase(String.valueOf(sum_prijs),font)));
		    
		    doc.add(table0);
		        
		        
		        doc.add(Chunk.NEWLINE);   
		        
		  general_price = general_price + sum_prijs;  
		        sum_prijs = 0;     
		  }     
		  
		   BaseFont bf = BaseFont.createFont(
                   BaseFont.TIMES_ROMAN,
                   BaseFont.CP1252,
                   BaseFont.EMBEDDED);
	        
           Font font = new Font(bf, 12);
           PdfPTable tablex = new PdfPTable(2);
           tablex.addCell(new PdfPCell(new Phrase("Overall Summary",font)));
           tablex.addCell(new PdfPCell(new Phrase(String.valueOf(general_price),font)));
		    
		    doc.add(tablex);
		  
		  
		        doc.close();
	}
	
	
	public void  add_cell(String company_name, String company_id, Document doc, PdfPTable table, int zmienna)
	{		
		// jezeli zmienna == 0 - czyli normalnie, to kolor bialy neutralny,
		// jezeli zmienna == 1 - czyli co 2gi row, to kolor szarawy - dla odroznienia
		int c1= 255, c2=255, c3=255;
		if(zmienna ==1)   	{
			c1 = 226;
			c2 = 226;
			c3= 226;
		}
		

		table_cell1 = new PdfPCell(new Phrase(company_name));
		table_cell1.setBackgroundColor(new BaseColor(c1, c2, c3)); 

	    table.addCell(table_cell1);
	    

	    table_cell2 = new PdfPCell(new Phrase(company_id));
		table_cell2.setBackgroundColor(new BaseColor(c1, c2, c3)); 

	    table.addCell(table_cell2);
	      
	    try {
			doc.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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