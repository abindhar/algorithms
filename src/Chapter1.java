import java.nio.file.FileSystemNotFoundException;
import java.util.Arrays;
import java.util.*;

public class Chapter1 {
    /* Chapter 1 :  Arrays and Strings
     */

    public static boolean isUnique(String str){
        // O(n) Time O(n) Space
        boolean[] char_map = new boolean[26];
        for (int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            int offset = (int) c - (int) 'a';
            if (char_map[offset]==true){
                return false;
            }
            char_map[offset] = true;
        }
        return true;
    }
    public static boolean isUnique2(String str){
        // Optimal Solution: O(1) Space
        if (str.length()>26) {
            return false;
        }
        int checker = 0; // Integer as a hashmap
        for (int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            int offset = (int) c - (int) 'a';
            if ((checker & (1<<offset)) == 1){
                // Already seen
                return false;
            }
            checker |= (1 << offset);
        }
        return true;
    }

    //Q2
    public static String sort(String s){
        // Returns sorted string
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    public static boolean isPermutation(String s, String t){
        // Time: O(nlogn) Space: O(1)
        String s_sorted = sort(s);
        String t_sorted = sort(t);
        //System.out.println(s_sorted + t_sorted);
        return s_sorted.equals(t_sorted);
    }

    public static boolean isPermutation2(String s, String t){
        // O(n) Time and Space
        if (s.length()!=t.length()){
            return false;
        }
        int[] mymap = new int[26];
        for (int i=0; i<s.length(); i++){
            int offset = s.charAt(i) - 'a';
            mymap[offset] += 1;
        }
        //Loop over string t
        for (int i=0; i<t.length(); i++){
            int offset = t.charAt(i) - 'a';
            if (mymap[offset]==0){
                return false;
            }
            mymap[offset] -= 1;
        }
        return true;
    }

    // Q4
    public static boolean isPalindromePermutation(String str){
        int checker = 0;
        for (int i=0; i<str.length(); i++){
            int offset = str.charAt(i) - 'a';
            //System.out.println(str.charAt(i))
            checker ^= (1 << offset); // XOR
            //System.out.println(Integer.toBinaryString(checker));
        }
        //System.out.println(Integer.toBinaryString(checker));

        //Now only one bit should be set in checker, representing the possible char with odd freq

        int set_bits = 0;
        //Count number of set bits
        while (checker>0){
            set_bits += (checker & 1);
            checker >>= 1;
            if (set_bits > 1){
                return false;
            }
        }
        return true;

        // x & (x-1) == 0 // for one bit set check
        //return (checker & (checker-1)) == 0;
    }


    //Q5: One Away
    public static boolean oneReplacement(String s, String t){
        // Both strings are of same length, check only one char difference
        // eg s: pale t: sale ==> return true
        if (s.length()!=t.length()){
            return false;
        }
        boolean flag = false;
        int ptr1 = 0;
        while (ptr1<s.length()){
            boolean diff = (s.charAt(ptr1)!=t.charAt(ptr1));
            if (diff && flag){
                return false;
            } else if (diff) {
                flag = true;
            }
            ptr1 += 1;
        }
        return true;
    }
    public static boolean oneAddition(String s, String t){
        // lens = lent+1
        // Jump over one char that is extra in s
        int ptr1 = 0, ptr2 = 0;
        boolean flag = false;
        while (ptr1 < s.length() && ptr2 < t.length()){
            if (s.charAt(ptr1)!=t.charAt(ptr2)){
                if (flag){
                    return false;
                }
                ptr1 += 1;
                flag = true;
            } else {
                ptr1 += 1;
                ptr2 += 1;
            }
        }
        return true;
    }
    public static boolean oneAway(String s, String t){
        // One Replacement
        // One Add/Remove
        int lens = s.length(), lent = t.length();
        if (lens==lent){
            return oneReplacement(s, t);
        } else if (lens == lent+1){
            return oneAddition(s, t);
        } else if (lent == lens+1){
            return oneAddition(t, s);
        }
        return false;
    }

    // Q6
    public static String compressString(String s){
        // Stack solution
        // aaabbbbbbccc ==> a3b6c3
        // Tricky to implement correctly

        StringBuilder sb = new StringBuilder();
        char last = s.charAt(0);
        int count = 1;
        for (int i=1; i<s.length(); i++){
            if (s.charAt(i) == last){
                count += 1;
            } else {
                sb.append(last);
                sb.append(count);
                last = s.charAt(i);
                count = 1;
            }
        }
        sb.append(last);
        sb.append(count);
        String ans = sb.toString();
        return ans.length()>s.length() ? s:ans;

    }

    public static void rotateMatrix(int[][] matrix){
        /*
        [[1,2,3],
         [4,5,6],
         [7,8,9]]
        */
        
    }

    // An aside, a simple lambda function demonstration
    public static void greet(Greeting greeter){
        greeter.perform();
    }

    public static void main(String args[]){
        //Addenda
        //Greet the user
        // Declare lambda function to pass to method
        Greeting greeter = () -> System.out.println("This is Chapter 1 of CTCI");
        greet(greeter);

        String test = "Abindu";
        for (char c: test.toCharArray()){
            System.out.println(c);
        }
        /*
        //Q1
        String test = "qwerty";
        System.out.println(isUnique2(test));
        test = "abcdefgha";
        System.out.println(isUnique2(test));

        //Q2
        String s = "qwerty", t = "ytrewq";
        System.out.println(isPermutation(s, t));
        System.out.println(isPermutation2(s, t));



        //Q4
        //System.out.println(isPalindromePermutation("racecar"));
        System.out.println(isPalindromePermutation("raceycar"));



        //Q5
        System.out.println(oneAway("pale", "sale"));
        System.out.println(oneAway("pale", "pal"));
        */

        //Q6
        System.out.println(compressString("aaabbbbccc"));
        System.out.println(compressString("abcdefg"));




    }
}

