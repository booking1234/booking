package net.cbtltd.rest.mybookingpal;

import java.util.Collection;

import net.cbtltd.shared.NameId;

public class ImageItem {
		
		public String entity;
		public String id;
		public String type;
		public Collection<NameId> item;

		public ImageItem() {}

		public ImageItem( String id, Collection<NameId> item) {
		
			this.id = id;
			this.item = item;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ImageItem [entity=");
			builder.append(entity);
			builder.append(", id=");
			builder.append(id);
			builder.append(", type=");
			builder.append(type);
			builder.append(", item=");
			builder.append(item);
			builder.append("]");
			return builder.toString();
		}


		
		
	}