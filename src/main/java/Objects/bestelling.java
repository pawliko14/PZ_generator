package Objects;

public class bestelling {
	
	private int leverancier;
	private String ordernummer;
	private String statuscode;
	private String cfnaam;
	private String bestelldatum;
	private String Leverdatum;
	
	
	
	@Override
	public String toString() {
		return "bestelling [leverancier=" + leverancier + ", ordernummer=" + ordernummer + ", statuscode=" + statuscode
				+ ", cfnaam=" + cfnaam + ", bestelldatum=" + bestelldatum + ", Leverdatum=" + Leverdatum + "]";
	}
	public bestelling(int leverancier, String ordernummer, String statuscode, String cfnaam, String bestelldatum,
			String leverdatum) {
		super();
		this.leverancier = leverancier;
		this.ordernummer = ordernummer;
		this.statuscode = statuscode;
		this.cfnaam = cfnaam;
		this.bestelldatum = bestelldatum;
		Leverdatum = leverdatum;
	}
	public String getLeverdatum() {
		return Leverdatum;
	}
	public void setLeverdatum(String leverdatum) {
		Leverdatum = leverdatum;
	}
	public String getBestelldatum() {
		return bestelldatum;
	}
	public void setBestelldatum(String bestelldatum) {
		this.bestelldatum = bestelldatum;
	}
	public int getLeverancier() {
		return leverancier;
	}
	public void setLeverancier(int leverancier) {
		this.leverancier = leverancier;
	}
	public String getOrdernummer() {
		return ordernummer;
	}
	public void setOrdernummer(String ordernummer) {
		this.ordernummer = ordernummer;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getCfnaam() {
		return cfnaam;
	}
	public void setCfnaam(String cfnaam) {
		this.cfnaam = cfnaam;
	}

	
	
	

}
