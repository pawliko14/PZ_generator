package Objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sound.midi.SysexMessage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Program.DBConnection;

public class Generator_bestelling {
	
    private static Connection myConn;
    private static Document doc;
    private String Date_from;
    private String Date_to;
	private List<bestelling> bestelling_list;
	private String DEST = Filesave.getPathToSaveHours();
	
	private int c1,c2,c3;
	
	private static Font font_header;
	private static Font font_values;



	public Generator_bestelling(String d1, String d2) throws DocumentException, IOException
	{
		Filesave.createDirectory();
		Filesave.setFilename("testowy_dokument.pdf");
		Filesave.createFile();
		 c1 = 210;
		 c2 = 210;
		 c3 = 210;

		
		
		   BaseFont bf = BaseFont.createFont(
                   BaseFont.TIMES_ROMAN,
                   BaseFont.CP1252,
                   BaseFont.EMBEDDED);
		   
		   BaseFont bf_values = BaseFont.createFont(
                   BaseFont.TIMES_ROMAN,
                   BaseFont.CP1252,
                   BaseFont.EMBEDDED);
	        
		font_header = new Font(bf, 15);
		font_values = new Font(bf_values,12);
		
		doc = new Document();
		Date_from  =d1;
    	Date_to = d2;
    	
        myConn = DBConnection.dbConnector();
		bestelling_list = new ArrayList<bestelling>();
		
		
	}
	
	
	public void run() throws FileNotFoundException, DocumentException
	{
		retrive();
		
		doc.close();
	}
	
	public void show_bestelling_list()
	{
		for(bestelling b : bestelling_list)
		{
			System.out.println(b.toString());
		}
	}
	
	private void retrive() throws FileNotFoundException, DocumentException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String curdate = formatter.format(date);
		
		   doc.setPageSize(PageSize.A2.rotate());
		   PdfWriter.getInstance(doc, new FileOutputStream(Filesave.getFUllFilePath()));
		   doc.open();
		   doc.add(new Paragraph("Raport Brakujacych fakturacji w danym przedziale czasowym ",FontFactory.getFont(FontFactory.TIMES_ROMAN,17, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("Data zamowien od :  "+Date_from+"  do   "+Date_to+"",FontFactory.getFont(FontFactory.TIMES_ROMAN,15, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("\n"));
		   doc.add(new Paragraph("Raport wygenerowany: " + curdate, FontFactory.getFont(FontFactory.TIMES_ROMAN,15, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("\n"));
		   doc.add(new Paragraph("Adnotacja:  Program odczytuje tylko takie artykuly, ktore w HACOSOFT maja w kolumnie 'DOSTARCZONE'(DELIVERED) wartosc inna niz '0'  " , FontFactory.getFont(FontFactory.TIMES_ROMAN,15, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("Artykul z dostarczonym == '0' nie ma wygenerowanego dokumentu PZ  ", FontFactory.getFont(FontFactory.TIMES_ROMAN,15, Font.ITALIC, BaseColor.BLACK)));
		   doc.add(new Paragraph("\n"));

    	
    	String query = "		select  b2.leverancier ,b2.ORDERNUMMER ,b2.STATUSCODE , a2.NAAM ,b2.BESTELDATUM,b2.LEVERDATUM from bestelling b2 \r\n" + 
    			"		left join leverancier a2 \r\n" + 
    			"		on b2.leverancier  = a2.LEVERANCIERNR \r\n" + 
    			"		where b2.BESTELDATUM  between ? and  ? \r\n" + 
    			"		and length(b2.leverancier) >= 6\r\n and b2.LEVERANCIER  <  9000000 \r\n" + 
    			"		group by b2.ORDERNUMMER  ";

        try
        {
            PreparedStatement takeDate = myConn.prepareStatement(query);
            takeDate.setString(1, Date_from);
            takeDate.setString(2, Date_to);
            ResultSet r = takeDate.executeQuery();

            while(r.next())
            {
                int leverancier = Integer.parseInt(r.getString(1));
                String ordernummer = r.getString(2);   
                String statuscode = r.getString(3);
                String  cfnaam = r.getString(4);
                String  BESTELDATUM = r.getString(5);
                String LEVERDATUM = r.getString(6);

                
                
                bestelling obj = new bestelling( leverancier,ordernummer,statuscode,cfnaam,BESTELDATUM,LEVERDATUM);
                
                bestelling_list.add(obj);
            }      
            takeDate.close();
            r.close();
            
            
            System.out.println("list begin: ");
            for(int i = 0 ; i < bestelling_list.size(); i++)
            {
            	retrive2(bestelling_list.get(i).getLeverancier(),bestelling_list.get(i).getOrdernummer(),bestelling_list.get(i).getCfnaam(), bestelling_list.get(i).getBestelldatum(),bestelling_list.get(i).getLeverdatum() );
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    
	}
	

	
	// remove leverdatum, not used here
	private String getReceptie(int leverancier, String ordernummer, String articel) {
				
		String  receptie  = null;

		
		String query =  "select BONNR from receptiedetail r \r\n" + 
				"		where LEVERANCIER  = ? \r\n" + 
				"		and ARTIKELCODE  = ? \r\n" +
				"		and ORDERNUMMER  = ? ";
		
		try
		{
		    PreparedStatement takeDate = myConn.prepareStatement(query);
            takeDate.setInt(1, leverancier);
            takeDate.setString(2, articel);
            takeDate.setString(3, ordernummer);
            ResultSet rs = takeDate.executeQuery();
            
            if (rs.next() == false) {
                System.out.println("Resultset is empty! " );
              } else
              {
            	  receptie = rs.getString(1);
              }
            rs.close();
            takeDate.close();
		}
		catch(Exception e )
		{
			e.printStackTrace();	
		}
		
		
		
		return receptie;
	}
	
	public void retrive2(int leverancier, String ordernummer, String Cfnaam,String besteldatum ,String leverdatum)
	{
		PdfPTable table2 ;
		PdfPTable table3 = null;
		boolean table_created =false;
				

		
		String query = "select ARTIKELCODE, SEQUENTIE,BESTELD,GELEVERD,GEFACTUREERD , concat(AFDELING , '/', AFDELINGSEQ ) as AFDELINGS, eenheidsprijs , suma,MUNT,LEVERINGSDATUMINGAVERECEPTIE  from bestellingdetail b \r\n" + 
				"		where leverancier  = ? \r\n" + 
				"		and GELEVERD  > 0 \r\n" + 
				"		and GELEVERD  <> GEFACTUREERD\r\n" + 
				"		and ORDERNUMMER  = ? ";

        try
        {
            PreparedStatement takeDate = myConn.prepareStatement(query);
            takeDate.setInt(1, leverancier);
            takeDate.setString(2, ordernummer);
            ResultSet rs = takeDate.executeQuery();
            
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java -> cfnaam " + Cfnaam + " previousCfnaam -> " );
              } else {

                do {
                	
                	if(table_created == false  )
                	{                		
        		        doc.add(Chunk.NEWLINE);   

                		table2  = new PdfPTable(3);
                		PdfPCell cell1 = new PdfPCell();
                				cell1.setBackgroundColor(new BaseColor(c1,c2,c3));
                				Paragraph p = new Paragraph("Supplier name  : "  + Cfnaam , font_header);
                				p.setAlignment(Element.ALIGN_CENTER);
                			cell1.addElement(p);
                		PdfPCell cell2 = new PdfPCell();
                				cell2.setBackgroundColor(new BaseColor(c1,c2,c3));
                				Paragraph p2 = new Paragraph("Order Date  : "  + besteldatum , font_header);
        						p2.setAlignment(Element.ALIGN_CENTER);
                    		cell2.addElement(p2);
                    	PdfPCell cell3 = new PdfPCell();
                    		cell3.setBackgroundColor(new BaseColor(c1,c2,c3));
            				Paragraph p3 = new Paragraph("Delivery Date(theoretical) : "  + leverdatum , font_header);
            				p3.setAlignment(Element.ALIGN_CENTER);
            				cell3.addElement(p3);
                		

            				
        		        table2.addCell(cell1);
        		        table2.addCell(cell2);
        		        table2.addCell(cell3);

        		        doc.add(table2);
        		       
        		       table3  = new PdfPTable(13);
        		       table3.addCell("Supplier Nr");
        		       table3.addCell("Order Nr");
        		       table3.addCell("Order Sequency");
        		       table3.addCell("Article code");
        		       table3.addCell("Ordered amount");
        		       table3.addCell("Delivered amount");
        		       table3.addCell("Invoiced amount");
        		       table3.addCell("Recipient");
        		       table3.addCell("Prices per item");
        		       table3.addCell("Prices summary");
        		       table3.addCell("Currency");
        		       table3.addCell("PZ Number");
        		       table3.addCell("Real Del. Date");


        		       table_created = true;
                	}
                  
                  
                  String articel_code = rs.getString(1);
                  String seq = rs.getString(2);
                  String BESTELD =  rs.getString(3);
                  String GELEVERD =  rs.getString(4);
                  String GEFACTUREERD =  rs.getString(5);
                  String Afdelings =  rs.getString(6);
                  String eenhijdprijs =  rs.getString(7);
                  String SUMA =  rs.getString(8);
                  String MUNT =  rs.getString(9);
                  String receptie = getReceptie(leverancier,ordernummer,articel_code);
                  String LEVERINGSDATUMINGAVERECEPTIE = rs.getString(10);
                  
                  
                  System.out.println("leverancier: " + leverancier + ", ordernummer: " + ordernummer + ", seq: "+ seq + "  -> data in set : " + articel_code );
                  
                  List<String> objects_to_print = new ArrayList<String>();
                  	objects_to_print.add(String.valueOf(leverancier));
                  	objects_to_print.add(ordernummer);
                  	objects_to_print.add(seq);
                  	objects_to_print.add(articel_code);
                  	objects_to_print.add(BESTELD);
                  	objects_to_print.add(GELEVERD);
                  	objects_to_print.add(GEFACTUREERD);
                  	objects_to_print.add(Afdelings);
                  	objects_to_print.add(eenhijdprijs);
                  	objects_to_print.add(SUMA);
                  	objects_to_print.add(MUNT);
                  	objects_to_print.add(receptie);
                  	objects_to_print.add(LEVERINGSDATUMINGAVERECEPTIE);

                  	
                  	for(int x = 0 ; x < objects_to_print.size();x++)
                  	{
              	 		PdfPCell cell = new PdfPCell();

      
			  				Paragraph p = new Paragraph(objects_to_print.get(x) , font_values);
			  				p.setAlignment(Element.ALIGN_CENTER);
			  				cell.addElement(p);
                  		
		  				
		                table3.addCell(cell);
                  	}



                  doc.add(table3);
                  table3.flushContent();
                  
                  
                } while (rs.next());
              }     
            takeDate.close();
            rs.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}



	
}
