package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();

      root.setLeft(new RBNode<T>());
      root.setRight(new RBNode<T>());

      root.getLeft().setParent(root);
      root.getRight().setParent(root);
   }

   protected int blackHeight() {
      return blackHeight((RBNode<T>) root);
   }

   private int blackHeight(RBNode<T> node) {
      if (node.isEmpty())
         return 1;

      int maiorAltura = Math.max(blackHeight((RBNode<T>) node.getLeft()), blackHeight((RBNode<T>) node.getRight()));

      if (isBlack(node)) {
         return 1 + maiorAltura;
      }

      return maiorAltura;
   }

   private boolean isBlack(RBNode<T> node) {
      return node.getColour() == Colour.BLACK;
   }

   private boolean isRed(RBNode<T> node) {
      return node.getColour() == Colour.RED;
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      return verifyChildrenOfRedNodes((RBNode<T>) root);
   }

   private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
      if (node.isEmpty())
         return true;

      boolean res = true;

      if (isRed(node)) {
         if (isRed((RBNode<T>) node.getLeft()) || isRed((RBNode<T>) node.getRight())) {
            res = true;
         }
      }

      res &= verifyChildrenOfRedNodes((RBNode<T>) node.getLeft());
      res &= verifyChildrenOfRedNodes((RBNode<T>) node.getRight());

      return res;
   }

   /**
    * Verifies the black-height property from the root. The method blackHeight
    * returns an exception if the black heights are different.
    */
   protected boolean verifyBlackHeight() {
      return verifyBlackHeight((RBNode<T>) root);
   }

   private boolean verifyBlackHeight(RBNode<T> node) {
      if (node.isEmpty())
         return true;

      int leftHeight = blackHeight((RBNode<T>) node.getLeft());
      int rightHeight = blackHeight((RBNode<T>) node.getRight());

      return leftHeight == rightHeight;
   }

   protected void rotacionaDireita(BSTNode<T> node) {
      BSTNode<T> filho = (BSTNode<T>) node.getLeft();

      if (node.getParent() != null) {
         if (isLeftChild(node)) {
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

      if (node.getParent() != null) {
         if (isLeftChild(node)) {
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

   @Override
   public void insert(T element) {
      BSTNode<T> it = root;
      if (isEmpty()) {
         root.setData(element);
      } else {

         while (!it.isEmpty()) {
            if (it.getData().compareTo(element) > 0) {
               it = (BSTNode<T>) it.getLeft();
            } else {
               it = (BSTNode<T>) it.getRight();
            }
         }
         it.setData(element);

         it.setLeft(new RBNode<T>());
         it.setRight(new RBNode<T>());

         it.getLeft().setParent(it);
         it.getRight().setParent(it);
      }
      fixUpCase1((RBNode<T>) it);
   }

   @Override
   public RBNode<T>[] rbPreOrder() {
      RBNode<T>[] arr = new RBNode[size()];

      if (size() == 0)
         return arr;

      return rbPreOrder(arr, (RBNode<T>) root, 0);
   }

   private RBNode<T>[] rbPreOrder(RBNode<T>[] arr, RBNode<T> node, int idx) {
      int sizeLeft = size((RBNode<T>) node.getLeft());
      arr[idx] = node;

      if (!node.getLeft().isEmpty()) {
         rbPreOrder(arr, (RBNode<T>) node.getLeft(), idx + 1);
      }
      if (!node.getRight().isEmpty()) {
         rbPreOrder(arr, (RBNode<T>) node.getRight(), idx + 1 + sizeLeft);
      }

      return arr;
   }

   private int size(RBNode<T> node) {
      int result = 0;
      // base case means doing nothing (return 0)
      if (!node.isEmpty()) { // indusctive case
         result = 1 + size((RBNode<T>) node.getLeft()) + size((RBNode<T>) node.getRight());
      }
      return result;
   }

   private RBNode<T> getUncle(RBNode<T> node) {
      BSTNode<T> parent = (BSTNode<T>) node.getParent();
      if (isLeftChild(parent)) {
         return (RBNode<T>) parent.getParent().getRight();
      } else {
         return (RBNode<T>) parent.getParent().getLeft();
      }
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
      if (!root.equals(node)) {
         node.setColour(Colour.RED);
         fixUpCase2(node);
      } else {
         node.setColour(Colour.BLACK);
      }
   }

   protected void fixUpCase2(RBNode<T> node) {
      if (isRed((RBNode<T>) node.getParent())) {
         fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      if (isRed(getUncle(node))) {
         helperCase3((RBNode<T>) node.getParent().getParent());
         fixUpCase1((RBNode<T>) node.getParent().getParent());
      } else {
         fixUpCase4(node);
      }
   }

   private void helperCase3(RBNode<T> grandParent) {
      grandParent.setColour(Colour.RED);
      ((RBNode<T>) grandParent.getLeft()).setColour(Colour.BLACK);
      ((RBNode<T>) grandParent.getRight()).setColour(Colour.BLACK);
   }

   protected void fixUpCase4(RBNode<T> node) {
      if (isZigueZague(node)) {
         if (isLeftChild(node)) {
            rotacionaDireita((BSTNode<T>) node.getParent());
            fixUpCase5((RBNode<T>) node.getRight());
         } else {
            rotacionaEsquerda((BSTNode<T>) node.getParent());
            fixUpCase5((RBNode<T>) node.getLeft());
         }
      } else {
         fixUpCase5(node);
      }
   }

   private boolean isZigueZague(RBNode<T> node) {
      if (isLeftChild(node) && isRightChild((BSTNode<T>) node.getParent())) {
         return true;
      }
      if (isRightChild(node) && isLeftChild((BSTNode<T>) node.getParent())) {
         return true;
      }
      return false;
   }

   protected void fixUpCase5(RBNode<T> node) {
      ((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
      ((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);

      if (isLeftChild(node)) {
         rotacionaDireita((BSTNode<T>) node.getParent().getParent());
      } else {
         rotacionaEsquerda((BSTNode<T>) node.getParent().getParent());
      }
   }
}
