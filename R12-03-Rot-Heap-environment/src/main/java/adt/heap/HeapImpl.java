package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita 
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[size()];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		int maior = position;
		
		if (left(position) <= index && comparator.compare(heap[left(position)], heap[maior]) > 0){
			maior = left(position);
		}
		
		if (right(position) <= index){
			//System.out.println(heap[right(position)]+ " - "+ heap[maior] + " = " + comparator.compare(heap[right(position)], heap[maior]));
			if (comparator.compare(heap[right(position)], heap[maior]) > 0){
				maior = right(position);
			}
		}
		
		util.Util.swap(heap, maior, position);
		
		if (position != maior){
			heapify(maior);
		} else if (position != 0){
			heapify(parent(position));
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		heap[++index] = element;
		heapify(parent(index));
	}

	@Override
	public void buildHeap(T[] array) {
		index = array.length-1;
		heap = array;
		
		for (int i=parent(index); i>=0; i--){
			heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		T res = heap[0];
		Util.swap(heap, 0, index--);
		heapify(parent(index+1));
		return res;
	}

	@Override
	public T rootElement() {
		return heap[0];
	}

	@Override
	public T[] heapsort(T[] array) {
		Heap<T> auxHeap = new HeapImpl<T>(new LesserThan<T>());
		
		auxHeap.buildHeap(array);
		T[] out = (T[]) new Comparable[auxHeap.size()];
		
		int tam = auxHeap.size(); 
		for (int i=0; i<tam; i++){
			out[i] = auxHeap.extractRootElement();
		}
		
		return out;
	}

	@Override
	public int size() {
		return index+1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}
}

class LesserThan<T extends Comparable<T>> implements Comparator<T> {
	@Override
	public int compare(T o1, T o2) {
		return o2.compareTo(o1);
	}
}
