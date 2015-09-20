package net.cbtltd.shared.image;

import net.cbtltd.shared.Image;

public class ImageDelete extends Image {

	public ImageDelete() {
		// TODO Auto-generated constructor stub
	}

	public ImageDelete(String title, String language) {
		super();
		this.setName(title);
		this.setLanguage(language);
	}
	
	public ImageDelete(int id, String language) {
		super(id, language);
		// TODO Auto-generated constructor stub
	}

	public ImageDelete(int id, String name, Type type, String notes,
			String language) {
		super(id, name, type, notes, language);
		// TODO Auto-generated constructor stub
	}

	public ImageDelete(String name, int productId, Type type, String notes,
			String language, String url) {
		super(name, productId, type, notes, language, url);
		// TODO Auto-generated constructor stub
	}

}
