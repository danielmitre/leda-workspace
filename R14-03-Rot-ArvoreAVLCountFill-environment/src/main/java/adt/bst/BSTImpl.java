package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
		
		root.setLeft(new BSTNode<T>());
		root.getLeft().setParent(root);
		
		root.setRight(new BSTNode<T>());
		root.getRight().setParent(root);
		
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		if (isEmpty()) return -1;
		return height(root);
	}
	
	protected int height(BSTNode<T> node){
		if (node == null){
			return -1;
		}
		
		if (node.isEmpty()){
			return -1;
		}
		
		int left = -1;
		int right = -1;
		
		if (node.getLeft() != null && !node.getLeft().isEmpty()){
			left = height((BSTNode<T>) node.getLeft());
		}
		
		if (node.getRight() != null && !node.getRight().isEmpty()){
			right = height((BSTNode<T>) node.getRight());
		}
		
		return (Math.max(left, right) + 1);
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> it = root;
		while (!it.isEmpty()){
			if (it.getData().equals(element)){
				return it;
			}
			
			if (it.getData().compareTo(element) > 0){
				it = (BSTNode<T>) it.getLeft();
			} else {
				it = (BSTNode<T>) it.getRight();
			}
		}
		return it;
	}
	
	@Override
	public void insert(T element) {
		if (isEmpty()){
			root.setData(element);
		} else {
			BSTNode<T> it = root;
			while (!it.isEmpty()){
				if (it.getData().compareTo(element) > 0){
					it = (BSTNode<T>) it.getLeft();
				} else {
					it = (BSTNode<T>) it.getRight();
				}
			}
			it.setData(element);
			
			it.setLeft(new BSTNode<T>());
			it.setRight(new BSTNode<T>());
			
			it.getLeft().setParent(it);
			it.getRight().setParent(it);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if (root.isLeaf()) return root;
		if (isEmpty()) return null;
		
		BSTNode<T> it = root;
		while (!it.getRight().isEmpty()){
			it = (BSTNode<T>) it.getRight();
		}
		return it;
	}

	@Override
	public BSTNode<T> minimum() {
		if (root.isLeaf()) return root;
		if (isEmpty()) return null;
		
		BSTNode<T> it = root;
		
		while (!it.getLeft().isEmpty()){
			it = (BSTNode<T>) it.getLeft();
		}
		return it;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> it = search(element);
		
		if (!it.getRight().isEmpty()){
			it = (BSTNode<T>) it.getRight();
			
			while (!it.getLeft().isEmpty()){
				it = (BSTNode<T>) it.getLeft();
			}
		} else {
			while (it.getParent() != null && !isLeftChild(it)){
				it = (BSTNode<T>) it.getParent();
			}
			it = (BSTNode<T>) it.getParent();
		}
		
		return it;
	}
	
	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> it = search(element);
		
		if (!it.getLeft().isEmpty()){
			it = (BSTNode<T>) it.getLeft();
			
			while (!it.getRight().isEmpty()){
				it = (BSTNode<T>) it.getRight();
			}
		} else {
			while (it.getParent() != null && isLeftChild(it)){
				it = (BSTNode<T>) it.getParent();
			}
			it = (BSTNode<T>) it.getParent();
		}
		
		return it;
	}
	
	
	protected boolean isLeftChild(BSTNode<T> node){
		if (node.getParent() == null) return false;
		return node.equals(node.getParent().getLeft());
	}
	
	protected boolean isRightChild(BSTNode<T> node){
		if (node.getParent() == null) return false;
		return node.equals(node.getParent().getRight());
	}

	@Override
	public void remove(T element) {
		BSTNode<T> el = search(element);
		if (!el.isEmpty()) remove(el);
	}
	
	protected void remove(BSTNode<T> node){
		if (node.isLeaf()){
			node.setData(null);
		} else if (node.getLeft().isEmpty() ^ node.getRight().isEmpty()){ //1 filho
			BSTNode<T> filho = null;		
			
			if (!node.getLeft().isEmpty()){	//filho na esquerda
				filho = (BSTNode<T>) node.getLeft();
			} else {						//filho na direita
				filho = (BSTNode<T>) node.getRight();
			}
			
			filho.setParent(node.getParent());
			if (node.getParent() == null){
				root = filho; 
			} else {
				if (isLeftChild(node)){
					node.getParent().setLeft(filho);
				} else {
					node.getParent().setRight(filho);
				}
			}
			
		} else {	//2 filhos
			BSTNode<T> pre = sucessor(node.getData());
			T data = node.getData();
			
			node.setData(pre.getData());
			pre.setData(data);
			
			remove(pre);
		}
	}

	@Override
	public T[] preOrder() {
		T[] arr = (T[]) new Comparable[size()];
		
		if (size() == 0) return arr;
		
		return preOrder(arr, root, 0);
	}

	private T[] preOrder(T[] arr, BSTNode<T> node, int idx) {
		int sizeLeft = size((BSTNode<T>) node.getLeft()); 
		arr[idx] = node.getData();
		if (!node.getLeft().isEmpty()){
			preOrder(arr, (BSTNode<T>) node.getLeft(), idx+1);
		}
		if (!node.getRight().isEmpty()){
			preOrder(arr, (BSTNode<T>) node.getRight(), idx+1+sizeLeft);
		}
		
		return arr;
	}

	@Override
	public T[] order() {
		T[] arr = (T[]) new Comparable[size()];
		
		if (size() == 0) return arr;
		
		return order(arr, root, 0);
	}

	private T[] order(T[] arr, BSTNode<T> node, int idx) {
		int sizeLeft = size((BSTNode<T>) node.getLeft()); 
		
		if (!node.getLeft().isEmpty()){
			order(arr, (BSTNode<T>) node.getLeft(), idx);
		}
		
		arr[idx+sizeLeft] = node.getData();
		
		if (!node.getRight().isEmpty()){
			order(arr, (BSTNode<T>) node.getRight(), idx+1+sizeLeft);
		}
		
		return arr;
	}

	@Override
	public T[] postOrder() {
		T[] arr = (T[]) new Comparable[size()];
		
		if (size() == 0) return arr;
		
		return postOrder(arr, root, 0);
	}

	private T[] postOrder(T[] arr, BSTNode<T> node, int idx) {
		int sizeLeft = size((BSTNode<T>) node.getLeft());
		int sizeRight= size((BSTNode<T>) node.getRight()); 
		
		arr[idx+sizeLeft+sizeRight] = node.getData();
		
		if (!node.getLeft().isEmpty()){
			postOrder(arr, (BSTNode<T>) node.getLeft(), idx);
		}
		
		if (!node.getRight().isEmpty()){
			postOrder(arr, (BSTNode<T>) node.getRight(), idx+sizeLeft);
		}
		
		return arr;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
