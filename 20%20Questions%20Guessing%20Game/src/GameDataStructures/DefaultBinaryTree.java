package GameDataStructures;

/**
 * DeafultBinaryTree is the class for a basic binary tree. 
 */
public class DefaultBinaryTree<T> implements BinaryTree<T> {

	/**root is a binary tree node**/
	public BinaryTreeNode<T> root;
	
	/**
	 * Get the root node for this tree.
	 * @return root or null if tree is empty.
	 */
	@Override
	public BinaryTreeNode<T> getRoot() {
		if (root !=  null) {
			return root;
		}
		return null;
	}

	/**
	 * Set the root node for this tree.
	 */
	@Override
	public void setRoot(BinaryTreeNode<T> root) {
			this.root = root;
	}

	/**
	 * Test if the tree is empty.
	 * 
	 * @return true if tree has no data.
	 */
	@Override
	public boolean isEmpty() {
		if (root != null) {
			return false;
		}
		return true;
	}

	/**
	 * Get the data of this tree using inorder traversal.
	 * 
	 * @return inorder List.
	 */
	@Override
	public LinkedList<T> inorderTraversal() {
		
		//creating a linked list to store the sequence of elements
		LinkedList<T> list = new LinkedList<T>();
		
		//call recursion
		inorderTraversalRecursion(list, root);
		
		//return the inorderlist
		return list;
	}

	/**recurisve method to get inorder traversal of tree**/
	public void inorderTraversalRecursion(LinkedList<T> list, BinaryTreeNode<T> node) {
		
		//base case
		if (node == null) {
			return;
		}
		
		//2nd base case, if node has no children
		else if (node.isLeaf()) {
			
			//insert the node at the end
			list.insertLast((T) node.getData());
			
			return;
		}
		
		//recursive case
		else {
			
			//get leftChild
			inorderTraversalRecursion(list, node.getLeftChild());
			
			//get the node
			list.insertLast((T) node.getData());
			
			//then get right child
			inorderTraversalRecursion(list, node.getRightChild());
		}
	}

	/**
	 * Get the data of this tree using preorder traversal.
	 * 
	 * @return preorder List.
	 */
	@Override
	public LinkedList<T> preorderTraversal() {
		
		//a new linked list to store sequence of data
		LinkedList<T> list = new LinkedList<T>();
		
		//call recursion
		preorderTraversalRecursion(list, root);
		
		return list;
	}

	/**a recursive method to get preorder traversal of a tree**/
	public void preorderTraversalRecursion(LinkedList<T> list, BinaryTreeNode<T> node) {
		
		//base case
		if (node == null) {
			return;
		}
		
		//2nd base case, if node has no children
		else if (node.isLeaf()) {
			
			//insert the node's data and end function
			list.insertLast((T) node.getData());
			
			return;
		}
		
		//recursive case
		else {
			
			//insert the node
			list.insertLast((T) node.getData());
			
			//get the left child
			preorderTraversalRecursion(list, node.getLeftChild());
			
			//then get the right child
			preorderTraversalRecursion(list, node.getRightChild());
		}
	}

	/**
	 * Get the data of this tree using postorder traversal.
	 * 
	 * @return postorder List.
	 */
	@Override
	public LinkedList<T> postorderTraversal() {
		
		//a list to store sequence of elements
		LinkedList<T> list = new LinkedList<T>();
		
		//recursion
		postorderTraversalRecursion(list, root);
		
		return list;
	}

	/**a recursive method to get a postorder traversal of a tree**/
	public void postorderTraversalRecursion(LinkedList<T> list, BinaryTreeNode<T> node) {
		
		//base case
		if (node == null) {
			return;
		}
		
		//2nd base case, if node has no children
		else if (node.isLeaf()) {
			
			//insert the node at the end of the list
			list.insertLast( (T) node.getData());
			
			return;
		}
		
		//recurisve case
		else {
			
			//get the left child
			postorderTraversalRecursion(list, node.getLeftChild());
			
			//get the right child
			postorderTraversalRecursion(list, node.getRightChild());
			
			//then insert the node
			list.insertLast((T) node.getData()); 
		}
	}

	/**
	 * Print the tree using inorder traversal.
	 * 
	 * @return inorder String
	 */
	@Override
	public String inorderString() {
		String s = "";
		s = "inorder: "+inorderTraversal().toString();
		return s;
	}

	/**
	 * Print the tree using preorder traversal.
	 * 
	 * @return preorder String
	 */
	@Override
	public String preorderString() {
		String s = "";
		s = "preOrder: "+preorderTraversal().toString();
		return s;
	}

	/**
	 * Print the tree using postorder traversal.
	 * 
	 * @return preorder String
	 */
	@Override
	public String postorderString() {
		String s = "";
		s = "postOrder: " + postorderTraversal().toString();
		return s;
	}

	/**main method creates a tree and implements traversals**/
	public static void main (String[] args) {
		
		//creating a new tree of type String
		BinaryTree<String> tree=  new DefaultBinaryTree<String>();

		//creating nodes of type String and setting data
		BinaryTreeNode<String> happy = new DefaultBinaryTreeNode<String>();
		happy.setData("happy");
		
		BinaryTreeNode<String> grumpy = new DefaultBinaryTreeNode <String> ();
		grumpy.setData("grumpy");
		
		BinaryTreeNode<String> bashful = new DefaultBinaryTreeNode<String>();
		bashful.setData("bashful");
		
		BinaryTreeNode<String> doc = new DefaultBinaryTreeNode<String>();
		doc.setData("doc");
		
		BinaryTreeNode<String> sneezy = new DefaultBinaryTreeNode<String>();
		sneezy.setData("sneezy");
		
		BinaryTreeNode<String> sleepy = new DefaultBinaryTreeNode<String>();
		sleepy.setData("sleepy");
		
		tree.setRoot(happy);
		
		happy.setRightChild(sleepy);

		sleepy.setRightChild(sneezy);

		happy.setLeftChild(doc);

		doc.setRightChild(grumpy);

		doc.setLeftChild(bashful);


		//print out tree in all three traversals

		System.out.println(tree.inorderString());

		System.out.println(tree.preorderString());

		System.out.println(tree.postorderString());
	}

}
