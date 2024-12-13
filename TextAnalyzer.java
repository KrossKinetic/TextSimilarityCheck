/**
 * Main method java class that executes the Passage class and performs cosine similarity on passages.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TextAnalyzer {
    FrequencyTable frequencyTable = new FrequencyTable();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the similarity percentage: ");
        int num = (int) (scanner.nextDouble()*100);
        scanner.nextLine();
        System.out.print("Enter the directory of a folder of text files: ");
        String dir = scanner.nextLine();
        System.out.println("Reading texts...");
        File[] listFiles = new File(dir).listFiles();
        ArrayList<Passage> list_pass = new ArrayList<>();
        for(File i : listFiles){
            if (i.getName().equals("StopWords.txt")){
                continue;
            }
            list_pass.add(new Passage(i.getName(), i));
        }
        list_pass.sort(Comparator.comparing(Passage::getTitle));
        StringBuilder table = new StringBuilder();
        StringBuilder table2 = new StringBuilder();
        table.append("Text (title)             | Similarities (%)\n");
        table.append("--------------------------------------------------------------------------------\n");
        table2.append("Suspected Texts With Same Authors\n");
        table2.append("--------------------------------------------------------------------------------\n");
        Set<String> printedPairs = new HashSet<>();
        int passage_length = list_pass.size();
        int cur_passage = 0;
        for(Passage p1: list_pass){
            String c1 = p1.getTitle().replace(".txt", "");
            String c2 = "";
            int count = 0;
            for (Passage p2: list_pass){
                if (!p1.getTitle().equalsIgnoreCase(p2.getTitle())){
                    int num_sim = (int) Math.round(Passage.cosineSimilarity(p1, p2)*100);
                    if (count%2==0 && count != 0){
                        c2 += ",\n                         |";
                        c2 += " " + p2.getTitle().replace(".txt", "") + "(" + num_sim + "%)";
                    } else {
                        c2 += "," + p2.getTitle().replace(".txt", "") + "(" + num_sim + "%)";
                    }
                    count++;
                    if (num_sim >= num && !printedPairs.contains(p1.getTitle()+p2.getTitle())){
                        printedPairs.add(p1.getTitle() + p2.getTitle());
                        printedPairs.add(p2.getTitle() + p1.getTitle());
                        table2.append("'"+p1.getTitle().replace(".txt", "") + "' and '" + p2.getTitle().replace(".txt", "") + "' may have the same author (" + num_sim + "% similar).\n");
                    }
                }
            }
            table.append(String.format("%-24s | %s \n", c1, c2.strip().substring(1)));
            if (cur_passage+1 < passage_length){
                table.append("--------------------------------------------------------------------------------\n");
            }
            cur_passage++;
        }
                
        System.out.println(table.toString());
        System.out.println(table2.toString());
        scanner.close();
    }
}
