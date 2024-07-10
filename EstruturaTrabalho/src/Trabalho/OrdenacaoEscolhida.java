package Trabalho;

import java.util.Arrays;
import java.util.Random;

public class OrdenacaoEscolhida {

	public static SortResult countingSort(int[] array) {
	    long startTime = System.currentTimeMillis();
	    int max = getMax(array);
	    int min = getMin(array);
	    int range = max - min + 1;
	    int[] count = new int[range];
	    int[] output = new int[array.length];
	    long comparisons = 0;
	    int swaps =0 ;

	    // Contagem de comparações durante o preenchimento do array de contagem
	    for (int i = 0; i < array.length; i++) {
	        comparisons++;
	        swaps++;
	        count[array[i] - min]++;
	    }

	    // Contagem de comparações durante a soma cumulativa no array de contagem
	    for (int i = 1; i < count.length; i++) {
	        comparisons++;
	        swaps++;
	        count[i] += count[i - 1];
	    }

	    // Montagem do array de saída com base no array de contagem
	    for (int i = array.length - 1; i >= 0; i--) {
	    	swaps++;
	    	swaps++;
	        comparisons += 2; // Contagens ao acessar o array original e o array de contagem
	        output[count[array[i] - min] - 1] = array[i];
	        count[array[i] - min]--;
	    }

	

	    long endTime = System.currentTimeMillis();
	    return new SortResult(0, comparisons, endTime - startTime); // Zero trocas
	}


    public static SortResult heapSortDecrescente(int[] array) {
        long startTime = System.currentTimeMillis();
        int n = array.length;
        long swaps = 0;
        long comparisons = 0;

        for (int i = n / 2 - 1; i >= 0; i--) {
            long[] result = heapify(array, n, i);
            swaps += result[0];
            comparisons += result[1];
        }

       
        for (int i = n - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            swaps++;
            long[] result = heapify(array, i, 0);
            swaps += result[0];
            comparisons += result[1];
        }

        long endTime = System.currentTimeMillis();
        return new SortResult(swaps, comparisons, endTime - startTime);
    }

    private static long[] heapify(int[] array, int n, int i) {
        long swaps = 0;
        long comparisons = 0;
        int largest = i; 
        int left = 2 * i + 1; 
        int right = 2 * i + 2; 

        comparisons++;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        comparisons++;
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            swaps++;

            long[] result = heapify(array, n, largest);
            swaps += result[0];
            comparisons += result[1];
        }

        return new long[]{swaps, comparisons};
    }

    private static int getMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private static int getMin(int[] array) {
        int min = array[0];
        for (int value : array) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int[] sizes = {100000, 1000000};
        Random rand = new Random();

        for (int size : sizes) {
            int[] array = rand.ints(size, 1, 1000001).toArray();
            
            System.out.println("\nTamanho: " + size);

            int[] arrayCopy = array.clone();
            SortResult countingSortResult = countingSort(arrayCopy);
            System.out.println("Counting Sort - Aleatório - Trocas: " + countingSortResult.swaps + ", Comparações: " + countingSortResult.comparisons + ", Tempo: " + countingSortResult.time + " ms (" + countingSortResult.getTimeInSeconds() + " s)");

            System.out.println("\nOrdem crescente");
            arrayCopy = array.clone();
            Arrays.sort(arrayCopy);
            countingSortResult = countingSort(arrayCopy);
            System.out.println("Counting Sort - Crescente - Trocas: " + countingSortResult.swaps + ", Comparações: " + countingSortResult.comparisons + ", Tempo: " + countingSortResult.time + " ms (" + countingSortResult.getTimeInSeconds() + " s)");

            System.out.println("\nOrdem decrescente");
            arrayCopy = array.clone();
            Arrays.sort(arrayCopy);
            reverseArray(arrayCopy);
            SortResult heapSortResult = heapSortDecrescente(arrayCopy);
            System.out.println("Heap Sort - Decrescente - Trocas: " + heapSortResult.swaps + ", Comparações: " + heapSortResult.comparisons + ", Tempo: " + heapSortResult.time + " ms (" + heapSortResult.getTimeInSeconds() + " s)");

        }
    }

    private static void reverseArray(int[] array) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}