package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {
		data = null;
		next = null;
	}

	public RecursiveSingleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return data == null;
	}

	@Override
	public int size() {
		if (data == null){
			return 0;
		}
		
		if (next == null){
			return 1;
		}
		
		return next.size()+1;
	}

	@Override
	public T search(T element) {
		if (element == null){
			if (element == data) {
				return element;
			}
		} else if (element.equals(data)){
			return element;
		}
		
		if (next != null){
			return next.search(element);
		}
		
		return null;
	}

	@Override
	public void insert(T element) {
		if (data == null){
			data = element;
		} else {
			if (next == null){
				next = new RecursiveSingleLinkedListImpl<T>(element, null);
			} else {
				next.insert(element);
			}
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
		toArray(array, 0, size());
		return array;
	}
	
	protected void toArray(T[] arr, int idx, int tam){
		if (idx < tam){
			arr[idx] = data;
			
			if (next != null){
				next.toArray(arr, idx+1, tam);
			}
		}
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
