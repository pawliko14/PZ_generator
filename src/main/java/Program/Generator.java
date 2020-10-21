package Program;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import Objects.Facturiate;
import Objects.Invoice;
import Objects.Provider;

import java.sql.*;

public class Generator {
    private static Connection myConn;
    private List<Provider> provider_list; 
    
    private String Date_from;
    private String Date_to;
 
    
    public Generator(String d1, String d2)
    {
    	Date_from  =d1;
    	Date_to = d2;
    	provider_list = new ArrayList<Provider>();
        myConn = DBConnection.dbConnector();

    }

    public void run() throws SQLException {
        retrieve1();
    }

    /*
    retrieve 1 etap data
     */

    private void retrieve1() throws SQLException {

    
//
//        String query = "select LEVERANCIER, CFNAAM from aankoopfact a2\n" +
//                "WHERE a2.FACTURATIEDATUM  BETWEEN ? AND  ?  \n" +
//                "group by LEVERANCIER";
    	
    	// only AKROSTAL
    	String query = "select LEVERANCIER, CFNAAM from aankoopfact a2\n" +
                "WHERE a2.FACTURATIEDATUM  BETWEEN ? AND  ? and LEVERANCIER = '101057'  \n" +
                "group by LEVERANCIER";

        try
        {
            PreparedStatement takeDate = myConn.prepareStatement(query);
            takeDate.setString(1, Date_from);
            takeDate.setString(2, Date_to);
            ResultSet r = takeDate.executeQuery();

            while(r.next())
            {
                int leverancier = Integer.parseInt(r.getString(1));
                String leverancier_description = r.getString(2);                
                Provider obj = new Provider( leverancier_description,leverancier);
                
                provider_list.add(obj);
            }


            
            for(int i =  0 ; i < provider_list.size(); i++)
            {
            	retrive2(provider_list.get(i).getProvideID(), i);
            	
            	
            	
            	
            }
            
          
            /*
             * 
             * 
             *  ponizej trzeba poprawic aby mozna bylo jednoznacznie printowac liste
             * 
             */    
            for(int i  = 0 ; i< provider_list.size();i++)
            {
            	provider_list.get(i).showAllDataInObject();

            }
            
            
            
            takeDate.close();
            r.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    
    private void retrive2( int leverancier_id , int provider_list_id)
    {
    
    	  String query = "select FACTURATIENUMMER from aankoopfact a2\r\n" + 
    	  		"WHERE a2.FACTURATIEDATUM  BETWEEN ? AND  ? \r\n" + 
    	  		"and LEVERANCIER  = ? ";
        
    	  try {
    		  
    	  
             PreparedStatement takeDate = myConn.prepareStatement(query);
             takeDate.setString(1, Date_from);
             takeDate.setString(2, Date_to);
             takeDate.setInt(3, leverancier_id);
             ResultSet r = takeDate.executeQuery();
             
             while(r.next())
             {
                 int lev_id = Integer.parseInt(r.getString(1));              
                 Facturiate obj = new Facturiate(lev_id);     
                 provider_list.get(provider_list_id).insert( retrive3(lev_id, obj));            
             }
             
             
             
             takeDate.close();
             r.close();
             
    	  }
    	  catch(Exception e)
    	  {
              e.printStackTrace();
    	  }

    }

	private Facturiate retrive3(int lev_id, Facturiate o) {
		
		 String query ="select  a2.ORDERNUMMER , a2.FACTURATIEDATUM , a2.REGISTRATIEDATUM , a2.CFPROJECTNUMMER , \r\n" + 
		 		"a2.CFNAAM, a3.NETTOPRIJS , a3.MUNT , a3.CFARTIKELCODE, a3.CFARTIKELOMSCHRIJVING \r\n" + 
		 		"from aankoopfact a2\r\n" + 
		 		"left join aankoopfactdetail a3 \r\n" + 
		 		"on a2.FACTURATIENUMMER  = a3.FACTURATIENUMMER \r\n" + 
		 		"where  a2.FACTURATIENUMMER  = ?";
		
		  try {
    		  
	    	  
	             PreparedStatement takeDate = myConn.prepareStatement(query);
	             takeDate.setInt(1, lev_id);
	             ResultSet r = takeDate.executeQuery();
	             
	             while(r.next())
	             {
	                   
	            	 String ORDERNUMMER = r.getString(1);
	            	 String FACTURATIEDATUM= r.getString(2);
	            	 String REGISTRATIEDATUM= r.getString(3);
	            	 String CFPROJECTNUMMER= r.getString(4);
	            	 String CFNAAM= r.getString(5);
	            	 String NETTOPRIJS= r.getString(6);
	            	 String Munt= r.getString(7);
	            	 String CFARTIKELCODE= r.getString(8);
	            	 String CFARTIKELOMSCHRIJVING= r.getString(9);
	            	 
	            	 Invoice obj = new Invoice(ORDERNUMMER,FACTURATIEDATUM,REGISTRATIEDATUM,CFPROJECTNUMMER,CFNAAM,NETTOPRIJS,Munt,CFARTIKELCODE,CFARTIKELOMSCHRIJVING);
	            	 obj.add_to_sum();
	            	 o.insert_to_invoiceList(obj);
	            			 
	            
	             }
	             
	             
	             
	             takeDate.close();
	             r.close();
	             
	    	  }
	    	  catch(Exception e)
	    	  {
	              e.printStackTrace();
	    	  }
		
		  
		  return o;
	}
	
	public List<Provider>  getMainList()
	{
		return this.provider_list;
	}
    
}
