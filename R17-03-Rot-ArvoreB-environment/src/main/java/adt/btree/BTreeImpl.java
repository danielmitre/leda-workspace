package adt.btree;

import java.util.Iterator;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

   protected BNode<T> root;
   protected int order;

   public BTreeImpl(int order) {
      this.order = order;
      this.root = new BNode<T>(order);
   }

   @Override
   public BNode<T> getRoot() {
      return this.root;
   }

   @Override
   public boolean isEmpty() {
      return this.root.isEmpty();
   }

   @Override
   public int height() {
      if (isEmpty())
         return -1;
      return height(this.root);
   }

   private int height(BNode<T> node) {
      if (node.isLeaf())
         return 0;

      return 1 + height(node.getChildren().getFirst());
   }

   @Override
   public BNode<T>[] depthLeftOrder() {
      BNode<T>[] array = new BNode[getNumberOfNodes(root)];

      depthLeftOrder(array, 0, this.root);

      return array;
   }

   private int getNumberOfNodes(BNode<T> node) {
      if (node.isEmpty())
         return 0;

      int res = 1;

      Iterator<BNode<T>> it = node.children.iterator();
      while (it.hasNext()) {
         res += getNumberOfNodes(it.next());
      }

      return res;
   }

   private int depthLeftOrder(BNode<T> arr[], int idx, BNode<T> node) {
      if (!node.isEmpty()) {
         arr[idx++] = node;

         for (int i = 0; i < node.children.size(); i++) {
            idx = depthLeftOrder(arr, idx, node.children.get(i));
         }
      }

      return idx;
   }

   @Override
   public int size() {
      return size(this.root);
   }

   private int size(BNode<T> node) {
      if (node.isEmpty())
         return 0;

      int tam = node.size();
      Iterator<BNode<T>> it = node.children.iterator();
      while (it.hasNext()) {
         tam += size(it.next());
      }
      return tam;
   }

   @Override
   public BNodePosition<T> search(T element) {
      return search(root, element);
   }

   private BNodePosition<T> search(BNode<T> node, T element) {
      int pos = 0;

      while (pos < node.getElements().size() && element.compareTo(node.getElementAt(pos)) > 0) {
         pos++;
      }

      if (pos < node.getElements().size() && element.equals(node.getElementAt(pos))) {
         return new BNodePosition<T>(node, pos);
      }

      if (!node.isLeaf()) {
         return search(node.getChildren().get(pos), element);
      }

      return new BNodePosition<T>();
   }

   @Override
   public void insert(T element) {
      if (element != null)
         insert(root, element);
   }

   private void insert(BNode<T> node, T element) {
      int pos = 0;

      while (pos < node.getElements().size() && element.compareTo(node.getElementAt(pos)) > 0) {
         pos++;
      }

      if (node.isLeaf()) {
         node.addElement(element);
         node.addChild(pos, new BNode<T>(this.order));
         if (node.size() > node.getMaxKeys()) {
            node.split();

            while (node.parent != null) {
               node = node.parent;
            }

            this.root = node;
         }
      } else {
         if (node.size() == node.getMaxKeys()) {
            node.split();
         }
         insert(node.getChildren().get(pos), element);
      }
   }

   private void split(BNode<T> node) {
      node.split();
   }

   private void promote(BNode<T> node) {
      node.promote();
   }

   // NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
   @Override
   public BNode<T> maximum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public BNode<T> minimum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public void remove(T element) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

}
