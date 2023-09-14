import java.util.ArrayList;
import java.util.Scanner;
enum TokenTypes {separatorSpace, // This program is capable to analyse this token types
    specialCharacter,
    operator,
    numberInt,
    reservedKeyword,
    identifier,
    erorrCharInStr, // this error happen when there is a special character in the identifier
    erorrNumInStr // this error happen when the first character of a identifier is number
}
public class Main {
    static char[] operatorsArray = {'^','*','-','+','/','!','='}; // This array can have more data
    static char[] specialCharactersArray = {'@','#','$','(',')' , '\''  ,'?','/','{','}', ',' ,';', '<' ,':', '>'}; // This array can have more data
    static char[] nums = {'0','1','2','3','4','5','6','7','8','9'}; // This array can have more data
    // you can add more reservedKeywords or remove some of them
    static String[] reservedKeywords = {"for","if"};
    static ArrayList<String> tokens = new ArrayList<String>(); // List of separated tokens itself
    static ArrayList<TokenTypes> tokenTypes = new ArrayList<TokenTypes>(); // List of type of the tokens that only can store the "TokenTypes" values
    static void appender (String token, TokenTypes tokenType){
        tokens.add(token);
        tokenTypes.add(tokenType);
    }
    static boolean cond (char[] condList , char value) {
        for (char index : condList) {
            if (value==index){
                return true;
            }
        }
        return false;
    }
    static void analyzer (String token){ // This function is capable of recognizing reservedKeywords, identifiers, numberInts
        boolean isReservedKeywords=false;
        if (token.length()>0) { // Skip the empty tokens
            if (!(cond (nums, token.charAt(0)))){ // ReservedKeywords, Identifiers or Numbers shouldn't begin with numbers
                // ReservedKeywords
                for ( String reservedKeyword : reservedKeywords ){
                    if ( token.equals(reservedKeyword)){
                        appender ( token, TokenTypes.reservedKeyword);
                        isReservedKeywords=true;

                    }
                }
                // Identifiers
                if (!isReservedKeywords){
                    boolean isInChar=true;
                    for (char SC :specialCharactersArray) {
                        if (cond(token.toCharArray(), SC)) { // Identifiers shouldn't have any specialCharacter
                            isInChar=false;
                        }
                    }

                    if (isInChar) {
                        appender ( token, TokenTypes.identifier);
                    }
                else if (isReservedKeywords){
                    appender ( token, TokenTypes.erorrCharInStr);
                }
            }}
            else {
                // NumberInt
                boolean isNum=true;
                for (char num :nums) {
                    if (cond(token.toCharArray(), num)) { // Number shouldn't have anything other than number
                        isNum=false;
                    }
                }
                if (isNum){
                    appender ( token, TokenTypes.numberInt);
                }
                else {
                    appender ( token, TokenTypes.erorrNumInStr);
                }
            }
        }
    }
    public static void main(String[] args) { // Main class
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        while (true){
            tokens.clear();
            tokenTypes.clear();

            System.out.print("Enter Script to tokenize : ");
            String script = scannerObj.nextLine();
            if (script.length()>=0) { // Skip the empty scripts
                int index = 1;
                String thisToken =String.valueOf(script.charAt(0)) ;
                while (script.length()>index) {
                    char theChar =script.charAt(index);
                    if (theChar==' ' ){ // Separator
                        analyzer(String.valueOf(thisToken));
                        appender(String.valueOf(theChar),TokenTypes.separatorSpace);
                        thisToken="";
                    }
                    else if (cond(specialCharactersArray,theChar)) { // SpecialCharacters
                        analyzer(String.valueOf(thisToken));
                        appender(String.valueOf(theChar),TokenTypes.specialCharacter);
                        thisToken="";
                    }
                    else if (cond(operatorsArray,theChar)){ //Operator
                        analyzer(String.valueOf(thisToken));
                        appender(String.valueOf(theChar),TokenTypes.operator);
                        thisToken="";
                    }
                    else if ((script.length()-1)==index) {
                        thisToken+=theChar;
                        analyzer(String.valueOf(thisToken));
                        thisToken="";
                    }
                    else {
                        thisToken+=theChar;
                    }
                    index++;
                }
            }
            System.out.println(tokens);
            System.out.println(tokenTypes);
        }
    }
}
