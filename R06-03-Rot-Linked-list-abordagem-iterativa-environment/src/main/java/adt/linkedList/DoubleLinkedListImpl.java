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
			DoubleLinkedListNode<T> it = (DoubleLinkedListNode<T>) getHead();
			while (!it.getNext().isNIL() && !it.getNext().getData().equals(element)){
				it = (DoubleLinkedListNode<T>) it.getNext();
			}
			if (!it.getNext().isNIL()){
				if (it.getNext().getNext().isNIL()){
					setLast((DoubleLinkedListNode<T>) it.getNext().getNext());
				}
				
				it.setNext(it.getNext().getNext());
				((DoubleLinkedListNode<T>) it.getNext()).setPrevious(it);
			}
		}		
	}
	
	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), new DoubleLinkedListNode<T>());
		novoNode.setNext(getHead());
		novoNode.setPrevious(new DoubleLinkedListNode<T>());
		((DoubleLinkedListNode<T>)getHead()).setPrevious(novoNode);
		if (getHead().isNIL()){
			setLast(novoNode);
		}
		setHead(novoNode);	
	}

	@Override
	public void removeFirst() {
		if (!getHead().isNIL()){
			setHead(getHead().getNext());
			if (getHead().isNIL()){
				setLast((DoubleLinkedListNode<T>) getHead());
			}
			((DoubleLinkedListNode<T>)getHead()).setPrevious(new DoubleLinkedListNode<T>());			
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()){
			if (!getLast().isNIL()){
				setLast(getLast().getPrevious());
 
				if (getLast().isNIL()){
					setHead(getLast());
				}
			}
			getLast().setNext(new DoubleLinkedListNode<T>());
		}
	}
	
	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
