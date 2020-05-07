import java.util.Arrays;


public class Heap {
    /* Array Based implementation of a Binary MinHeap Data Structure
     *  MinHeap => A complete binary tree where root<=its children, and recursively true for subtrees
     *  LeftChildIndex=2*i+1 RightChildIndex=2*i+2 ParentIndex=(i-1)/2
     *  poll() => Pop and return top of heap
     *  add(item) => Add new item
     *  peek() => Return top of heap
     *  Credits: Adapted from Gayle Lackman McDowell
     * */
    private int size;
    private int capacity = 10;
    private int[] items = new int[capacity];
    // Helper Methods
    private int getLeftChildIndex(int parentIndex){ return 2*parentIndex + 1; }
    private int getRightChildIndex(int parentIndex){ return 2*parentIndex + 2; }
    private int getParentIndex(int childIndex){ return (childIndex-1)/2; }
    private boolean hasLeftChild(int parentIndex) { return getLeftChildIndex(parentIndex)<=size-1;}
    private boolean hasRightChild(int parentIndex) { return getRightChildIndex(parentIndex)<=size-1;}
    private boolean hasParent(int childIndex) { return childIndex>0;}
    private int getLeftChild(int parentIndex) {return items[getLeftChildIndex(parentIndex)];}
    private int getRightChild(int parentIndex) {return items[getRightChildIndex(parentIndex)];}
    private int getParent(int childIndex) {return items[getParentIndex(childIndex)];}
    private void ensureCapacity(){
        if (size==capacity) {
            Arrays.copyOf(items, capacity*2);
            capacity *= 2;
        }
    }
    int peek(){
        if (size==0) throw new IllegalStateException();
        return items[0];
    }
    void add(int item){
        // Make sure there's more space in heap
        ensureCapacity();
        // Add it to the end, then maintain heap property
        items[size] = item;
        size++;
        heapifyUp();
    }
    int poll(){
        // Extract Min operation
        if (size==0) throw new IllegalStateException();
        // Pop off item at index=0, replace it with last item in list
        int min = items[0];
        int last = items[size-1];
        items[0] = last;
        size--;
        heapifyDown();
        return min;
    }
    private void heapifyUp(){
        /* Pick the last element (ie the one just added) and sift it up
         * */
        int childIndex = size-1;
        while (hasParent(childIndex) &&
                (getParent(childIndex) > items[childIndex])) {
            swap(getParentIndex(childIndex), childIndex);
            childIndex = getParentIndex(childIndex); // Repeat for the parent
        }
    }
    private void heapifyDown(){
        // Pick the root element (after popping min last elem has been inserted here), and bubble it down
        int index = 0; // Index of item to be bubbled down
        // Bubble down
        while (hasLeftChild(index)){ // if there's no left child, then there's no right child either
            // In a heap the parent is always smaller than children so you don't compare parent to child
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && getRightChild(index)<items[smallerChildIndex]){
                smallerChildIndex = getRightChildIndex(index);
            }
            if (items[index] < items[smallerChildIndex]){
                // Heapify property is restored
                break;
            }
            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }
    private void swap(int i, int j){
        int tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }
}
