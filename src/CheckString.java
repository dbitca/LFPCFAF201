import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class CheckString {

    char c;

    CheckString(char[][] matrix, HashMap<Character, ArrayList<String>> productions){

        String str = "$ababfcdf$";
        StringBuffer strBuffer = new StringBuffer(str);
        System.out.println(strBuffer);

        for (int i = 1; i < strBuffer.length(); i++){
            strBuffer.insert(i, findRelation (matrix, strBuffer.charAt(i-1), strBuffer.charAt(i)));
            i++;
        }

        System.out.println(strBuffer);
        openBrackets(matrix, strBuffer, productions);
        System.out.println(strBuffer);

    }



    public void openBrackets(char[][] matrix, StringBuffer strBuffer, HashMap<Character, ArrayList<String>> productions){
        int startIndex = 0, stopIndex = 0;
        for (int i = 0; i < strBuffer.length(); i++)
            if (strBuffer.charAt(i) == '<') startIndex = i;
            else if (strBuffer.charAt(i) == '>'){
                stopIndex = i;
                break;
            }

        String aux = "";
        for (int i = startIndex+1; i < stopIndex; i++)
            if ((strBuffer.charAt(i) >= 'a' && strBuffer.charAt(i) <= 'z') || (strBuffer.charAt(i) >= 'A' && strBuffer.charAt(i) <= 'Z'))
                aux += Character.toString(strBuffer.charAt(i));

        replace(productions, strBuffer, aux, startIndex, stopIndex, matrix);

        System.out.println(startIndex + " " + stopIndex + " " + aux);

    }

    public char check(HashMap<Character, ArrayList<String>> productions, String aux){
        c = ' ';
        productions.forEach(
                (key, value) -> {
                    if (productions.get(key).contains(aux)) c = key;
                }
        );
        return  c;
    }

    public void replace (HashMap<Character, ArrayList<String>> productions, StringBuffer strBuffer,String aux, int startIndex, int stopIndex, char[][] matrix){
        char c = check(productions, aux);
        if (c != ' ') {
            String s = " " + c + " ";
            strBuffer.replace(startIndex, stopIndex+1, s);

            for (int i = 1; i < strBuffer.length()-1; i++){
                if (strBuffer.charAt(i) == ' ') strBuffer.replace(i,i+1,Character.toString(findRelation(matrix, strBuffer.charAt(i-1), strBuffer.charAt(i+1))));
            }
        }
        //openBrackets(matrix, strBuffer, productions);
    }

    char findRelation(char[][] matrix, char charRow, char charColumn){
        char c = ' ';
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                if (matrix[i][0] == charRow && matrix[0][j] == charColumn){
                    c = matrix[i][j];
                    break;
                }
        return c;
    }

}