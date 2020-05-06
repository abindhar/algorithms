import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {
    public static int[] bubbleSort(int[] nums){
        for (int i=nums.length; i>=0; i--){
            for (int j=1; j<i; j++){
                if (nums[j-1]>nums[j]){
                    int tmp = nums[j-1];
                    nums[j-1] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        return nums;
    }
    public static int[] selectionSort(int[] nums){
        for (int i=0; i<nums.length; i++){
            int minIdx = i;
            for (int j=i; j<nums.length; j++){
                // Find minimum element from index = i to last
                if (nums[j]<nums[minIdx]) {
                    minIdx = j;
                }
            }
            // Swap elem at index i with minimum
            int tmp = nums[minIdx];
            nums[minIdx] = nums[i];
            nums[i] = tmp;
        }
        return nums;
    }
    public static int[] insertionSort(int[] nums){
        /* Alog: nums[i-1] is already sorted, now insert nums[i] into correct place
        Store current elem at i in variable key. Now initialize j=i-1
        Move all elements > target one place to right. Then place key at j+1
        * */
        int key, n = nums.length;
        // Insertion sort loop runs from second element
        for (int i=1; i<n; i++){
            // Store current elem at correct pos in sorted nums[:i]
            key = nums[i];
            // Move elements of arr[0..i-1], that are greater than key
            // to one position ahead of their current position
            int j = i-1;
            while (j>=0 && nums[j]>key) {
                nums[j+1] = nums[j];
                j--;
            }
            // Now j+1 is the index where key should be inserted
            nums[j+1] = key;
            // System.out.println(Arrays.toString(nums));
        }
        return nums;
    }
    public static int[] mergeSort(int[] nums){
        int[] helper = new int[nums.length];
        int lo = 0, hi = nums.length-1;
        mergeSortHelper(nums, helper, lo, hi);
        return nums;
    }
    public static void mergeSortHelper(int[] nums, int[] helper, int lo, int hi) {
        if (lo<hi){
            int mid = lo + (hi-lo)/2;
            mergeSortHelper(nums, helper, lo, mid);
            mergeSortHelper(nums, helper, mid+1, hi);
            //Now lo to mid is sorted, so is mid+1 to hi
            mergeSorted(nums, helper, lo, mid, hi);
        }
    }
    public static void mergeSorted(int[] nums, int[] helper, int lo, int mid, int hi){
        // Merges sorted parts of nums array from lo to mid and mid+1 to hi
        //Copy elements of nums to helper array
        for (int i=lo; i<=hi; i++)
            helper[i] = nums[i];
        int l = lo, r = mid+1, idx = lo;
        while (l<=mid && r<=hi){
            if (helper[l]<=helper[r]){
                nums[idx] = helper[l];
                l++;
            }else{
                nums[idx] = helper[r];
                r++;
            }
            idx++;
        }
        // if l>mid then right side elems are already in correct place
        for (int i=0; i<mid-l+1; i++){
            nums[idx+i] = helper[l+i];
        }
    }
    public static int[] quickSort(int[] nums){
        quickSortHelper(nums, 0, nums.length-1);
        return nums;
    }
    public static void quickSortHelper(int[] nums, int lo, int hi){
        if (lo>=hi){
            return;
        }
        Random rand = new Random();
        //Choose a pivot element
        int pivot  = nums[lo + rand.nextInt(hi+1-lo)];
        int idx = partition(nums, pivot, lo, hi);
        quickSortHelper(nums, lo, idx-1);
        quickSortHelper(nums, idx, hi);
    }
    public static int partition(int[] nums, int pivot, int lo, int hi){
        //Find first elem > pivot from left and first elem < pivot from right and swap
        while (lo<hi){
            while (nums[lo]<pivot){
                lo++;
            }
            while (nums[hi]>pivot){
                hi--;
            }
            if (lo<=hi){
                //Swap
                int tmp = nums[lo];
                nums[lo] = nums[hi];
                nums[hi] = tmp;
                lo++;
                hi--;
            }
        }
        return lo;
    }

    // Non comparison based sorting algorithms

    public static int[] simpleCouting(int[] nums){
        /* This is a simple implementation of the basic idea of counting sort
        *  Unlike counting sort this method isn't stable sorting
        * */
        int max = nums[0], min = nums[0];
        for (int x : nums) {
            if (x<min) min = x;
            else if (x>max) max = x;
        }
        int range = max-min+1;
        // Freq Counter of size to accomodate min to max range
        int[] freqCounter = new int[range];
        // Store count of x at x-min index
        for (int x : nums) {
            freqCounter[x-min]++;
        }
        int ptr = 0;
        for (int i=0; i<range; i++){
            if (!(freqCounter[i]==0)){
                for (int j=0; j<freqCounter[i]; j++){
                    nums[ptr] = min+i;
                    ptr++;
                }
            }
        }
        return nums;
    }
    public static void main(String[] args){
        // Driver code
        int[] nums = {7,1,9,2,11,13,6,7,3,4,0,-1};
        System.out.println("Original Array:\n" + Arrays.toString(nums));
        // Sorting algorithms
//        System.out.println(Arrays.toString(bubbleSort(nums.clone())));
//        System.out.println(Arrays.toString(selectionSort(nums.clone())));
//        System.out.println(Arrays.toString(insertionSort(nums.clone())));
//        System.out.println(Arrays.toString(mergeSort(nums.clone())));
//        System.out.println(Arrays.toString(quickSort(nums.clone())));
        System.out.println(Arrays.toString(simpleCouting(nums.clone())));
    }
}

