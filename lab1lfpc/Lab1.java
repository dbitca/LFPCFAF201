package com.example.demo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

    public class Lab1 {

        public static void main(String[] args) throws FileNotFoundException {

            //citim info despre gramatica din fisier .txt
            File file = new File("C:\\Users\\Администратор\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\Lab_1.txt.txt");
            Scanner grammar = new Scanner(file);

            String input;
            //creez outer HashMap (key - valoare cu care lucram) si inner HashMap (cu reguli)
            HashMap<Character, HashMap<Character, Character>> autom = new HashMap<>();
            //System.out.println(autom);
            //adaug info din .txt in string "input"
            HashMap<Character, Character> rules = null;
            while (grammar.hasNextLine()) {
                input = grammar.nextLine();
                // in string noderules adaug fiecare stare folosita in gramatica
                // le imparte semnul "->"
                String[] noderules = input.split(" -> ");
//se verifica daca hashmapul este deja populat cu acest key se adauga toate posibilitatile pt key
                if (autom.containsKey(noderules[0].charAt(0))) {
                    // daca al 2-lea simbol este neterminal
                    if (noderules[1].length() == 2) {
                        autom.get(noderules[0].charAt(0)).put(noderules[1].charAt(0), noderules[1].charAt(1));
                    } else {
                        autom.get(noderules[0].charAt(0)).put(noderules[1].charAt(0), '!');
                    }
                   // System.out.println("Primul if:" + autom);
                  //  System.out.println("Primul if:" + rules);
                } else {
                    //populez HashMapRules
                    rules = new HashMap<>();
                    if (noderules[1].length() == 2) {
                        rules.put(noderules[1].charAt(0), noderules[1].charAt(1));
                    } else {
                        rules.put(noderules[1].charAt(0), '!');
                    }
                    //populez HashMapAutom
                    autom.put(noderules[0].charAt(0), rules);
                  //  System.out.println("Al doilea if:" + autom);
                  //  System.out.println("Al doilea if:" + rules);
                }

            }

// input cuvantul de verificat
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the word to be verified: ");
            input = scanner.nextLine();

            char node = 'S';
            //creez un string builder now pentru a arata nodurile traversate
            StringBuilder nodes = new StringBuilder();
            //nodurile o sa inceapa cu S
            nodes.append("S");
//traversam cuvantul cream lista de noduri traversate
            for (int i = 0; i < input.length(); i++) {
                //verificam in HashMap daca exista valoare S = ?
                if (autom.get(node).containsKey(input.charAt(i))) {
                    //la node se atribuie valoarea data
                    node = autom.get(node).get(input.charAt(i));
                    //daca nodul nu e terminal
                    if (node != '!')
                        // se adauga "->" + nodul gasit
                        nodes.append("->").append(node);
                }
                //daca ultimul nod e terminal print result
                if (i == input.length() - 1) {
                    if (node == '!') {
                        System.out.println("The word ->" + input + "<- is accepted !");
                        System.out.println("The traversed nodes are " + nodes);
                    } else {
                        System.out.println("The word " + input + " is not accepted ?!");
                    }
                }
            }
        }
    }

