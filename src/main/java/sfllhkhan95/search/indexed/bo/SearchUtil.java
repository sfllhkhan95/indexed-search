package sfllhkhan95.search.indexed.bo;

import sfllhkhan95.search.indexed.entity.SearchResults;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Search classes.
 */
public class SearchUtil {

    private static SearchUtil ourInstance;
    private Map<String, SearchResults> index;
    private double dataSize = 0.0;

    private SearchUtil() {
        System.out.print("Indexing ... ");
        long start = System.currentTimeMillis();

        // Open all the text files
        String SEARCH_DIRECTORY = "src/main/resources";
        Map<String, File> textFiles = getTextFiles(SEARCH_DIRECTORY);

        // Build index
        IndexBuilder indexBuilder = new IndexBuilder(textFiles);
        index = indexBuilder.getIndex();

        System.out.printf("Finished %d files (%.3f MB) in %.5f s.\n",
                textFiles.size(),
                dataSize / (1024 * 1024),
                (System.currentTimeMillis() - start) / 1000.f);
    }

    public static SearchUtil getInstance() {
        if (ourInstance == null) {
            ourInstance = new SearchUtil();
        }

        return ourInstance;
    }

    private Map<String, File> getTextFiles(String directory) {
        Map<String, File> textFiles = new HashMap<String, File>();
        File[] allFiles = new File(directory).listFiles();
        try {
            for (File file : allFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    textFiles.put(file.getName(), file);
                    dataSize += file.length();
                }
            }
        } catch (NullPointerException ignored) {

        }

        return textFiles;
    }

    public SearchResults search(String query) {
        SearchResults result = index.get(query.toLowerCase());
        try {
            result.sort();
        } catch (Exception ignored) {

        }
        return result;
    }

}
