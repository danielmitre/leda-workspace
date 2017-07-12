package adt.linkedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return next == null;
	}

	@Override
	public int size() {
		if (next == null){
			return 0;
		}
		return next.size()+1;
	}

	@Override
	public T search(T element) {
		if (element.equals(data)){
			return element;
		}
		
		if (next != null){
			return next.search(element);
		}
		
		return null;
	}

	@Override
	public void insert(T element) {
		if (next == null){
			next = new RecursiveSingleLinkedListImpl<T>(element, null);
		} else {
			next.insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (next.data.equals(element)){
			next = next.next;
		} else {
			next.remove(element);
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[size()];
		RecursiveSingleLinkedListImpl<T> it = this;
		for (int i=0; i<size(); i++){
			array[i] = it.data;
			
			if (it.next != null) {
				it = it.next;
			}
		}
		System.out.println(Arrays.toString(array));
		return array;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
