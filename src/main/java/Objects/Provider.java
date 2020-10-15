package Objects;

import java.util.ArrayList;
import java.util.List;

public class Provider {

	private String providerName;
	private int provideID;
	private List<Facturiate> facturiate_list;
	
	
	
	
	public List<Facturiate> getFacturiate_list() {
		return facturiate_list;
	}


	public Provider(String providerN,  int provideI)
	{
		facturiate_list = new ArrayList<Facturiate>();
		providerName= providerN;
		provideID = provideI;
	}
	
	
	public void insert(Facturiate obj)
	{
		facturiate_list.add(obj);
	}


	@Override
	public String toString() {
		return "Provider [providerName=" + providerName + ", provideID=" + provideID + ", facturiate_list="
				+ facturiate_list + "]";
	}


	public String getProviderName() {
		return providerName;
	}


	public int getProvideID() {
		return provideID;
	}
	
	
	public void showFacturiateList()
	{
		for(Facturiate  i : facturiate_list)
		{
			System.out.println("el: " + i + " : " + i.getFv_number());
		}
	}
	
	
	public void showAllDataInObject()
	{
		System.out.println("Name: " + this.providerName + ", provider ID: " + this.provideID);
	
		for(int i  = 0 ; i< facturiate_list.size() ;i++)
		{
			System.out.println(facturiate_list.get(i).getFv_number());
			facturiate_list.get(i).showInvoiceList();
			System.out.println(" ");

		}

	}
	
	
	
}
