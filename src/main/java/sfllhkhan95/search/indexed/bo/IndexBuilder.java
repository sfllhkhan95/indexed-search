package sfllhkhan95.search.indexed.bo;

import sfllhkhan95.search.indexed.entity.SearchResult;
import sfllhkhan95.search.indexed.entity.SearchResults;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * IndexBuilder indexes the files which is then used for searching.
 *
 * @author Muhammad Saifullah K
 */
class IndexBuilder {

    private Map<String, SearchResults> index;

    IndexBuilder(Map<String, File> files) {
        index = new HashMap<String, SearchResults>();

        for (String filename : files.keySet()) {
            Map<String, SearchResults> childIndex = buildIndex(filename, files.get(filename));
            index = join(childIndex, index);
        }
    }

    public Map<String, SearchResults> getIndex() {
        return index;
    }

    private String filterQuery(String query) {
        query = query.replace(".", "");
        query = query.replace(",", "");
        query = query.replace("!", "");
        query = query.replace("?", "");
        query = query.replace("\"", "");
        query = query.replace("\n", "");
        query = query.replace("\t", "");
        query = query.replace("\r", "");
        query = query.replace(":", "");
        return query.toLowerCase();
    }

    private Map<String, SearchResults> buildIndex(String filename, File file) {
        Map<String, SearchResults> index = new HashMap<String, SearchResults>();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);

            String line;
            while ((line = bf.readLine()) != null) {
                for (String query : line.split(" ")) {
                    query = filterQuery(query);
                    if (!query.equals("")) {
                        SearchResults searchResults;
                        if (!index.containsKey(query)) {
                            searchResults = new SearchResults();
                            searchResults.merge(new SearchResult(query, filename));
                        } else {
                            searchResults = index.get(query);
                            searchResults.merge(new SearchResult(query, filename));
                        }
                        index.put(query, searchResults);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return index;
    }

    private Map<String, SearchResults> join(Map<String, SearchResults> childIndex, Map<String, SearchResults> masterIndex) {
        for (String key : childIndex.keySet()) {
            if (masterIndex.keySet().contains(key)) {
                masterIndex.get(key).merge(childIndex.get(key));
            } else {
                masterIndex.put(key, childIndex.get(key));
            }
        }

        return masterIndex;
    }
}