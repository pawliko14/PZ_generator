package Objects;

import java.util.ArrayList;
import java.util.List;

public class Facturiate {
	
	private int fv_number;
	List<Invoice> invoiceList;
	
	
	
	
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}


	public Facturiate(int n)
	{
		fv_number = n;
		invoiceList = new ArrayList<Invoice>();
	}
	

	public int  getFv_number()
	{
		return fv_number;
	}
	
	public  void insert_to_invoiceList(Invoice i)
	{
		invoiceList.add(i);
	}
	
	public void showInvoiceList() {
		
		for(Invoice i : invoiceList)
		{
			System.out.println(i.toString());
		}
		
	}
	
}
