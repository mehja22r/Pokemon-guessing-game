package GameDataStructures;

/**
 * DefaultBinaryTreeNode is the class for a basic binary tree node,
 * with data of type T and pointers to left and right children.
 */
public class DefaultBinaryTreeNode<T> implements BinaryTreeNode<T> {

	/**data is an instance of type T**/
	public T data;

	/**leftChild and rightChild are binary nodes**/
	
	public BinaryTreeNode<T> leftChild;
	
	public BinaryTreeNode<T> rightChild;

	/**
	 * Get the data stored at this node.
	 * @return Object data.
	 */
	public DefaultBinaryTreeNode() {
		
	}
	
	@Override
	public T getData() {
		return data;
	}

	/**
	 * Set the root node for this tree.
	 */
	@Override
	public void setData(Object data) {
		this.data = (T) data;
	}

	/**
	 * Get the left child.
	 * @return BinaryTreeNode that is left child,
	 * or null if no child.
	 */
	@Override
	public BinaryTreeNode<T> getLeftChild() {
		return leftChild;
	}

	/**
	 * Get the right child.
	 * @return BinaryTreeNode that is right child,
	 * or null if no child.
	 */
	@Override
	public BinaryTreeNode<T> getRightChild() {
		return rightChild;
	}

	/**
	 * Set the left child.
	 */
	@Override
	public void setLeftChild(BinaryTreeNode<T> left) {
		leftChild = left;
	}

	/**
	 * Set the right child.
	 */
	@Override
	public void setRightChild(BinaryTreeNode<T> right) {
		rightChild = right;
	}

	/**
	 * Tests if this node is a leaf (has no children).
	 * @return true if leaf node.
	 */
	@Override
	public boolean isLeaf() {
		if (leftChild == null && rightChild == null) {
			return true;
		}
		return false;
	}

}
