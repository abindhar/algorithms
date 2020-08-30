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
        Use sorted individual elems of names, as the key for comparison, then anagrams would be next to each
        other
        anagram => sort => aaagmnr
        nagaram => sort => aaagmnr
        Time: Let's say String array has N elements, and longest string is K characters long
        Time: (N log N) * (K log K)
        Space: Depends on the sorting algorithm used
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
        /* More efficient solution
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
    public List<List<String>> groupAnagrams4(String[] names){
        /* Avoid any sorting.
        Write out a freq representation of each string in terms of an int[26]
        "aba" => [2, 1, 0 ......] => O(k) time O(26) space (here k is the length of the longest string
        Use String.valueOf() to convert char[] to String // Python=> ''.join()
        Time: Assume N is length of names array K is the length of longest string
        O( N * K ) To create freq map of string run through it => K do this N times
        Space: O ( N * K ) That is total amount of worst case chars to be stored (in hashmap)
         */
        HashMap<String, List<String>> map = new HashMap<>();
        for (String name : names){
            char[] cArr = new char[26];
            for (char c : name.toCharArray()) {
                if (c==' ') continue;
                cArr[c-'a']++;
            }
            String key = String.valueOf(cArr);
            if (!map.containsKey(key)) map.put(key, new ArrayList<>()); // Python => defaultdict(list)
            map.get(key).add(name);
        }
        return new ArrayList<>(map.values());
    }

    public int searchRotatedArray(int[] nums, int target) {
        /* Search in a rotated sorted array
        arr = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14}
        arr = {5,5,5,2,3,5,5}
        target = 5
         */
        int n = nums.length, lo = 0, hi = nums.length - 1;
        //return binarySearchHelper(nums, target, lo, hi);
        return binarySearchHelper2(nums, target, lo, hi);
    }

    public int binarySearchHelper(int[] nums, int target, int lo, int hi){
        /*
        Time: O(ln 2) Space: Recursion stack
        This implementation works for all cases except such:
        nums = [2,2,2,2,2,5,6,2]
        Here comparing mid with lo or hi doesn't reduce the search space by half
         */
        if (lo>hi)
            return -1;
        int mid = lo + (hi-lo)/2;
        if (nums[mid]==target)
            return mid;
        if (nums[mid]<nums[hi]){
            // mid .. hi is sorted
            if (nums[mid]<target && target<=nums[hi]) {
                return binarySearchHelper(nums, target, mid+1, hi);
            } else {
                return binarySearchHelper(nums, target, lo, mid-1);
            }
        } else if (nums[lo] < nums[mid]){
            // lo .. mid is sorted
            if (nums[lo]<=target && target<nums[mid]) {
                return binarySearchHelper(nums, target, lo, mid-1);
            } else {
                return binarySearchHelper(nums, target, mid+1, hi);
            }
        }
        return -1;
    }

    public int binarySearchHelper2(int[] nums, int target, int lo, int hi){
        /*
        Time:
        Have to handle a case like this below:
        nums = [2,2,2,2,2,5,6,2] comparing middle with lo or hi doesn't tell us where to look
         */
        if (lo>hi)
            return -1;
        int mid = lo + (hi-lo)/2;
        if (nums[mid]==target)
            return mid;
        if (nums[mid]<nums[hi]){
            // mid .. hi is sorted
            if (nums[mid]<target && target<=nums[hi]) {
                return binarySearchHelper2(nums, target, mid+1, hi);
            } else {
                return binarySearchHelper2(nums, target, lo, mid-1);
            }
        } else if (nums[lo] < nums[mid]){
            // lo .. mid is sorted
            if (nums[lo]<=target && target<nums[mid]) {
                return binarySearchHelper2(nums, target, lo, mid-1);
            } else {
                return binarySearchHelper2(nums, target, mid+1, hi);
            }
        } else{
            // Either nums[lo]==nums[mid] or nums[hi]==nums[mid] or both
            if (nums[lo]!=nums[mid]) {
                // If right half is 
                return binarySearchHelper2(nums, target, mid+1, hi);
            }
            int left_result = binarySearchHelper2(nums, target, lo, mid-1);
            if (left_result!=-1)
                return left_result;
            else
                return binarySearchHelper2(nums, target, mid+1, hi);
        }
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
        //System.out.println(Arrays.toString(names));
        //System.out.println(Arrays.toString(obj.groupAnagrams(names.clone()))); // Wrong, this is lexical sorting
        //System.out.println(Arrays.toString(obj.groupAnagrams2(names.clone())));
        //System.out.println(Arrays.toString(obj.groupAnagrams3(names.clone())));
        //System.out.println(obj.groupAnagrams4(names.clone()));

        int[] arr = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        int target = 16;
        System.out.println("Array: " + Arrays.toString(arr) + " Length: " + arr.length + " Target: " + target);
        System.out.println(obj.searchRotatedArray(arr, target));
        // Following case fails
        int[] arr2 = {2,2,2,2,2,5,6,2};
        target = 5;
        System.out.println("Array: " + Arrays.toString(arr2) + " Length: " + arr2.length + " Target: " + target);
        System.out.println(obj.searchRotatedArray(arr2, target)); // returns -1 even tho elem is present


    }
}
