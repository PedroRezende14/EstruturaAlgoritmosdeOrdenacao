package Trabalho;

import java.util.Random;

public class Crescente {

    public static void main(String[] args) {
        int[] sizes = {100000, 1000000};
        Random rand = new Random();
        
        for (int size : sizes) {
            int[] array = rand.ints(size, 1, 1000001).toArray();

            System.out.println("Crescente Tamanho : " + size);

            int[] arrayCopy = array.clone();
            SortResult bubbleSortResult = bubbleSort(arrayCopy);
            System.out.println("Bubble Sort - Trocas: " + bubbleSortResult.swaps + ", Comparacoes: " + bubbleSortResult.comparisons + ", Tempo: " + bubbleSortResult.time + " ms (" + bubbleSortResult.getTimeInSeconds() + " s)");

            arrayCopy = array.clone();
            SortResult insertionSortResult = insertionSort(arrayCopy);
            System.out.println("Insertion Sort - Trocas: " + insertionSortResult.swaps + ", Comparacoes: " + insertionSortResult.comparisons + ", Tempo: " + insertionSortResult.time + " ms (" + insertionSortResult.getTimeInSeconds() + " s)");

            arrayCopy = array.clone();
            SortResult selectionSortResult = selectionSort(arrayCopy);
            System.out.println("Selection Sort - Trocas: " + selectionSortResult.swaps + ", Comparacoes: " + selectionSortResult.comparisons + ", Tempo: " + selectionSortResult.time + " ms (" + selectionSortResult.getTimeInSeconds() + " s)");

            arrayCopy = array.clone();
            SortResult mergeSortResult = mergeSort(arrayCopy);
            System.out.println("Merge Sort - Trocas: " + mergeSortResult.swaps + ", Comparacoes: " + mergeSortResult.comparisons + ", Tempo: " + mergeSortResult.time + " ms (" + mergeSortResult.getTimeInSeconds() + " s)");

            arrayCopy = array.clone();
            SortResult quickSortResult = quickSort(arrayCopy);
            System.out.println("Quick Sort - Trocas: " + quickSortResult.swaps + ", Comparacoes: " + quickSortResult.comparisons + ", Tempo: " + quickSortResult.time + " ms (" + quickSortResult.getTimeInSeconds() + " s)");

        }
    }

    public static SortResult bubbleSort(int[] array) {
        long swaps = 0;
        long comparisons = 0;
        long startTime = System.currentTimeMillis();
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        long endTime = System.currentTimeMillis();
        return new SortResult(swaps, comparisons, endTime - startTime);
    }

    public static SortResult insertionSort(int[] array) {
        long swaps = 0;
        long comparisons = 0;
        long startTime = System.currentTimeMillis();
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                comparisons++;
                array[j + 1] = array[j];
                j = j - 1;
                swaps++;
            }
            comparisons++;
            array[j + 1] = key;
        }
        long endTime = System.currentTimeMillis();
        return new SortResult(swaps, comparisons, endTime - startTime);
    }

    public static SortResult selectionSort(int[] array) {
        long swaps = 0;
        long comparisons = 0;
        long startTime = System.currentTimeMillis();
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
            if (minIdx != i) swaps++;
        }
        long endTime = System.currentTimeMillis();
        return new SortResult(swaps, comparisons, endTime - startTime);
    }

    public static SortResult mergeSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long[] result = mergeSortHelper(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();
        return new SortResult(result[0], result[1], endTime - startTime);
    }

    private static long[] mergeSortHelper(int[] array, int left, int right) {
        long swaps = 0;
        long comparisons = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            long[] leftResult = mergeSortHelper(array, left, mid);
            long[] rightResult = mergeSortHelper(array, mid + 1, right);
            long[] mergeResult = merge(array, left, mid, right);
            swaps = leftResult[0] + rightResult[0] + mergeResult[0];
            comparisons = leftResult[1] + rightResult[1] + mergeResult[1];
        }
        return new long[]{swaps, comparisons};
    }

    private static long[] merge(int[] array, int left, int mid, int right) {
        long swaps = 0;
        long comparisons = 0;
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; ++i)
            leftArray[i] = array[left + i];
        for (int j = 0; j < n2; ++j)
            rightArray[j] = array[mid + 1 + j];
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            comparisons++;
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
                swaps++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
        return new long[]{swaps, comparisons};
    }

    public static SortResult quickSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long[] result = quickSortHelper(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();
        return new SortResult(result[0], result[1], endTime - startTime);
    }

    private static long[] quickSortHelper(int[] array, int low, int high) {
        long swaps = 0;
        long comparisons = 0;
        if (low < high) {
            long[] partitionResult = partition(array, low, high);
            int pi = (int) partitionResult[0];
            swaps += partitionResult[1];
            comparisons += partitionResult[2];
            long[] leftResult = quickSortHelper(array, low, pi - 1);
            long[] rightResult = quickSortHelper(array, pi + 1, high);
            swaps += leftResult[0] + rightResult[0];
            comparisons += leftResult[1] + rightResult[1];
        }
        return new long[]{swaps, comparisons};
    }

    private static long[] partition(int[] array, int low, int high) {
        long swaps = 0;
        long comparisons = 0;
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            comparisons++;
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                swaps++;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        swaps++;
        return new long[]{i + 1, swaps, comparisons};
    }
}
