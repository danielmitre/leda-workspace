package sorting.divideAndConquer.quicksort3;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que
 * funciona de forma ligeiramente diferente. Relembre que quando o pivô
 * escolhido divide o array aproximadamente na metade, o QuickSort tem um
 * desempenho perto do ótimo. Para aproximar a entrada do caso ótimo, diversas
 * abordagens podem ser utilizadas. Uma delas é usar a mediana de 3 para achar o
 * pivô. Essa técnica consiste no seguinte: 1. Comparar o elemento mais a
 * esquerda, o central e o mais a direita do intervalo. 2. Ordenar os elemento,
 * tal que: A[left] < A[center] < A[right]. 3. Adotar o A[center] como pivô. 4.
 * Colocar o pivô na penúltima posição A[right-1]. 5. Aplicar o particionamento
 * considerando o vetor menor, de A[left+1] até A[right-1]. 6. Aplicar o
 * algoritmo na metade a esquerda e na metade a direita do pivô.
 */
public class QuickSortMedianOfThree<T extends Comparable<T>> extends AbstractSorting<T> {

   public void sort(T[] array, int leftIndex, int rightIndex) {
      if (leftIndex < rightIndex) {
         int menor, maior, mediana;
         int midIndex = (leftIndex + rightIndex) / 2;
         
         medianOfThree(array, leftIndex, midIndex, rightIndex);
         int mid = particiona(array, leftIndex, rightIndex);
         sort(array, leftIndex, mid-1);
         sort(array, mid+1, rightIndex);
      }
   }

   private int particiona(T[] array, int leftIndex, int rightIndex) {
      T pivot = array[leftIndex];
      int i = leftIndex;

      int j = rightIndex;
      
      for (int j = leftIndex + 1; j <= rightIndex; j++) {
         if (array[j].compareTo(pivot) <= 0) {
            util.Util.swap(array, ++i, j);
         }
      }

      util.Util.swap(array, leftIndex, i);

      return i;
   }
   
   private void medianOfThree(T[] array, int leftIndex, int midIndex, int rightIndex){
	   if (array[rightIndex].compareTo(array[leftIndex]) < 0) {
		   Util.swap(array, leftIndex, rightIndex);
	   }
	   if (array[midIndex].compareTo(array[leftIndex]) < 0) {
		   Util.swap(array, midIndex, leftIndex);
	   }
	   if (array[rightIndex].compareTo(array[midIndex]) < 0) {
		   Util.swap(array, rightIndex, midIndex);
	   }
   }
}
