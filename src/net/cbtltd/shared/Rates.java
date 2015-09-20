package net.cbtltd.shared;

public class Rates extends ModelTable {

	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public Service service() {
		return Service.PRICE;
	}

}
