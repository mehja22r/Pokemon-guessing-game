package GameDataStructures;

public class LinkedListNode<T> {
	
	/**data is a value of type T to be stored in nodes in the list**/
	public T data;
	/**next is a node**/
	public LinkedListNode<T> next;
	
	//constructor
	public LinkedListNode () {
		
	}
	
	/**
	 * Set the data stored at this node.
	 **/
	public void setData( T data ) {
		this.data = data;
	}
	 
	/**
	 * Get the data stored at this node.
	 **/
	public T getData() {
		return data;
	}
	 
	/**
	 * Set the next pointer to passed node.
	 **/
	public void setNext( LinkedListNode<T> node ) {
		next = node;
	}
	 
	/**
	 * Get (pointer to) next node.
	 **/
	public LinkedListNode<T> getNext() {
		return next;
	}
	 
	/**
	 * Returns a String representation of this node.
	 **/
	public String toString() {
		if (data == null) {
			return "null";
		}
		return (data.toString());
	}
		
}
