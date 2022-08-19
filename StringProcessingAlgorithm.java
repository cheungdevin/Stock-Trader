import java.util.*;

public class StringProcessingAlgorithm {
    private static String originalText;
    private static Scanner reader=new Scanner(System.in); //static reader, no need to open reader in the methods of the class

    //  Option 1:   Split the string whenever there is a delimiter
    //              Output each substring in separate line
    //  Input:      Hello World!!
    //  Delimiter:  e
    //  Output:     H
    //              llo World!!
    public static void split() {
        System.out.print("Please input a delimiter: ");
        String target = "";
        target = reader.next();

        for(String s : originalText.split(target)) System.out.println(s);
    }

    // Option 2:    Remove the target character sequence from the original string
    //              if it doesn't exist in the string, output "target is not found"

    public static void removeSubstring() {
        System.out.print("Please input string to remove: ");
        String target = reader.next();

        System.out.println("String before removing \'" + target + "\': " + originalText);

        
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (char c : originalText.toCharArray()) {
            if(count != target.length() && c==target.charAt(count)){
                count++;
                continue;
            }
            sb.append(c);
        }
        if(sb.length()==originalText.length()){
            System.out.println("target is not found");
            return;
        }
        System.out.println("String after removing '" + target + "': " + sb.toString());
    }

    // Option 3: Shift characters to the right by the amount specified by shiftAmount
    //         e.g., Input string: "Hello World"
    //               shiftAmount: 3
    //               Result: "rldHello Wo"
    public static void shiftString() {
        System.out.print("Please input amount of shift: ");
        int shiftAmount = reader.nextInt();

        StringBuilder sb = new StringBuilder();
        for(int i=originalText.length()-shiftAmount;i<originalText.length();i++){
            sb.append(originalText.charAt(i));
        }
        for(int i=0;i<originalText.length()-shiftAmount;i++){
            sb.append(originalText.charAt(i));
        }
        System.out.println("After shifting \"" + originalText + "\" by " + shiftAmount + ": \"" + sb.toString());
    }

    // Option 4: Count number of vowels in String. (A, E, I, O, U, a, e, i, o, and u)
    public static void countVowels() {
        List<Character> vow = Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        int count = 0;
        for (int i = 0; i < originalText.length(); i++){
            if(vow.contains(originalText.charAt(i))) count++;
        }
        System.out.println("number of vowels in \"" + originalText +"\": " + count);
    }

    // Option 5: Rotate each characters forward by the amount specified by shiftAmount. Any numbers and special characters should be left the same.
    //         Also, The string should be converted into capital letters first
    //         e.g., Input string: "Hello World! 123"
    //               shiftAmount: 30
    //               Result: "LIPPS ASVPH! 123"
    public static void ceaserCipher() {
        System.out.print("Please input amount of shift: ");
        int shift = reader.nextInt();


        StringBuilder sb = new StringBuilder(originalText.toUpperCase());
        for (int i = 0; i < sb.length(); i++){
            if (sb.charAt(i) >= 'A' && sb.charAt(i) <= 'Z') {
                int c = sb.charAt(i);
                c += shift;
                while(c>90) c -= 26;
                sb.setCharAt(i, (char)c);
            }
        } 
        System.out.println(sb.toString());
    }

    // Option 6: First Unique Character in a String
    // find the first non-repeating character in it and print its index. If it does not exist, prints -1.
    // string should consists of ONLY lowercase English letters.
    // Input:   sdnvlbkrmtbollujsdjfjfppksravjkwwsimlmdtcmiilpjibjhcppluisqbqfwrjjlrapsmcwrsrnfrmtjrffpuuqwonq
    // Output:  50
    public static void uniqueChar(){
        //*Faster Array method:
        // int[] count = new int[26];
        // int n = originalText.length();
        // for (int i = 0; i < n; i++) {            
        //     int index = originalText.charAt(i) - 'a';
        //     count[index]++;
        // }
        
        // for (int i = 0; i < n; i++) {
        //     int index = originalText.charAt(i) - 'a';
        //     if (count[index] == 1) {
        //         System.out.println(i);
        //         return;
        //     }
                
        // }
        // System.out.println(-1);

        //*HashMap method:
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int n = originalText.length();
        for (int i = 0; i < n; i++) {
            char c = originalText.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        for (int i = 0; i < n; i++) {
            if (map.get(originalText.charAt(i)) == 1) {
                System.out.println(i);
                return;
            }
        }
        System.out.println(-1);
    }

    // Option 7: Compare two string, print True if they consists of the same char
    public static void sameChar(){
        System.out.print("Please input the second string you want to compare: ");
        String s2 = reader.nextLine();
        int len = s2.length();
        if(originalText.length()!=len){
            System.out.println(false);
            return;
        }
        int[] count = new int[26];
        for(int i=0;i<len;i++){
            count[originalText.charAt(i)-'a']++;
            count[s2.charAt(i)-'a']--;
        }
        for(int i=0;i<26;i++){
            if(count[i]!=0){    
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }


    // Option 8: Reverse String by modifying the input array in-place with O(1) extra memory.
    // Below sulution: Runtime: 1 ms, faster than 99.91% of Java online submissions on Leetcode
    public static void reverseString(){
        char[] s = originalText.toCharArray();
        for(int i=0;i<s.length/2;i++){
            char t = s[i];
            s[i] = s[s.length-1-i];
            s[s.length-1-i] = t;
        }
        System.out.println(s);
    }
    

    public static void main(String args[]){

        System.out.println("Welcome to the String Handling System!");
        System.out.print("Please input a string you want to process: ");

        Scanner reader = new Scanner(System.in); // Scanner is used for Java input
        originalText = reader.nextLine();

        String option = "";

        while (!option.equals("Q")) {
            System.out.println("=========== Options ============");
            System.out.println("1: Split string");
            System.out.println("2: Remove all substring");
            System.out.println("3: Shift string");
            System.out.println("4: Count vowels");
            System.out.println("5: Ceaser cipher");
            System.out.println("6: First unique char");
            System.out.println("7: Compare string");
            System.out.println("8: Reverse string");
            System.out.println("================================");
            System.out.println("Please choose an option (type in Q if you want to quit): ");
            option = reader.next();

            switch (option){
                case "1":
                    split();
                    break;
                case "2":
                    removeSubstring();
                    break;
                case "3":
                    shiftString();
                    break;
                case "4":
                    countVowels();
                    break;
                case "5":
                    ceaserCipher();
                    break;
                case "6":
                    uniqueChar();
                    break;
                case "7":
                    sameChar();
                    break;
                case "8":
                    reverseString();
                    break;
                default:
                    if (option.equals("Q"))
                        System.out.println("Goodbye!");
                    else
                        System.out.println("Invalid Option! Please try again: ");
                    break;
            }
        }
        reader.close();
    }
}