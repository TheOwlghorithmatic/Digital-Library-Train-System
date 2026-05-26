package DataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Heap {
    private List<Integer> heap;
    boolean mx;
    // if you want a max heap
    // initialise with true in the constructor
    // initialise with false or keep as is to make it a min heap

    public Heap() {
        heap = new ArrayList<>();
        this.mx = false;
    }

    public Heap(boolean mx) {
        heap = new ArrayList<>();
        this.mx = mx;
    }

    private boolean compare(Integer a, Integer b) {
        if(mx) {
            return heap.get(a) > heap.get(b);
        }
        return heap.get(a) < heap.get(b);
    }

    private int parent(int i) { 
        return (i - 1) / 2;
    }
    private int leftChild(int i) { 
        return (2 * i) + 1; 
    }
    private int rightChild(int i) { 
        return (2 * i) + 2; 
    }

    private void swap(int i, int j) {
        Integer temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void heapifyUp(int i) {
        while (i != 0 && compare(i, parent(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && compare(left, largest)) {
            largest = left;
        }
        if (right < heap.size() && compare(right, largest)) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            heapifyDown(largest);
        }
    }

    public void insert(Integer node) {
        heap.add(node);
        heapifyUp(heap.size() - 1);
    }

    public Integer pop() throws NoSuchElementException {
        if (heap.isEmpty()) throw new NoSuchElementException();
        if (heap.size() == 1) return heap.remove(heap.size() - 1);

        Integer rootNode = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown(0);
        return rootNode;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
