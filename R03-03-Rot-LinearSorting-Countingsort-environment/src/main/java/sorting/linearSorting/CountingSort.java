package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		int maior = 0;
		for (int i=leftIndex; i<=rightIndex; i++){
			maior = Math.max(maior, array[i]);
		}
		
		int counter[] = new int[maior+1];
		for (int i=leftIndex; i<=rightIndex; i++){
			counter[array[i]]++;
		}
		
		int pos = leftIndex; 
		for (int i=0; i<counter.length; i++){
			for (int j=0; j<counter[i]; j++){
				array[pos++] = i;
			}
		}		
	}

}
