/**
 * BinaryTreeNode.java
 * CS 201
 * Heather Pon-Barry
 */

package GameDataStructures;
/**
 * BinaryTreeNode is the interface for a basic binary tree node,
 * with data of type T and pointers to left and right children.
 */
public interface BinaryTreeNode<T>
{

  /**
   * Get the data stored at this node.
   * @return Object data.
   */
  public T getData();

  /**
   * Set the data stored at this node.
   */
  public void setData(T data);

  /**
   * Get the left child.
   * @return BinaryTreeNode that is left child,
   * or null if no child.
   */
  public BinaryTreeNode<T> getLeftChild();

  /**
   * Get the right child.
   * @return BinaryTreeNode that is right child,
   * or null if no child.
   */
  public BinaryTreeNode<T> getRightChild();

  /**
   * Set the left child.
   */
  public void setLeftChild( BinaryTreeNode<T> left );

  /**
   * Set the right child.
   */
  public void setRightChild( BinaryTreeNode<T> right );

  /**
   * Tests if this node is a leaf (has no children).
   * @return true if leaf node.
   */
  public boolean isLeaf();

}
