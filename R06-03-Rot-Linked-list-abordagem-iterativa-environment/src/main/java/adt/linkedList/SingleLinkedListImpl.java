package adt.linkedList;

import java.util.Arrays;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return getHead().isNIL();
	}

	@Override
	public int size() {
		int tam = 0;
		SingleLinkedListNode<T> it = getHead();
		
		while (!it.isNIL()){
			tam++;
			it = it.getNext();
		}
		return tam;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> it = getHead();
		while (it.getNext() != null && !it.isNIL()){
			if (it.getData().equals(element)) return it.getData();
			it = it.getNext();
		}
		return null;
	}

	@Override
	public void insert(T element) {
		
		SingleLinkedListNode<T> it = getHead();
		while (!it.isNIL()){
			it = it.getNext();
		}

		it.setData(element);
		it.setNext(new SingleLinkedListNode<T>());
		
	}

	@Override
	public void remove(T element) {
		if (getHead().getData().equals(element)){
			setHead(getHead().getNext());
		} else {
			SingleLinkedListNode<T> it = getHead();
			while (!it.getNext().isNIL() && !it.getNext().getData().equals(element)){
				it = it.getNext();
			}
			if (!it.getNext().isNIL()){
				it.setNext(it.getNext().getNext());
			}
		}		
	}

	@Override
	public T[] toArray() {
		T[] arr = (T[]) new Object[size()];
		int pos = 0;
		SingleLinkedListNode<T> it = getHead();
		while (!it.isNIL()){
			arr[pos++] = it.getData();
			it = it.getNext();
			
		}
		System.out.println(Arrays.toString(arr));
		return arr;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
