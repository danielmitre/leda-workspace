package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Daniel Mitre
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {
	
	public AVLTreeImpl(){
		super();
	}

	@Override
	protected void remove(BSTNode<T> node){
		if (node.isLeaf()){
			node.setData(null);
			rebalanceUp((BSTNode<T>) node);
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
			rebalanceUp(filho);
			
		} else {	//2 filhos
			BSTNode<T> pre = sucessor(node.getData());
			T data = node.getData();
			
			node.setData(pre.getData());
			pre.setData(data);
			
			remove(pre);
		}
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
			rebalanceUp((BSTNode<T>) it);
		}
	}
	
	protected void insertWithoutBalance(T element){
		super.insert(element);
	}
	

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		BSTNode<T> filho;
		int balanco = calculateBalance(node);
		if (balanco >= 2){	//desbalanceado para esquerda
			filho = (BSTNode<T>) node.getLeft();
			if (calculateBalance(filho) <= -1){	//pendendo para direita
				rotacionaEsquerda(filho);
			}
			rotacionaDireita(node);
		} else if (balanco <= -2){
			filho = (BSTNode<T>) node.getRight();
			if (calculateBalance(filho) >= 1){
				rotacionaDireita(filho);
			}
			rotacionaEsquerda(node);
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null){
			rebalance(node);
			rebalanceUp((BSTNode<T>) node.getParent());
		}
	}
	
	protected void rotacionaDireita(BSTNode<T> node) {
		BSTNode<T> filho = (BSTNode<T>) node.getLeft();
		
		if (node.getParent() != null){
			if (isLeftChild(node)){
				node.getParent().setLeft(filho);
			} else {
				node.getParent().setRight(filho);
			}
		} else {
			root = filho;
		}
		
		
		node.setLeft(filho.getRight());
		filho.setRight(node);
		filho.setParent(node.getParent());
		node.setParent(filho);
	}
	
	protected void rotacionaEsquerda(BSTNode<T> node) {
		BSTNode<T> filho = (BSTNode<T>) node.getRight();
		
		if (node.getParent() != null){
			if (isLeftChild(node)){
				node.getParent().setLeft(filho);
			} else {
				node.getParent().setRight(filho);
			}
		} else {
			root = filho;
		}
		
		node.setRight(filho.getLeft());
		filho.setLeft(node);
		filho.setParent(node.getParent());
		node.setParent(filho);
	}
}
