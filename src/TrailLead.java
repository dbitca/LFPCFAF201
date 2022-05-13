import java.util.ArrayList;
import java.util.HashMap;

public class TrailLead {

    public static HashMap<Character, ArrayList<Character>> leadTable = new HashMap<Character, ArrayList <Character>>();
    public static HashMap<Character, ArrayList<Character>> trailTable = new HashMap<Character, ArrayList <Character>>();


    TrailLead(HashMap<Character, ArrayList<String>> productions){

        productions.forEach(
                (key, value) -> {
                    leadTable.put(key, new ArrayList<Character>());
                    trailTable.put(key, new ArrayList<Character>());

                    buildingFirstTable(key, value);
                    buildingLastTable(key, value);
                }
        );
        checkFirstTable(productions);
        checkLastTable(productions);

        System.out.println();
        new SimplePrecedence(productions, leadTable, trailTable);

    }

    public void buildingFirstTable(char key, ArrayList<String> value){
        for (int i = 0; i < value.size(); i++){
            if (!leadTable.get(key).contains(value.get(i).charAt(0)))
                leadTable.get(key).add(value.get(i).charAt(0));
        }
    }

    public void checkFirstTable (HashMap<Character, ArrayList<String>> productions){
        leadTable.forEach(
                (key, value) -> {
                    for (int i = 0; i < value.size(); i++){
                        if (value.get(i) >= 'A' && value.get(i) <= 'Z' && value.get(i) != key)
                            buildingFirstTable(key, productions.get(value.get(i)));
                    }
                }
        );
        System.out.println("Table of first elements: " + leadTable);
    }

    public void buildingLastTable(char key, ArrayList<String> value){
        for (int i = 0; i < value.size(); i++){
            //If last table does not contain
            if (!trailTable.get(key).contains(value.get(i).charAt(value.get(i).length()-1)))
                trailTable.get(key).add(value.get(i).charAt(value.get(i).length()-1));
        }
    }

    public void checkLastTable (HashMap<Character, ArrayList<String>> productions){
        trailTable.forEach(
                (key, value) -> {
                    for (int i = 0; i < value.size(); i++){
                        if (value.get(i) >= 'A' && value.get(i) <= 'Z' && value.get(i) != key)
                            buildingLastTable(key, productions.get(value.get(i)));
                    }
                }
        );
        System.out.println("Table of last elements: " + trailTable);
    }
}