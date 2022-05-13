import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {
    public static String Vt = "";

    Input(HashMap<Character, ArrayList<String>> productions) throws IOException {
        File file = new File("C:\\Users\\Администратор\\IdeaProjects\\Lab6LFPC\\src\\input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String in;
        //Determine non-terminal symbols and storage them as keys in the hashmap
        in = reader.readLine();
        for (int i = 2; i < in.length(); i++)
            //Check if the character is UpperCase
            if (in.charAt(i) >= 'A' && in.charAt(i) <= 'Z' && in.charAt(1) == 'n')
                productions.put(in.charAt(i), new ArrayList<String>());

        //Determine terminal symbols and store them in a string
        in = reader.readLine();
        if (in.charAt(1) == 't'){
            for (int i = 2; i < in.length(); i++)
                if (in.charAt(i) >= 'a' && in.charAt(i) <= 'z')
                    Vt += String.valueOf(in.charAt(i));
        }

        String[] arrOfStr;
        while ((in = reader.readLine()) != null)
            if (in.charAt(0) >= 'A' && in.charAt(0) <= 'Z'){
                arrOfStr = in.split("->",2);
                productions.get(in.charAt(0)).add(arrOfStr[1]);
            }
    }

    public static String getVt(){
        return Vt;
    }

}