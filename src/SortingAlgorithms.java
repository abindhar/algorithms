import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        int[] helper = new int[nums.length]; //Extra space
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

    public static int[] heapSort(int[] nums){
        /* Simple version of Heapsort
        *  Algo: Build a max heap out of nums, by inserting one item at a time O(nlogn) [This is not heapify]
        *  Delete Max: Pop max, by swapping it with last item, and siftDown from 0 to n-1, to maintain heap property.
        *  Do above while size of heap>1
        * */
        int n = nums.length;
        // Build out a max heap from nums inefficiently using n-1 inserts into heap using siftUp Time:O(nlogn)
        // nums[0] already satisfies heap property
        for (int i=1; i<n; i++){
            siftUp(nums, i);
        }
        // Now nums looks like a max heap
        // Pop max item at index=0, by swapping with end index, then siftDown(index=0) Time:O(nlogn)
        for (int i=0; i<n; i++){
            swap(nums, 0, n-i-1); // Move max to end
            siftDown(nums, n-i-1); // Ensure nums[:n-1] is a valid max heap by siftDown()
        }
        return nums;
    }
    public static void siftUp(int[] nums, int index){
        // Element at index = index needs to be sifted up (max heap version) to ensure nums[:index+1]
        // all follow max heap property
        // Time: O(logn)
        while (nums[getParentIndex(index)]<nums[index]){
            swap(nums, index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }
    public static void siftDown(int[] nums, int endIndex){
        // Place root at its correct position
        // Pick larger of two children and swap with root if child>root
        int index = 0;
        while (getLeftChildIndex(index)<endIndex) {
            // Only if left child exists can right child exist too
            int smallerChildIndex = getLeftChildIndex(index);
            if (getRightChildIndex(index)<endIndex &&
                    nums[getRightChildIndex(index)]>nums[getLeftChildIndex(index)]) {
                smallerChildIndex = getRightChildIndex(index);
            }
            if (nums[index]>=nums[smallerChildIndex]) {
                // If heap property is satisfied break out no need of additional swaps
                break;
            }
            swap(nums, index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }
    public static int getLeftChildIndex(int index) {return 2*index+1;}
    public static int getRightChildIndex(int index) {return 2*index+2;}
    public static int getParentIndex(int index) { return (index-1)/2;}
    public static void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static int[] heapSortEfficient(int[] nums){
        /* 1. Build a max heap out of nums, using heapify procedure in O(n) time
        *  2. While heap size>1: Swap root with last (max elem reaches end)
        *     then bubbleDown/siftDown this new root in heap of size=size-1
        */
        int n = nums.length;
        heapify(nums); // O(n) time buildHeap method :)
        // Now nums looks like a max heap
        // Pop max item at index=0, by swapping with end index, then siftDown(index=0) Time:O(nlogn)
        for (int i=0; i<n; i++){
            swap(nums, 0, n-i-1); // Move max to end
            siftDown(nums, n-i-1); // Ensure nums[:n-1] is a valid max heap by siftDown()
        }
        return nums;
    }
    public static void heapify(int[] nums){
        /* Converts an unsorted input array nums into a max heap in O(n) time using siftDown()
        *  Algo: Start from idx=n down to 1. Check if subtree rooted idx follows heap property else siftDown()
        *  Input is an unsorted array nums [4,2,6,1,10]
        *        4
        *      /  \
        *     2    6
        *    / \
        *   1   10
        *   Start checking from n down to 0 in array ie right to left bottom to top in tree
        *   Now leaf elements, 10, 1, 6 satisfy heap property, since they don't have any children
        *   When we come to element 2, we find it doesn't follow max heap property
        *   Pick greater of children and swap => swap(2,10)
        *   Now subtree rooted at 4 => Heap property not followed (left child is 10) => swap(4, 10)
        *   Now weirdly the whole array/tree follows max heap property, we did this with few swaps
        *   Compare this to simple method of add() on all elems from index 1 to n into the heap.
        * */
        int n = nums.length;
        // Following loop examines item at index i, and makes sure it is in correct place looking down
        int index;
        for (int i=n-1; i>=0; i--) {
            index = i; // Don't use i, its going to be modified in nested while loop
            // Modified siftDown for Heapify()
            while (getLeftChildIndex(index)<n){
                // At least left child exists
                int greaterChildIndex = getLeftChildIndex(index); // Pick left as a greater child first
                if (getRightChildIndex(index)<n &&
                        nums[getRightChildIndex(index)]>nums[greaterChildIndex])
                    // If right child exists and is larger reset greater childindex
                    greaterChildIndex = getRightChildIndex(index);
                if (nums[index]>nums[greaterChildIndex])
                    // This means current element under consideration is > its greater child
                    // Heap property satisfied
                    break;
                swap(nums, index, greaterChildIndex);
                index = greaterChildIndex;
            }
        }
    }

    // Linear Runtime Algorithms
    // Non comparison sorting

    public static int[] simpleCouting(int[] nums){
        /* This is a simple implementation of the basic idea of counting sort
        *  This is not counting sort though
        *  Unlike counting sort this method isn't stable sorting
        *  Time: O( n + (max-min)): Find min&max (n), Fill up freqCount arr (n), Check every index
        *  in freqCount arr and print that num, freq times (max-min)
        *  Space: O(max-min+1)
        *  If array is small but range of nums is too broad [1,2,3,1000000], this algo is bad
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
    public static int[] countingSort(int[] nums){
        /* Counting Sort Algorithm
        *  Useful when nums contains integers
        *  Let k be the range of the numbers in nums, k is number of unique values
        *  to be counted
        *  Time: O(n+k) Best, Worst, Average
        *  Space: O(n)
        *  Example:
        *  nums = [3,2,6,5,3,2,6,6,1] min=1 max=6 k=6-1+1=6 freq of min is at freq[0]
        *  freq = [1,2,2,0,1,3] cumFreq = [1,3,5,5,6,9]
        *  Start with num[0] => 3, 3 is mapped to 3-min => idx=2 cumFreq = 5, place it at idx=5-1 in ans
        *  ans = [0,0,0,0,3,0,0,0,0]
        *  Dec freq and cumFreq and continue until freq=0 ...
        *  ans = [0,2,2,3,3,0,0,0,0]
        * */
        int min, max, k, n = nums.length;
        // Get the range
        min = nums[0];
        max = nums[0];
        // Get min and max
        for (int i=1; i<n; i++) {
            if (nums[i]<min) min = nums[i];
            else if (nums[i]>max) max = nums[i];
        }
        // Freq Array
        k = max-min+1; // Range of the unique numbers
        int[] count = new int[k]; // If the range is too large this blows up
        // Fill count array
        // Freq of min elem is mapped to count[0]
        // Freq of max elem is mapped to count[k-1]
        for (int i=0; i<n; i++){
            count[nums[i]-min]++;
        }
        // Take a running sum
        for (int i=1; i<k; i++){
            count[i] += count[i-1];
        }
        // count array is now special
        // Suppose count[2]=3 it means count of numbers <= 2 present in nums is 3
        // Which means 2's last position is supposed to be at index 3-1 in final ans
        int[] ans = new int[n];
        for (int j = n-1; j>=0; j--){
            // Iterate in reverse order to maintain stable order
            // x's freq is freq[x-min] and last index cumFreq[x-min]-1
            // Place current element at the last index it can be in
            int x = nums[j];
            ans[count[x-min]-1] = x;
            count[x-min]--;
        }
        return ans;
    }

    // Radix Sort
    public static int[] radixSort(int[] nums){
        /* Sort by least significant digit, then next most and so on
        *  Use counting sort to sort each time with k = 10 (digits are from 0 - 9)
        *  Counting sort must be stable, otherwise final order would be wrong
        *  Time: O( d (n+k)) d = number of digits in largest number in array
        *  O(n+k) is the runtime for counting sort which is called d times
        *  k = range of digits for decimal numbers k = 10
        * */
        int n = nums.length;
        int max = getMax(nums, n), place = 1;
        while (max>0) {
            radixCountingSubroutine(nums, place);
            max/=10;
            place*=10;
            // System.out.println(Arrays.toString(nums));
        }
        return nums;
    }
    public static int getMax(int[] nums, int n){
        // Return maximum number in nums till n
        int max = nums[0];
        for (int i=1; i<n; i++) max = Math.max(max, nums[i]);
        return max;
    }
    public static void radixCountingSubroutine(int[] nums, int place){
        /* Modified counting sort subroutine for radix sort
        *  Main trick is filtering current place value under consideration
        *  (nums[i]/place) % 10
        *  eg (123/1)%10 = 3, (123/10)%10 = 2, (123/100)%10 = 1
        */
        int n = nums.length, digit;
        int[] output = new int[n];
        int[] count = new int[10]; // range here is 10
        // Populate the count/frequency
        for (int x : nums){
            //Filter the current units/tens... place digit
            digit = (x/place) % 10;
            count[digit]++;
        }
        // Get running sum of count
        for (int i=1; i<10; i++) count[i]+=count[i-1];
        // Populate the output array
        // Traverse original array in reverse
        for (int i=n-1; i>=0; i--) {
            digit = (nums[i]/place)%10;
            output[count[digit]-1] = nums[i];
            count[digit]--;
        }
        // Copy output array into original array
        for (int i=0; i<n; i++) nums[i]=output[i];
    }
    public static void main(String[] args){
        int[] nums = {7,1,9,2,11,13,6,7,3,4,0,-1};
        System.out.println("Original Array:\n" + Arrays.toString(nums));
//        System.out.println(Arrays.toString(bubbleSort(nums.clone())));
//        System.out.println(Arrays.toString(selectionSort(nums.clone())));
//        System.out.println(Arrays.toString(insertionSort(nums.clone())));
        System.out.println(Arrays.toString(heapSort(nums.clone())));
        System.out.println(Arrays.toString(heapSortEfficient(nums.clone())));
//        System.out.println(Arrays.toString(mergeSort(nums.clone())));
//        System.out.println(Arrays.toString(quickSort(nums.clone())));
//        System.out.println("Simple Counting: \n" + Arrays.toString(simpleCouting(nums.clone())));
//        nums = new int[]{-2,5,4,3,3,2,5,4,3,-2,-1,-1,-2,4,5,5,5,5,5,-2,-1};
//        nums = new int[]{-214748499, 214748389}; // Bad case for counting sort
//        System.out.println("Counting Sort: \n" + Arrays.toString(countingSort(nums.clone())));
//        int[] nums2 = new int[]{170, 45, 75, 90, 802, 24, 2, 66};
//        System.out.println("Radix Sort: \n" + Arrays.toString(radixSort(nums2.clone())));
    }
}

