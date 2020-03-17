import java.util.Arrays;

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
    public static int[] mergeSort(int[] nums){
        int[] helper = new int[nums.length];
        int lo = 0;
        int hi = nums.length -1;
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
            if (helper[l]<helper[r]){
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
            nums[idx+i] = nums[l+i];
        }
    }
    public static void quickSort(int[] nums){
        //
    }

    public static void main(String[] args){
        int[] nums = {7,1,9,2,11,13,6,7,3,4,0,-1};
        System.out.println(Arrays.toString(bubbleSort(nums.clone())));
        System.out.println(Arrays.toString(selectionSort(nums.clone())));
        System.out.println(Arrays.toString(mergeSort(nums.clone())));
        //System.out.println(Arrays.toString(quickSort(nums.clone())));
    }
}

