package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		for (int right = leftIndex+1; right <= rightIndex; right++){
			int i = right;
			while (i > leftIndex && array[i-1].compareTo(array[i]) > 0){
				Util.swap(array, i, i-1);
				i--;
			}
		}
	}
}
