package net.cbtltd.rest.atraveo.xmlexport;

public enum ProductType {
	APARTMENT(0),
	HOUSE(1),
	MOBILE_HOME(2),
	TENT(4);
	byte typeid;
	ProductType(int id){ typeid=(byte)id;}
	ProductType(byte id){ typeid=id;}
	public byte getId(){ return typeid; }
}
