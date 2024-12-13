/**
 * Represents a passage of text, storing word frequencies and providing methods for analysis.
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Passage extends HashMap<String,Integer>{
    private String title;
    private int wordCount = 0;
    private HashMap<String,Double> similarTitles;
    
    /**
     * Gets the title of the passage.
     *
     * @return The title of the passage.
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Sets the title of the passage.
     *
     * @param title The new title for the passage.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Gets the total word count of the passage.
     *
     * @return The total number of words in the passage.
     */
    public int getWordCount(){
        return wordCount;
    }

    /**
     * Sets the word count for the passage.
     *
     * @param wordCount The new word count.
     */
    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    /**
     * Gets the map of similar titles and their similarity scores.
     *
     * @return The map of similar titles.
     */
    public HashMap<String,Double> getSimilarTitles(){
        return similarTitles;
    }

    /**
     * Sets the map of similar titles.
     *
     * @param similarTitles The new map of similar titles.
     */
    public void setSimilarTitles(HashMap<String,Double> similarTitles){
        this.similarTitles = similarTitles;
    }

    /**
     * Constructs a Passage object from a file.
     *
     * @param title The title of the passage.
     * @param file The file containing the passage text.
     * @custom.precondition file is not null and points to a valid readable file.
     * @custom.postcondition This Passage is initialized with word counts from the file.
     */
    public Passage(String title, File file){
        this.title = title;
        parseFile(file);
    }

    /**
     * Parses a file, extracts words, removes stop words, and updates word frequencies in the Passage.
     *
     * @param file The file to parse.
     * @throws IOException If an I/O error occurs while reading the file.
     * @custom.precondition file is not null and points to a valid readable file.
     * @custom.postcondition The word frequencies in this Passage are updated from the file contents.
     */
    public void parseFile(File file){
        try {
            String content = Files.readString(file.toPath());
            String[] words = content.strip().replace("\n"," ").replaceAll("[^a-zA-Z\\s]", "").split(" ");
            String[] lineList = removeStop(words);
            for (String s: lineList){
                wordCount++;
                if (this.containsKey(s.toLowerCase())){
                    int val = this.get(s.toLowerCase());
                    this.put(s.toLowerCase(), val+1);
                } else {
                    this.put(s.toLowerCase(), 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /**
     * Removes stop words from an array of words.
     *
     * @param s The array of words.
     * @return A new array containing only the non-stop words, converted to lowercase.
     * @custom.precondition s is not null.
     * @custom.postcondition Returns a new array containing words from s that are not stop words.
     */
    private static String[] removeStop(String[] s){
        String[] stopWords = {
            "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", 
            "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", 
            "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", 
            "from", "further", "had", "has", "have", "having", "he", "hed", "hell", "hes", "her", "here", 
            "heres", "hers", "herself", "him", "himself", "his", "how", "hows", "i", "id", "ill", "im", 
            "ive", "if", "in", "into", "is", "it", "its", "itself", "lets", "me", "more", "most", "my", 
            "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", 
            "out", "over", "own", "same", "she", "shed", "shell", "shes", "should", "so", "some", "such", 
            "than", "that", "thats", "the", "their", "theirs", "them", "themselves", "then", "there", "theres", 
            "these", "they", "theyd", "theyll", "theyre", "theyve", "this", "those", "through", "to", "too", 
            "under", "until", "up", "very", "was", "we", "wed", "well", "were", "weve", "what", "whats", "when", 
            "whens", "where", "wheres", "which", "while", "who", "whos", "whom", "why", "whys", "with", "would", 
            "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves"
        };
        Set<String> stopWordsSet = new HashSet<>(List.of(stopWords));
        List<String> newList = new ArrayList<>();
        for (String a: s){
            if (!(stopWordsSet.contains(a.toLowerCase())|| a.isEmpty())) newList.add(a.toLowerCase());
        }
        return newList.toArray(new String[0]);
    }

    /**
     * Calculates the cosine similarity between two passages.
     *
     * @param passage1 The first passage.
     * @param passage2 The second passage.
     * @return The cosine similarity between the two passages.
     * @custom.precondition passage1 and passage2 are not null.
     * @custom.postcondition Returns a value representing the cosine similarity.
     */
    public static double cosineSimilarity(Passage passage1, Passage passage2) {
        double x1 = 0.0, x2 = 0.0, x3 = 0.0;

        Set<String> allKeys = new HashSet<>(passage1.keySet());
        allKeys.addAll(passage2.keySet());

        for (String key : allKeys) {
            int count1 = passage1.containsKey(key) ? passage1.get(key) : 0;
            int count2 = passage2.containsKey(key) ? passage2.get(key) : 0;

            x1 += count1 * count2;
            x2 += count1 * count1;
            x3 += count2 * count2;
        }

        return (x1/ (Math.sqrt(x2) * Math.sqrt(x3)));
    }


    /**
     * Gets the average frequency of a word in the passage as a percentage.
     *
     * @param word The word to get the frequency of.
     * @return The frequency of the word, calculated as word occurrences divided by total word count.
     */
    public double getWordFrequency(String word){
        int freq;
        
        if (this.containsKey(word)){
            freq = this.get(word);
        } else {
            freq = 0;
        }

        return freq/wordCount;
    }

    /**
     * Gets the set of unique words in the passage.
     *
     * @return The set of unique words.
     */
    public Set<String> getWords(){
        return this.keySet();
    }

    /**
     * Returns a string representation of the Passage, displaying word frequencies.
     *
     * @return String representation of the Passage's word frequencies.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : this.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            sb.append("Key: " + key + ", Value: " + value + "\n");
        }
        return sb.toString();
    }

}
