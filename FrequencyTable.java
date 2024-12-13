/**
 * Represents a frequency table of words across multiple passages.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FrequencyTable extends ArrayList<FrequencyList>{
    // Empty Constructor
    public FrequencyTable(){}

    /**
     * Constructs a FrequencyTable from an ArrayList of FrequencyLists.
     *
     * @param s The ArrayList of FrequencyLists to initialize the table with.
     * @custom.precondition s is not null.
     * @custom.postcondition This FrequencyTable contains all FrequencyLists from s.
     */
    public FrequencyTable(ArrayList<FrequencyList> s){
        this.addAll(s);
    }

    /**
     * Builds a frequency table from a given list of passages.
     *
     * @param passages The ArrayList of Passages to build the table from.
     * @return A new FrequencyTable representing word frequencies across the passages.
     * @custom.precondition passages is not null and contains valid Passage objects.
     * @custom.postcondition Returns a FrequencyTable where each FrequencyList represents a unique word and its frequencies in each passage.
     */
    public static FrequencyTable buildTable (ArrayList<Passage> passages){
        ArrayList<FrequencyList> temp = new ArrayList<>();
        Set<String> words = new HashSet<>();

        for (Passage p: passages){
            words.addAll(p.getWords());
        }

        for (String s: words){
            FrequencyList freq_list = new FrequencyList(s, passages);
            temp.add(freq_list);
        }

        return new FrequencyTable(temp);
    }

    /**
     * Adds a new passage to the frequency table, updating frequencies for existing words.
     *
     * @param p The Passage to add to the table.
     * @throws IllegalArgumentException If the given passage is null or empty.
     * @custom.precondition p is not null and not empty.
     * @custom.postcondition The FrequencyLists in this table are updated with the word frequencies from passage p.
     */
    public void addPassage(Passage p) throws IllegalArgumentException{
        if (p == null || p.isEmpty()){
            throw new IllegalArgumentException();
        }

        for (FrequencyList f: this){
            f.addPassage(p);
        }
    }

    /**
     * Retrieves the frequency of a given word in a specific passage.
     *
     * @param word The word to get the frequency of.
     * @param p The Passage to check for the word frequency.
     * @throws IllegalArgumentException If the given passage and word is null or empty.
     * @return The frequency of the word in the passage.
     * @custom.precondition word is not null, p is not null.
     * @custom.postcondition returns an int which is frequency of the specified word.
     */
    public int getFrequency(String word, Passage p){
        if (p == null || p.isEmpty() || word == null || word.equals("")){
            throw new IllegalArgumentException();
        }
        return p.get(word);
    }
}
