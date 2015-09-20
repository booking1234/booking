package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.SearchQuotes;
import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="search_response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResponse extends ApiResponse {
	
	public SearchResponse() {
		super();
	}

	@XmlElement(name="search_quotes")	
	private SearchQuotes searchQuotes;

	public SearchQuotes getSearchQuotes() {
		return searchQuotes;
	}

	public void setSearchQuotes(SearchQuotes searchQuotes) {
		this.searchQuotes = searchQuotes;
	}
}
