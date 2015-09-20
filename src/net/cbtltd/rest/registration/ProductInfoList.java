package net.cbtltd.rest.registration;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

/**
 * Contains a list of products and additional information for pagination.
 * @author Vova Kambur
 *
 */

@XmlRootElement(name = "products")
public class ProductInfoList {

	@SerializedName("list")
	private List<ProductInfo> list; // Products list.
	@SerializedName("page")
	private String page;					// Page number at UI.
	@SerializedName("per_page")
	private String perPage;					// Count of products per page.
	@SerializedName("total_count")
	private String totalCount;				// Total count of products that are related with property manager.
	
	public ProductInfoList() {
		super();
	}
	
	public ProductInfoList(List<ProductInfo> products, String page, String perPage, String totalCount) {
		super();
		this.list = products;
		this.page = page;
		this.perPage = perPage;
		this.totalCount = totalCount;
	}
	
	@XmlElement(name = "list")
	public List<ProductInfo> getList() {
		return list;
	}
	public void setList(List<ProductInfo> productsList) {
		this.list = productsList;
	}

	@XmlElement(name = "page")	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	@XmlElement(name = "per_page")
	public String getPerPage() {
		return perPage;
	}
	public void setPerPage(String perPage) {
		this.perPage = perPage;
	}
	
	@XmlElement(name = "total_count")
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
}
