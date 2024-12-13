/**
 * Represents a list of word frequencies across multiple passages.
 */
import java.util.ArrayList;
import java.util.HashMap;


public class FrequencyList {
    private String word;
    private ArrayList<Integer> frequencies = new ArrayList<>();; 
    private HashMap<String,Integer> passageIndices = new HashMap<>();;
    private int index = 0;

    /**
     * Constructs a FrequencyList for a given word and a list of passages.
     *
     * @param word The word for this frequency list.
     * @param passages The list of passages to extract frequencies from.
     * @custom.precondition word is not null and passages is not null.
     * @custom.postcondition This FrequencyList contains the frequencies of the given word in each passage.
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        for (Passage p: passages){
            if (p.containsKey(word)){
                frequencies.add(p.get(word));
                passageIndices.put(p.getTitle(),index);
                index++;
            }
        }
    }

    /**
     * Gets the word associated with this frequency list.
     *
     * @return The word.
     */
    public String getWord(){
        return word;
    }

    /**
     * Sets the word associated with this frequency list.
     *
     * @param word The new word to set.
     */
    public void setWord(String word){
        this.word = word;
    }

    /**
     * Gets the list of frequencies.
     *
     * @return The list of word frequencies.
     */
    public ArrayList<Integer> getFrequencies(){
        return frequencies;
    }

    /**
     * Sets the list of word frequencies.
     *
     * @param frequencies A new ArrayList of Integer frequencies to set.
     */
    public void setFrequencies(ArrayList<Integer> frequencies){
        this.frequencies = frequencies;
    }

    /**
     * Returns a HashMap containing the title of passages and their index in the frequencies ArrayList.
     *
     * @return the HashMap of passage titles and their associated index.
     */
    public HashMap<String,Integer> getPassageIndices(){
        return passageIndices;
    }

    /**
     * Sets the HashMap of passage titles and their index in the frequencies ArrayList.
     *
     * @param passageIndices the HashMap to be set.
     */
    public void setPassageIndices(HashMap<String,Integer> passageIndices){
        this.passageIndices = passageIndices;
    }

    /**
     * Adds a new passage to the frequency list.
     * If the passage contains the word, its frequency is added to the list.
     *
     * @param p The passage to add.
     * @custom.precondition p is not null.
     * @custom.postcondition The frequency of the word in passage p is added to the FrequencyList.
     */
    public void addPassage(Passage p){
        if (p.containsKey(word)){
            frequencies.add(p.get(word));
            passageIndices.put(p.getTitle(),index);
            index++;
        }
    }

    /**
     * Gets the frequency of the word in the given passage.
     *
     * @param p The passage to get the frequency from.
     * @return The frequency of the word in the passage, or 0 if it is not present.
     * @custom.precondition p is not null.
     * @custom.postcondition The returned value is the frequency of this FrequencyList's word in the given passage.
     */
    public int getFrequency(Passage p){
        if (!p.containsKey(word)){
            return 0;
        }
        int ind = passageIndices.get(word);
        return frequencies.get(ind);
    }

}
