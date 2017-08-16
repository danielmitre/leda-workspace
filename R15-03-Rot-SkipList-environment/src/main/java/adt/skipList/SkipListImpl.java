package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T> node = new SkipListNode<T>(key, height, newValue);
		
		SkipListNode<T>[] previous = new SkipListNode[height];
		
		SkipListNode<T> it = root;
		savePath(previous, height, it);
		int h = maxHeight;
		boolean none = false;
		while (it.getKey() < key && !none){
			none = true;
			
			for (int i=h; i>=0; i--){
				if (it.getForward(Math.min(i, it.height()-1)).getKey() < key){
					it = it.getForward(Math.min(i, it.height()));
					savePath(previous, height, it);
					none = false;
					h = Math.min(i, it.height());
					
				}
			}
		}
		
		for (int i=0; i<height; i++){
			node.forward[i] = previous[i].forward[i];
			previous[i].forward[i] = node;
		}
	}

	private void savePath(SkipListNode<T>[] path, int height, SkipListNode<T> node) {
		for (int i=Math.min(height, node.height())-1; i>=0; i--){
			path[i] = node;
		}
	}

	@Override
	public void remove(int key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public SkipListNode<T> search(int key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public int size() {
		SkipListNode<T> it = root;
		int size = 0;
		while (it.getForward(0) != NIL){
			size++;
			it = it.getForward(0);
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] arr = new SkipListNode[size()+2];
		SkipListNode<T> it = root;
		for (int i=0; i<size()+2; i++){
			arr[i] = it;
			it.getForward(0);
		}
		return arr;
	}

}
