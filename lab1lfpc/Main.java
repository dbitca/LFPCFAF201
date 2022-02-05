package com.example.lab1lfpc;
import java.util.Scanner;
class Automata {
    static final int S = 0, D = 1, R = 2, X = 3, INVALID = -1;
    static int state = S;

    // Function for the starting state (S)
    static void start(char c) {
        if (c == 'a') {
            state = S;
        }

        else if (c == 'b') {
            state = D;
        } else if (c == 'f') {
            state = R;
        } else {
            state = INVALID;
        }
    }

    // Function for the starting state (D)
    static void transition1a(char c) {
        if (c == 'c') {
            state = D;
        } else if (c == 'd') {
            state = R;
        } else {
            state = INVALID;
        }
    }

    //Function for the starting State (D) second option
    static void transition1b(char c) {
        if (c == 'c') {
            state = D;
        } else if (c == 'd') {
            state = X;
        } else {
            state = INVALID;
        }
    }

    // Function for the starting state (R)
    static void transition2(char c) {
        if (c == 'f') {
            state = X;
        } else if (c == 'b'){
        state = R;
        }
        else {
            state = INVALID;
        }
    }

    //Function for ending state (X)
    static void transition3(char C) {
        state = INVALID;
    }

    static int Check(char str[]) {
        int i, len = str.length;

        for (i = 0; i < len; i++) {
            if (state == S)
                start(str[i]);
            else if (state == D && i != len-1) {
                transition1a(str[i]);
            } else if (state == D && i == len-1) {
                transition1b(str[i]);
            } else if (state == R)
                transition2(str[i]);

            else if (state == X) {
                transition3(str[i]);
            } else return 0;
        }
        if (state == X){
            return 1;
        }
        else return 0;
    }

    public static void main(String []args)
    {
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        char str[] = expression.toCharArray();

        if (Check(str) == 1)
            System.out.printf("ACCEPTED");
        else
            System.out.printf("NOT ACCEPTED");
    }
}


