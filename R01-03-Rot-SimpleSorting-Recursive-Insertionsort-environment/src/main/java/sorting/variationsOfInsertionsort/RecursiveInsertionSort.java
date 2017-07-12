package sorting.variationsOfInsertionsort;

import sorting.AbstractSorting;
import util.Util;

public class RecursiveInsertionSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * Implementacaoo RECURSIVA do insertion sort. Para isso, tente definir o 
	 * caso base do algoritmo e depois o caso recursivo, que reduz o problema 
	 * para uma entrada menor em uma chamada recursiva. Seu algoritmo deve 
	 * ter complexidade quadratica O(n^2).
	 * 
	 * Restrições:
	 *  - voce nao pode utilizar arry auxiliar
	 *  - voce pode utilizar variaveis temporarias
	 *  - voce nao pode declarar novos atributos na classe
	 *  - para as trocas no array, utilize o metodo Util.swap
	 */
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex){
			sort(array, leftIndex, rightIndex-1);
			
			while (rightIndex > leftIndex && array[rightIndex-1].compareTo(array[rightIndex]) > 0){
				Util.swap(array, rightIndex, rightIndex-1);
				rightIndex--;
			}
		}
	}

}
