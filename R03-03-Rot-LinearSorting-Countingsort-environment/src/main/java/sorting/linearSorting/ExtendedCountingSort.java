package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		int maior = -999999999;
		int menor =  999999999;
		for (int i=leftIndex; i<=rightIndex; i++){
			maior = Math.max(maior, array[i]);
			menor = Math.min(menor,  array[i]);
		}
		
		if (maior < menor) return;	//array vazia
		
		int counter[] = new int[maior-menor+1];
		for (int i=leftIndex; i<=rightIndex; i++){
			counter[array[i]-menor]++;
		}
		
		int pos = leftIndex; 
		for (int i=0; i<counter.length; i++){
			for (int j=0; j<counter[i]; j++){
				array[pos++] = i+menor;
			}
		}	
	}

}
