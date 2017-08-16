package adt.avltree;

import adt.bst.BSTNode;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		super();
	}
	
	@Override
	protected void rebalance(BSTNode<T> node) {
		BSTNode<T> filho;
		int balanco = calculateBalance(node);
		if (balanco >= 2){	//desbalanceado para esquerda
			filho = (BSTNode<T>) node.getLeft();
			if (calculateBalance(filho) <= -1){	//pendendo para direita
				LRcounter++;
				rotacionaEsquerda(filho);
			} else {
				LLcounter++;
			}
			rotacionaDireita(node);
		} else if (balanco <= -2){
			filho = (BSTNode<T>) node.getRight();
			if (calculateBalance(filho) >= 1){
				rotacionaDireita(filho);
				RLcounter++;
			} else {
				RRcounter++;
			}
			rotacionaEsquerda(node);
		}
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		for (T el : array){
			insertWithoutBalance(el);
		}
	}

}
