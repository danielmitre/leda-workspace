package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex){
			int mid = (leftIndex + rightIndex)/2;
			
			sort(array, leftIndex, mid);
			sort(array, mid+1, rightIndex);
			merge(array, leftIndex, mid, rightIndex);
		}
	}
	
	private void merge(T[] array, int leftIndex, int midIndex, int rightIndex) {
		T[] left = Arrays.copyOfRange(array, leftIndex, midIndex+1);
		T[] right = Arrays.copyOfRange(array, midIndex+1, rightIndex+1);
		
		int pos = leftIndex;
		int i = 0;
		int j = 0;
		
		while (i<left.length && j<right.length){
			if (left[i].compareTo(right[j]) <  0){
				array[pos++] = left[i++];
			} else {
				array[pos++] = right[j++];
			}
		}
		
		for (; i < left.length; i++){
			array[pos++] = left[i];
		}
		
		for (; j < right.length; j++){
			array[pos++] = right[j];
		}
		
		//System.out.printf("%s + %s = ", Arrays.toString(left), Arrays.toString(right));
		//System.out.println(Arrays.toString(array));
	}
}
