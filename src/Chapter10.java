import java.util.*;

public class Chapter10 {
    /*
    Chapter 10: Sorting and Searching
     */

    public void sortedMerge(int[] A, int[] B){
        /*
        Merge sorted array B into sorted array A, A has buffer space at the end
        Two pointers, Aptr Bptr track last elems of A & B, compare and write larger to A[idx]
         */
        int n = A.length, m = B.length;
        int Aptr = n-m-1, Bptr = m-1, idx = n-1;
        while (Aptr>=0 && Bptr>=0){
            if (A[Aptr]>B[Bptr]){
                A[idx] = A[Aptr];
                Aptr--;
            } else {
                A[idx] = B[Bptr];
                Bptr--;
            }
            idx--;
        }
        // If Aptr>0 some elems of A weren't compared as they are smaller than all elems of B
        // they are already at the right position.
        while (Bptr>=0){
            A[idx] = B[Bptr];
            idx--;
            Bptr--;
        }
    }

    public String[] groupAnagrams(String[] names) {
        /*
        Sort the input array so that all anagrams are next to each other
        Just sort the array using custom comparator
        *** WRONG SOLUTION ***
        */
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return names;
    }

    public String[] groupAnagrams2(String[] names) {
        /*
        Use sorted version of individual elems of names, as the key for comparison, then anagrams
        would evaluate to equal eg
        anagram => sort => aaagmnr
        nagaram => sort => aaagmnr
        Time: Let's say String array has N elements, and longest string is K characters long
        Space: (N log N) * (K log K)
         */
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return sortStringHelper(o1).compareTo(sortStringHelper(o2));
            }
        });
        return names;
    }
    public String sortStringHelper(String s){
        /* Helper to sort a string
        Strings are immutable, there can't be an in-place sorting of strings
        Additionally we will trim whitespaces using String trim() method
        "  ram " => sorted => "    ram" => trim => "ram"
         */
        char[] c = s.toCharArray();
        Arrays.sort(c);
        String ans = new String(c);
        return ans.trim();
    }

    public String[] groupAnagrams3(String[] names){
        /*
        More efficient solution:
        Sort each string, use sorted string as HashMap key, all anagrams will correspond to same key
        Save strings in ArrayList mapped to that key
         */
        HashMap<String, ArrayList<String>> dict = new HashMap<>();
        for (String name : names){
            dict.getOrDefault(sortStringHelper(name), new ArrayList<String>()).add(name);
        }
        int i = 0;
        for (String s : dict.keySet()){
            for( String x : dict.get(s)){
                names[i] = x;
                i++;
            }
        }
        return names;
    }
    public String[] groupAnagrams4(String[] names){
        /*
        Avoid any sorting. Write out a freq representation of each string in terms of an int[26]
        "aba" => [2, 1, 0 ......]
        Anagrams would have same representation
         */
        for (String name : names){
            int[] map = new int[26];
        }
        return names;
    }
    public static void main(String[] args){
        /*
        Driver Code
         */
        Chapter10 obj = new Chapter10();
        int[] A = {-1, 1, 7, 0,0};
        int[] B = {4, 6};
        obj.sortedMerge(A, B);
        //System.out.println("Result:\n" + Arrays.toString(A));

        String[] names = {"zscaler", "abba", "bbaa", "roland reagan", "a darn long era", "anagram", "nag a ram"};
        //System.out.println("Result: \n" + Arrays.toString(obj.groupAnagrams(names))); //wrong, this is simple lexical sorting
        System.out.println(Arrays.toString(obj.groupAnagrams2(names.clone())));
        System.out.println(Arrays.toString(obj.groupAnagrams3(names.clone())));
    }
}
