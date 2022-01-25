import org.jsoup.nodes.Document;
import java.io.IOException;
import org.jsoup.Jsoup;
import java.util.*;

public class SDLC_Assignment {
	
	// Generate string array for each word in poem
	// Removes punctuation and makes all words lowercase
	// @param url URL to pull poem from. Assumes URL has poem located in <div class="chapter">
	// @return array of all words in poem without punctuation and lowercased
	static String[] generate_poem(String url) {
		String poem = null;
		Document doc;
		// Loop through to account for a potential temporary connection issue
		do {
			try {
				// Connect to url and recover text from <div class="chapter"> tag
	            doc = Jsoup.connect(url).get();
	            poem = doc.select("div.chapter").first().text();
	        } catch (IOException e) {
	        	// Inform user the program is going to attempt the request again
	            System.out.println("Error connecting to URL...Make sure the server hosting the URL is accessible\nTrying again.\n");
	        }
		} while (poem == null);
		// Remove all punctuation, make lowercase, and split into array with whitespace as a delimiter
		return poem.replaceAll("[-—]", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
	}
	
	// Count frequency of words in passed array
	// @param arr String array of individual words. Assumes words are lowercase and contain no punctuation
	// @return A mapping with the key being an entry from arr and the value being the frequency in which it occurred
	static Map<String, Integer> count_freq(String[] arr){
        Map<String,Integer> mp = new TreeMap<>();
 
        // Loop to iterate over the words
        for(int i=0;i<arr.length;i++) {
            // Condition to check if the array element is present the hash-map
            if(mp.containsKey(arr[i])) {
                mp.put(arr[i], mp.get(arr[i])+1);
            } else {
                mp.put(arr[i],1);
            }
        }
        
        return mp;
    }
	
	// Comparable to sort mapping by value
	// @param map Mapping to sort by value. Assumes value within mappings can be compared
	// @return returns mapping sorted by value
	public static <K, V extends Comparable<V> > Map<K, V> valueSort(final Map<K, V> map)
    {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<K>(){
              
            public int compare(K k1, K k2){
  
                int comp = map.get(k2).compareTo(map.get(k1));
                if (comp == 0)
                     return 1;
                else
                     return comp;
            }
        };
  
        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }
	
	public static void main(String[] args) {
		// Calling the method generate_poem
		String[] words = generate_poem("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		// Calling the method count_freq
		Map<String, Integer> map = count_freq(words);
		 // Calling the method valueSort
        Map<String, Integer> sortedMap = valueSort(map);
        
        // Iterating through and printing results
        int x = 1;
        for (Map.Entry<String, Integer> en : sortedMap.entrySet()) {
        	System.out.println(x + ": " + en.getKey() + " - " + en.getValue());
        	// Uncomment for only top 20 results
        	// if (x == 20) break;
            x++;
        }
        
	}
}


//			Alternative method
//
//	// Get a set of the entries on the sorted map
//	Set<Map.Entry<String, Integer>> set = sortedMap.entrySet();
//
//	// Get an iterator
//	Iterator<Map.Entry<String, Integer>> i = set.iterator();
//
//	// Iterate through and print key value pair
//	for (int x = 0; x < 20 & x < set.size(); x++) {
//  	Map.Entry<String, Integer> mp = (Map.Entry<String, Integer>)i.next(); 
//  	System.out.println((x + 1) + ": " + mp.getKey() + " - " + mp.getValue());
//	}
