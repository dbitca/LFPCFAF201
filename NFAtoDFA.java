package com.example.lab2lfpc;

import java.util.*;

public class NFAtoDFA {

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of states in NFA: ");
        int nrStates = sc.nextInt();

        System.out.print("Enter the states (NOTE: The states should be denoted by single characters. EX: S, A, 0, 1...) : ");
        char[] statesList = new char[nrStates];
        //create map to store state and number of state
        HashMap<Character, Integer> States = new HashMap<>();

        for(int i=0; i<nrStates; i++)
        {
            //add states in array
            statesList[i] = sc.next().charAt(0);
            //populate HashMap
            States.put(statesList[i], i);
        }
     //   System.out.println(States);
        System.out.print("Enter the initial state : ");
        char initst = sc.next().charAt(0);

        System.out.print("Enter the no. of final states : ");
        int nrfinalst = sc.nextInt();

        System.out.print("Enter the final states : ");
        //create Hashmap to store final states
        HashMap<Character, Integer> finalst = new HashMap<>();
        for(int i=0; i<nrfinalst; i++) {
            //map all states to the value of 1
            finalst.put(sc.next().charAt(0), 1);
        }
      //  System.out.println(finalst);
        System.out.print("Enter the number of transition characters: ");
        int nrtransitions = sc.nextInt();

        System.out.print("Enter transition characters: ");
        char[] transitions = new char[nrtransitions];
        for(int i=0; i<nrtransitions; i++) {
            //populate transitions array
            transitions[i] = sc.next().charAt(0);
        }
//create a list of new states
        Queue<String> newStates = new LinkedList<>();
        //create hashmap populate with initial state map to value 1
        HashMap<String, Integer> DFA = new HashMap<>();
        newStates.add(initst+"");
        DFA.put(initst+"", 1);

        System.out.println("Enter the NFA transition table.");
        System.out.println("NOTE: if a character leads to multiple states, write all the states without spaces.");
        //create 2D array to store table
        String[][] tableNFA = new String[nrStates][nrtransitions];

        System.out.print("********************\n\t");
        for(int i=0; i<nrtransitions; i++) {
            System.out.print(transitions[i] + "\t");
        }
        System.out.println();

        for(int i=0;i<nrStates;i++)
        {
            System.out.print(statesList[i]+"->\t");
            for(int j=0;j<nrtransitions;j++)
            {
                //store input in a 2d table
                tableNFA[i][j] = sc.next();
            }
        }

        System.out.println("\nThe DFA transition table: ");
        //code for creating the transition table starts here
        for(int i=0; i<nrtransitions; i++) {
            System.out.print("\t" + transitions[i]);
        }
        System.out.println();
        String tableDFA[][] = new String[100][nrtransitions];
        String finalStatesDFA[] = new String[100];
        int nrFinalStates = 0;
        System.out.println("newStates:" + newStates);
        for(int i = 0; newStates.size() > 0; i++)
        {
            //retrieve current state
            String cur_state = newStates.remove();
            System.out.println("cur_state" + cur_state);
            if (cur_state == ""){
                //if there is no current state to check, break the for loop
                continue;
            }
//create an infinite loop because we have multiple conditions
        while (true){
                boolean isFinal = false;
//if the current state is the initial state print and break loop
                if (cur_state.length() == 1 && cur_state.charAt(0) == initst) {
                    System.out.print("\t->"+cur_state+"|");
                 break;
                }
//se verifica daca current state are final state in el
                for (int j = 0; j < cur_state.length(); j++) {
                    if (finalst.containsKey(cur_state.charAt(j))) {
                        System.out.print("*"+cur_state+"|");
                        isFinal = true;
                       break;
                    }
                }
                //daca nu are final state print state
                if (!isFinal){
                    System.out.print(cur_state+"|");
                  break;
                }
                break;
         }
//here populate table DFA
            for(int j=0;j<nrtransitions;j++)
            {
                //assign default value to table
                tableDFA[i][j] = " ";
//check if there is such state in newstates then add to tableDFA
                if(cur_state.length() == 1 && !States.containsKey(cur_state.charAt(0)))
                {
                    tableDFA[i][j] = cur_state;
//if DFA does not contain current_state add state to new newstates
                    if(!DFA.containsKey(tableDFA[i][j]))
                    {
                        newStates.add(tableDFA[i][j]);
                        //add new state in DFA map it to 1
                        DFA.put(tableDFA[i][j], 1);
                    }
//check if the current state is final state
                    if(finalst.containsKey((tableDFA[i][j].charAt(0)))) {
                        //add in string of final newstates the current values
                        finalStatesDFA[nrFinalStates++] = tableDFA[i][j];
                    }
                    //print current value regardless if any of the conditions have been met
                    System.out.print(tableDFA[i][j]+"\t");
                    continue;
                }
                boolean isFinal = false;
                HashMap<Character, Integer> newstates;
                newstates = new HashMap<>();

                for(int k=0; k<cur_state.length(); k++)
                {
                    if(!States.containsKey(cur_state.charAt(k))) {
                        continue;
                    }

                    if(finalst.containsKey(cur_state.charAt(k))) {
                        isFinal = true;
                    }
//iterate through all chars in cur_state newstates and add all newstates that come from it
                    for(int ch=0; ch<tableNFA[States.get(cur_state.charAt(k))][j].length(); ch++)
                    {
//check if this state has been added if not add new state
                        if(!newstates.containsKey(tableNFA[States.get(cur_state.charAt(k))][j].charAt(ch)) && States.containsKey(tableNFA[States.get(cur_state.charAt(k))][j].charAt(ch)))
                        {
                            tableDFA[i][j] += tableNFA[States.get(cur_state.charAt(k))][j].charAt(ch);
                            newstates.put(tableNFA[States.get(cur_state.charAt(k))][j].charAt(ch), 1);
                        }
                    }
                }

                if(!DFA.containsKey(tableDFA[i][j]))
                {
                    newStates.add(tableDFA[i][j]);
                    DFA.put(tableDFA[i][j], 1);
                }

                if(isFinal) {
                    finalStatesDFA[nrFinalStates++] = tableDFA[i][j];
                }
                System.out.print(tableDFA[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
