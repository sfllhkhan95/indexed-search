package sfllhkhan95.search.indexed.entity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Entity class to model a list of all the search resultsList.
 */
public class SearchResults {

    private ArrayList<SearchResult> resultsList = new ArrayList<SearchResult>();

    public void merge(SearchResult result) {
        try {
            SearchResult existing = this.find(result);
            existing.incrementFrequency();
        } catch (NullPointerException ex) {
            resultsList.add(result);
        }
    }

    private SearchResult find(SearchResult searchResult) {
        for (SearchResult result : resultsList) {
            if (result.getFilename().equals(searchResult.getFilename())) {
                return result;
            }
        }

        return null;
    }

    public void merge(SearchResults searchResults) {
        for (SearchResult result : searchResults.resultsList) {
            merge(result);
        }
    }

    public void sort() {
        Collections.sort(resultsList);
    }

    public ArrayList<SearchResult> getList() {
        return resultsList;
    }
}
