package sfllhkhan95.search.indexed.entity;

/**
 * Entity class to model a search result.
 */
public class SearchResult implements Comparable<SearchResult> {

    /**
     * Search query.
     */
    private final String query;

    /**
     * Name of the file which contains the search query.
     */
    private final String filename;
    /**
     * Number of times the search query occurs in the file.
     */
    private Integer frequency = 1;

    public SearchResult(String query, String filename) {
        this.query = query;
        this.filename = filename;
    }

    public String getQuery() {
        return query;
    }

    public String getFilename() {
        return filename;
    }

    public Integer getFrequency() {
        return frequency;
    }

    void incrementFrequency() {
        frequency++;
    }

    public int compareTo(SearchResult o) {
        if (this.getFrequency() > o.getFrequency()) {
            return -1;
        } else {
            return 1;
        }
    }
}