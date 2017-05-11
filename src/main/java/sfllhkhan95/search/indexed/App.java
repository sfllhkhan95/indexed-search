package sfllhkhan95.search.indexed;

import sfllhkhan95.search.indexed.bo.SearchUtil;
import sfllhkhan95.search.indexed.entity.SearchResult;
import sfllhkhan95.search.indexed.entity.SearchResults;

import java.util.Scanner;

/**
 * Executioner.
 */
public class App {

    public static void main(String[] args) {
        SearchUtil searchUtil = SearchUtil.getInstance();

        Scanner scanner = new Scanner(System.in);
        String query;
        System.out.print("Enter search query: ");
        while (scanner.hasNext()) {
            query = scanner.next();

            try {
                long start = System.currentTimeMillis();
                SearchResults results = searchUtil.search(query);
                System.out.printf("Returned %d result(s) in %d ms\n",
                        results.getList().size(),
                        System.currentTimeMillis() - start);

                System.out.println("Count\tFile\tQuery: " + query);
                for (SearchResult searchResult : results.getList()) {
                    System.out.printf("%d\t%s\n",
                            searchResult.getFrequency(),
                            searchResult.getFilename());
                }
            } catch (NullPointerException ex) {
                System.out.println("Your query '" + query + "' did not return any results.");

            }

            System.out.print("Enter search query: ");
        }
        scanner.close();
    }
}