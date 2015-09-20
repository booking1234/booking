package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name="search_quotes")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchQuotes extends Quotes {
	
	@XmlElement(name = "quotes_count")
	@SerializedName("quotes_count")
	private String quotesCount;
	@XmlElement(name = "page_number")
	@SerializedName("page_number")
	private String pageNumber;
	@XmlElement(name = "quotes_per_page")
	@SerializedName("quotes_per_page")
	private String quotesPerPage;
		
	public SearchQuotes() {
		super();
	}
	
	public SearchQuotes(Collection<Quote> quote) {
		super(quote);
	}
	
	public SearchQuotes(String type, String id, String message, Collection<Quote> quote, String xsl) {
		super(type, id, message, quote, xsl);
	}

	public String getQuotesCount() {
		return quotesCount;
	}

	public void setQuotesCount(String quotesCount) {
		this.quotesCount = quotesCount;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getQuotesPerPage() {
		return quotesPerPage;
	}

	public void setQuotesPerPage(String quotesPerPage) {
		this.quotesPerPage = quotesPerPage;
	}
			
}
