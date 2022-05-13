import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<Character, ArrayList<String>> productions = new HashMap<Character, ArrayList<String>>();

    public static void main(String[] args) throws IOException {

        new Input(productions);
        new TrailLead(productions);
        System.out.println();


    }
}