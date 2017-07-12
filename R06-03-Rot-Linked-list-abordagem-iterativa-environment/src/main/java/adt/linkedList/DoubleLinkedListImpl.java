package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	
	public DoubleLinkedListImpl(){
		setLast(new DoubleLinkedListNode<T>());
		setHead(getLast());
	}
	
	
	@Override
	public void insert(T element) {
		
		DoubleLinkedListNode<T> it = (DoubleLinkedListNode<T>) getHead();
		while (!it.isNIL()){
			it = (DoubleLinkedListNode<T>) it.getNext();
		}

		it.setData(element);
		it.setNext(new DoubleLinkedListNode<T>());
		it.setPrevious(getLast());
		setLast((DoubleLinkedListNode<T>) it);
		
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
				if (it.getNext().getNext().isNIL()){
					setLast((DoubleLinkedListNode<T>) it.getNext().getNext());
				}
				
				it.setNext(it.getNext().getNext());
				
			}
		}		
	}
	
	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), new DoubleLinkedListNode<T>());
		novoNode.next = head;
		((DoubleLinkedListNode<T>)head).previous = novoNode;
		if (head.isNIL()){
			last = novoNode;
		}
		head = novoNode;	
	}

	@Override
	public void removeFirst() {
		if (! head.isNIL()){
			head = head.next;
			if (head.isNIL()){
				last = (DoubleLinkedListNode<T>) head;
			}
			((DoubleLinkedListNode<T>)head).previous = new DoubleLinkedListNode<T>();			
		}
	}

	@Override
	public void removeLast() {
		if (! isEmpty()){
			if (! last.isNIL()){
				last = last.previous;
 
				if (last.isNIL()){
					head = last;
				}
			}
			last.next = new DoubleLinkedListNode<T>();
		}
	}
	
	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
