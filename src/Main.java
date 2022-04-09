
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Администратор\\IdeaProjects\\Chomsky5\\src\\input.txt");
        Scanner input = new Scanner(file);
        List<String> parent = new ArrayList<>();
        List<List<String>> child = new ArrayList<>();
        List<String> terminals = new ArrayList<>();
        List<String> non_term = new ArrayList<>();

        readinput(input, parent, child, terminals, non_term);
        print(child, parent);
        removeEpsilon(child, parent);
        removeunitproduct(parent,child);
        removenonprod(parent,child, non_term);
        removeinaccessible(parent, child, non_term);
        removelength2(parent,child, non_term);
        addterminals(parent, child, terminals,non_term);

    }
    static void readinput (Scanner input, List<String> parent, List<List<String>> child,List<String> terminals, List<String> non_term){
        boolean has_eps = false;
        while (input.hasNext()) {
            List<String> input_as_line = Arrays.asList(input.nextLine().split(" "));
            parent.add(input_as_line.get(0));
            child.add(new ArrayList<>());
            child.get(child.size() - 1).addAll(input_as_line.subList(2, input_as_line.size()));
            //adding terminals and non-terminals
            for (String s : input_as_line) {
                // if (!s.equals("->")) {
                for (char c : s.toCharArray()) {
                    if (c == '*') {
                        has_eps = true;
                    } else if (String.valueOf(c).toUpperCase().equals(String.valueOf(c))) {
                        String t = new String(String.valueOf(c));
                        if (!non_term.contains(t)) {
                            non_term.add(t); //if capital letter - add to non_term list
                        }
                    } else {
                        String t = new String(String.valueOf(c));
                        if (!terminals.contains(t)) {
                            terminals.add(String.valueOf(c)); //if small add to terminals
                        }
                    }
                }
            }
        }
        // f-ie ca sa stearga aceeasi litera din stanga daca apare de mai multe ori + transfer litere din dreapta
        List<Integer> remove = new ArrayList<>();
        for (int i = 0; i < parent.size(); i++) {
            for (int j = i + 1; j < parent.size(); j++) {
                if (parent.get(i).equals(parent.get(j))) {
                    child.get(i).addAll(child.get(j));
                    remove.add(j);
                }
            }
        }
        int k = 0;
        for (int i : remove) {
            parent.remove(i - k);
            child.remove(i - k);
            k += 1;
        }
    }
    //functia returneaza literele din dreapta care duc la epsilon direct sau indirect
    static List<String> eps_non_term(List<List<String>> child, List<String> parent){
        List<String> epsilon = new ArrayList<>();
        for (int i = 0; i < child.size(); i++){
            if(child.get(i).contains("*")){
                epsilon.add(parent.get(i));
            }
        }
        boolean added_new = true;
        while(added_new){
            added_new = false;
            for (int i =0; i < child.size(); i++){
                for(String word: child.get(i)){
                    boolean all_eps = true;
                    //verifica care litere duc indirect la epsilon
                    for (char c: word.toCharArray()){
                        if(!epsilon.contains(String.valueOf(c))){
                            all_eps = false;
                            break;
                        }
                    }
                    //daca litera parinte nu este deja in lista epsilon adaugam in lista
                    if (all_eps && !epsilon.contains(parent.get(i))){
                        added_new = true;
                        epsilon.add(parent.get(i));
                        break;
                    }
                }
            }
        }
        return epsilon;
    }

    private static void removeEpsilon(List<List<String>> child, List<String> parent){
        List<String> epsilon = eps_non_term(child, parent);
        System.out.println("\n Epsilon non terminals");
        for (String s: epsilon){
            System.out.print(s + " ");
        }
        System.out.println();
        for(int i = 0; i< parent.size(); i++){
            for(int j = 0; j < child.get(i).size(); j++){
                String word = child.get(i).get(j);
                for(char c: word.toCharArray()){
                    //adauga la sfarsit children care contin parinti care duc la epsilon
                    if(epsilon.contains(String.valueOf(c))){
                        if(word.length() == 1){
                            int index = parent.indexOf(String.valueOf(c));
                            for (String addw: child.get(index)){
                                if(!addw.equals("*")){
                                    child.get(i).add(addw);
                                } else continue;
                            }
                        }
                        else {
                            child.get(i).add(word.replaceFirst(String.valueOf(c), ""));
                        }
                        break;
                    }
                }
            }
        }
        List<String> remove = new ArrayList<>();
        for (String left: parent){
         if (left.equals("S")) continue;
         //se sterge "*" din copii care contin epsilon
            child.get(parent.indexOf(left)).remove("*");
            if(child.get(parent.indexOf(left)).size()==0){
                //daca parintele duce doar la "*" se adauga parintele in lista to remove
                remove.add(left);
            }
        }
        //sterge din lista parintii care duc doar la epsilon
        //sterge din lista epsilon parintii care s-au sters
        for (String left : remove){
         eps_non_term(child,parent).remove(left);
         child.remove(parent.indexOf(left));
         parent.remove(left);
        }
        print(child, parent);
    }

    private static void removeunitproduct(List<String> parent, List<List<String>> child){
        for(String left: parent){
            List<String> new_r = new ArrayList<>();
            for (String derivations: child.get(parent.indexOf(left))){
                //daca child e o singura litera si e uppercase
                if(derivations.length() < 2 && derivations.toUpperCase().equals(derivations)){
                    //retrieve copii parintelui la care se deriveaza
                    List<String> r = child.get(parent.indexOf(derivations));
                    //itereaza prin fiecare copil
                    for (String r_ : r){
                        //daca copilul nu este unit production adauga in new_r toate productiile care deriva din unit production
                        if (r_.length() == 1 && r_.toLowerCase().equals(r_)){
                            new_r.add(r_);
                        }
                        if(r_.length() == 2){
                            new_r.add(r_);
                        }
                        if (r_.length() == 3){
                            new_r.add(r_);
                        }
                        if (r_.length() == 4){
                            new_r.add(r_);
                        }
                        if (r_.length() == 5){
                            new_r.add(r_);
                        }

                    }
                }
                else{
                    //daca nu este upper case adaugam in new_r derivarile de la parent
                    new_r.add(derivations);
                }
                    }
            child.get(parent.indexOf(left)).clear();
            child.get(parent.indexOf(left)).addAll(new_r);

                }
        System.out.println("Removing unit productions");
        print(child, parent);
            }

private static void removenonprod(List<String> parent, List<List<String>> child, List<String> nonterminals){
        List<String> nonproductive = new ArrayList<>();
        //daca nu este parinte cu aceeasi valoare ca si nonterminal de sters
        for (String non_term: nonterminals){
            if(!parent.contains(non_term)){
                nonproductive.add(non_term);
            }
        }
        int i =0;
        for (List<String> words: child){
            i+=1;
            List<String> remove = new ArrayList<>();
            for (String word: words){
                //daca copilul are un non_term neproductiv de sters copilul
                for (char c: word.toCharArray()){
                    if(nonproductive.contains(String.valueOf(c))){
                        remove.add(word);
                        break;
                    }
                }
            }
            child.get(i-1).removeAll(remove);
        }
        //daca copilul era doar non_terminalul, trebuie sters parintele
        List<Integer> remove = new ArrayList<>();
        int k = 0;
        for (String par : parent){
            if(child.get(parent.indexOf(par)).size() == 0){
                remove.add(k);
            }
            k += 1;
        }
        k = 0;
        for (int j: remove){
            nonterminals.remove((parent).get(j - k));
            child.remove(j - k);
            parent.remove(j -k);
            k+=1;
        }
        if(nonproductive.size() != 0){
            nonterminals.removeAll(nonproductive);
            System.out.println("\nNonproductive nonterminals are: ");
            for (String s: nonproductive){
                System.out.print(s + " ");
            }
            System.out.println();
            print(child,parent);
        }
        else
        {
            System.out.println("\nNo nonproductive nonterminals");
        }
}
static  void removeinaccessible(List<String> parent, List<List<String>> child, List<String> nonterminals){
        List<String> inaccessible = new ArrayList<>();
        for (String nonterminal : parent){
            if (nonterminal.equals("S")) continue;
            boolean found = false;
            for(List<String> words: child){
                for(String word: words){
                    if(word.contains(nonterminal)){
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found && !inaccessible.contains(nonterminal)){
                inaccessible.add(nonterminal);
            }
        }
        //daca sunt parinti inaccesubuk
        if (inaccessible.size() != 0){
            nonterminals.removeAll(inaccessible);
            System.out.println("Inaccessible non-terminals are: ");
            for (String s: inaccessible){
                System.out.print(s + " ");
            }
            System.out.println();
            int i = 0;
            int deleted = 0;
            //sterge copiii inaccesibili
            for(String par: parent){
                if(inaccessible.contains(par)){
                    child.remove(i - deleted);
                    deleted += 1;
                }
                i += 1;
            }
            //sterge parintii inaccesibili din parent
            parent.removeAll(inaccessible);
            print(child, parent);
        }
        else {
            System.out.println("No inaccessible non-terminals");
        }
}

private static void removelength2(List<String> parent, List<List<String>> child, List<String> nonterminals) {
    List<String> r = new ArrayList<>(parent);
    for (String par : r) {
        List<String> new_r = new ArrayList<>();
        for (String rule : child.get(parent.indexOf(par))) {
            if (rule.length() > 2) {
                while (rule.length() > 2) {
                    String w = rule.substring(0, 2);
                    rule = rule.substring(2);
                    List<String> list = new ArrayList<>();
                    list.add(w);
                    //metoda get free returneaza o litera care nu se foloseste in parents
                    String w_ = get_free(parent, child, nonterminals);
                    //adaugam litera generata in nonterminals si in parinti
                    nonterminals.add(w_);
                    parent.add(w_);
                    //in child adaugam substringul
                    child.add(list);
                    rule = w_ + rule;
                }
                new_r.add(rule);
                }
                else{
                    new_r.add(rule);
                }
            }
        child.get(par.indexOf(par)).clear();
        child.get(par.indexOf(par)).addAll(new_r);
        }
    System.out.println("Removing productions with length more than 2");
    print(child, parent);
    }

    private static void addterminals(List<String> parent, List<List<String>> child, List<String> terminals, List<String> nonterminals){
        for(String par: parent){
            List<String> list = new ArrayList<>();
            for (String rule: parent){
                StringBuilder new_word = new StringBuilder("");
                if(rule.length() > 1){
                    for (char c: rule.toCharArray()){
                        if(!String.valueOf(c).toUpperCase().equals(String.valueOf(c))){
                            new_word.append(String.valueOf(c).toUpperCase() + "1");
                        }
                        else{
                            new_word.append(c);
                        }
                    }
                    list.add(String.valueOf(new_word));
                }
                else list.add(rule);
            }
            child.get(par.indexOf(par)).clear();
            child.get(par.indexOf(par)).addAll(list);
        }
        for (String terminal: terminals){
            String non_term = terminal.toUpperCase() + "1";
       //     if(!nonterminals.contains(non_term)){

            }

        }


    private static String get_free(List<String> parent, List<List<String>> child, List<String> nonterminals){
        for (char c = 'A'; c < 'Z'; c++){
            if (!nonterminals.contains(String.valueOf(c))){
                return String.valueOf(c);
            }
        }
        return "A";
    }
static void print(List<List<String>> child, List<String> parent) {
    for (int i = 0; i < child.size(); i++) {
            System.out.print(parent.get(i) + " -> ");
            for (String word : child.get(i)) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }
}


