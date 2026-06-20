package Problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Heap {
    private List<Pair> heap;
    boolean mx;
    // if you want a max heap initialise with true in the constructor
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
            return heap.get(a).b > heap.get(b).b;
        }
        return heap.get(a).b < heap.get(b).b;
    }

    private Integer parent(Integer i) { 
        return (i - 1) / 2;
    }
    private Integer leftChild(Integer i) { 
        return (2 * i) + 1; 
    }
    private Integer rightChild(Integer i) { 
        return (2 * i) + 2; 
    }

    private void swap(Integer i, Integer j) {
        Pair temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void heapifyUp(Integer i) {
        while (i != 0 && compare(i, parent(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(Integer i) {
        Integer largest = i;
        Integer left = leftChild(i);
        Integer right = rightChild(i);

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

    public void push(int node, int weight) {
        heap.add(new Pair(node, weight));
        heapifyUp(heap.size() - 1);
    }

    public Pair pop() throws NoSuchElementException {
        if (heap.isEmpty()) throw new NoSuchElementException();
        if (heap.size() == 1) return heap.remove(heap.size() - 1);

        Pair rootNode = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown(0);
        return rootNode;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
