import java.util.Arrays;

public class BinaryHeap<T extends Comparable<? super T>> {
    private T[] heap;
    private int size;

    public BinaryHeap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    public void insert(T value) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = value;
        percolateUp(size);
        size++;
    }

    public T deleteMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T min = heap[0];
        heap[0] = heap[--size];
        percolateDown(0);
        return min;
    }

    private void percolateUp(int index) {
        T value = heap[index];
        while (index > 0 && value.compareTo(heap[(index - 1) / 2]) < 0) {
            heap[index] = heap[(index - 1) / 2];
            index = (index - 1) / 2;
        }
        heap[index] = value;
    }

    private void percolateDown(int index) {
        T value = heap[index];
        int child;
        while (index * 2 + 1 < size) {
            child = index * 2 + 1;
            if (child != size - 1 && heap[child].compareTo(heap[child + 1]) > 0) {
                child++;
            }
            if (value.compareTo(heap[child]) > 0) {
                heap[index] = heap[child];
            } else {
                break;
            }
            index = child;
        }
        heap[index] = value;
    }

    public void buildHeapBottomUp(T[] array) {
        size = array.length;
        heap = Arrays.copyOf(array, size);
        for (int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    public void buildHeapTopDown(T[] array) {
        size = 0;
        heap = (T[]) new Comparable[array.length];
        for (T value : array) {
            insert(value);
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Create an array of 10 patients with random emergency levels
        Patient[] patients = new Patient[10];
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Ivy", "Jack"};
        Random random = new Random();

        for (int i = 0; i < patients.length; i++) {
            int emergencyLevel = random.nextInt(5) + 1; // Random emergency level between 1 and 5
            patients[i] = new Patient(names[i], emergencyLevel);
        }

        // (a) Print the array of patients
        System.out.println("Array of Patients:");
        for (Patient patient : patients) {
            System.out.println(patient);
        }

        // (b) Create a binary heap of these patients using enqueue
        BinaryHeap<Patient> heap = new BinaryHeap<>(patients.length);
        for (Patient patient : patients) {
            heap.insert(patient);
        }

        // (c) Heapsort the patients and print them
        Patient[] sortedPatients = new Patient[patients.length];
        int index = 0;
        while (heap.size > 0) {
            sortedPatients[index++] = heap.deleteMin();
        }

        System.out.println("\nSorted Patients:");
        for (Patient patient : sortedPatients) {
            System.out.println(patient);
        }
    }
}
public class Patient implements Comparable<Patient> {
    private String name;
    private int emergencyLevel;

    public Patient(String name, int emergencyLevel) {
        this.name = name;
        this.emergencyLevel = emergencyLevel;
    }

    public String getName() {
        return name;
    }

    public int getEmergencyLevel() {
        return emergencyLevel;
    }

    @Override
    public int compareTo(Patient other) {
        if (this.emergencyLevel != other.emergencyLevel) {
            return Integer.compare(this.emergencyLevel, other.emergencyLevel);
        } else {
            return this.name.compareTo(other.name);
        }
    }

    @Override
    public String toString() {
        return "Patient{name='" + name + "', emergencyLevel=" + emergencyLevel + "}";
    }
}

