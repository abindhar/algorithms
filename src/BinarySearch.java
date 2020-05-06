public class BinarySearch {
    /* Basic Binary Search
    * Floor & Ceiling calculation
    * */
    public static int binarySearch(int[] arr, int target){
        // If found, return the element else return -1
        int lo = 0, hi = arr.length-1, mid;
        while (lo<=hi){
            mid = lo + (hi-lo)/2;
            if (arr[mid]==target) return target;
            else if (arr[mid]<target) lo = mid+1;
            else hi = mid-1;
        }
        return -1;
    }
    public static int binarySearchFloor(int[] arr, int target){
        if (target<arr[0]) return -1;
        int lo = 0, hi = arr.length-1, mid;
        while (lo<=hi){
            mid = lo + (hi-lo)/2;
            if (arr[mid]==target) return target;
            else if (arr[mid]<target) lo = mid+1;
            else hi = mid-1;
        }
        return arr[hi];
    }
    public static int binarySearchCeiling(int[] arr, int target){
        if (target > arr[arr.length-1]) return -1;
        int lo = 0, hi = arr.length-1, mid;
        while (lo<=hi){
            mid = lo + (hi-lo)/2;
            if (arr[mid]==target) return target;
            else if (arr[mid]<target) lo = mid+1;
            else hi = mid-1;
        }
        return arr[lo];
    }
    public static void main(String[] args){
        int[] arr = {1,2,4,7,8,10,12,14,999};
        int target = 57;
        System.out.println(binarySearch(arr, target));
        System.out.println(binarySearchFloor(arr, target));
        System.out.println(binarySearchCeiling(arr, target));
    }
}
