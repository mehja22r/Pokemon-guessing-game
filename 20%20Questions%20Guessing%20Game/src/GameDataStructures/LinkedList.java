package GameDataStructures;

public class LinkedList<T> {

	/**declaring a linked list node to be the first node in the list**/ 
	public LinkedListNode<T> head; 
	
	//constructor initializes head to null at first
	public LinkedList() {
		head = null;
	}
	
	/**
	 * Get data stored in head node of list.
	 **/
	public T getFirst() {
		return head.getData();
	}
	 
	/**
	 * Get the head node of the list.
	 **/
	public LinkedListNode<T> getFirstNode() {
		return head;
	}
	 
	/**
	 * Get data stored in tail node of list.
	 **/
	public T getLast() {
		
		//create a new node and give it head's data
		LinkedListNode<T> temp = head;
		
		//if head is not null
		if (head.getData() != null) {
			
			//while the node after temp is not null
			while (temp.getNext() != null) {
				
				//update temp to be the node it points to
				temp = temp.getNext();
			}
			
		//return the data of temp node
		return temp.getData();
		}
		
		//if head is null, list is empty, return null
		return null;
	}
	 
	/**
	 * Get the tail node of the list.
	 **/
	public LinkedListNode<T> getLastNode() {
		
		//create a new node equal to first node in list
		LinkedListNode<T> temp = head;
		
		//if head is not null
		if (head != null) {
			
			//while the node after temp is not null
			while (temp.getNext() != null) {
				
				//update temp to be equal to the node it points to
				temp = temp.getNext();
			}
			
		//return the node
		return temp;
		}
		
		//otherwise return head which is null
		return head;
	}
	 
	/**
	 * Insert a new node with data at the head of the list.
	 **/
	public void insertFirst( T data )  {
		
		//create a new linked list node named tempo
		LinkedListNode<T> tempo = new LinkedListNode<T>();
		
		//set the data passed in through parameter to tempo node
		tempo.setData(data);
		
		//if head is null
		if (head == null) {
			//set the data in head equal to tempo
			head = tempo;
			//the the node head points to null
			head.setNext(null);
			}
		
		//if head is not null
		else {
			//the node after head will get head's data
			tempo.setNext(head);
			//head will get new data from tempo
			head = tempo;
		}
	}
		
	 
	/**
	 * Insert a new node with data after currentNode
	 **/
	
	public void insertAfter( LinkedListNode<T> currentNode, T data ) {
		
		//create a new node called temp
		LinkedListNode<T> temp = new LinkedListNode<T>();
		
		//give the node the data passed in through function parameter
		temp.setData(data);
		
		//make temp point to the node after currentNode
		temp.setNext(currentNode.getNext());
		
		//make currentNode point to temp
		currentNode.setNext(temp);
	}
	  
	/**
	 * Insert a new node with data at the tail of the list.
	 **/
	public void insertLast( T data ) {
		
		//create a new node
		LinkedListNode<T> tempo =  new LinkedListNode<T>();
		
		//set the data 
		tempo.setData(data);
		
		// if the list has nodes
		if (head != null) {
		
		//get the last node and let it point to tempo
		getLastNode().setNext(tempo);	
		}
		
		//if the list is empty
		else if (head == null) {
			//put the tempo node in head
			head = tempo;
		}
	}
	 
	/**
	 * Remove the first node
	 **/
	public void deleteFirst() {
		
		//if the list has nodes
		if (head != null) {
			//head will become the node it pointed to
			head = head.getNext();
		}
	}
	 
	/**
	 * Remove the last node
	 **/
	public void deleteLast() {
		//create a new node equal to head
		LinkedListNode<T> temp = head;
		//if there are nodes in the list
		if (head != null && head.getNext() != null) {
			
			//while the data of the 2 nodes after temp is not null
			while (temp.getNext().getNext() != null) {
				//update temp to the value to the next node
				temp = temp.getNext();
			}
		//temp will be the second last node in the list and the next node will be set to null
		temp.setNext(null);
		}
		
		//if there is only one node in the list
		else if (head != null && head.getNext() == null)
		{
			//delete it
			head = null;
		}
	}
	 
	/**
	 * Remove node following currentNode
	 * If no node exists (i.e., currentNode is the tail), do nothing
	 **/
	public void deleteNext( LinkedListNode<T> currentNode ) {
		//if current node is the last node
		if (currentNode == getLastNode()) {
			return;
		}
		//if the node after is the last node
		else if (currentNode.getNext() == getLastNode())
		{
			//set it to null
			currentNode.setNext(null);
		}
		else {
			//create a new node
			LinkedListNode<T> temp = new LinkedListNode<T>();
			//set temp equal to the 2 nodes after current node
			temp = currentNode.getNext().getNext();
			//make current node point to temp, deleting the node right after current node
			currentNode.setNext(temp);
		}
	}
	
	 
	/**
	 * Return the number of nodes in this list.
	 **/
	public int size() {
		//int i keeps track of size of the list
		int i = 0;
		//a new node temp is set equal to head
		LinkedListNode<T> temp = head;
		//if temp is not null (there are nodes in the list)
		if (temp != null) {
			//while the temp is not null
			while ( temp != null) {
				//temp will be equal to its next node
				temp = temp.getNext();
				//increment i
				i++;
			}
			//return the size of the array
			return i;
		}
		//if the head is null, there are no nodes so return 0
		return 0;
	}
	 
	/**
	 * Check if list is empty.
	 * @return true if list contains no items.
	 **/
	public boolean isEmpty() {
	//if head is null there are no nodes in the list
	 if (head == null) {
		 return true;
	 }
	 //return false if there are items
	 return false;
	}
	 
	/**
	 * Return a String representation of the list.
	 **/
	public String toString() {
		//create a new node and set it equal to head
		LinkedListNode<T> tempo = head;
		if (head == null) {
			return "null";
		}
		//create a string s 
		String s = "";    
		//if head is not null
		if (tempo != null) {
			//store head's data in s
			s = tempo.toString();
			//for loop runs through the size of the list, subtracting 1 because head is already included in s
			for (int i = 0; i<size()-1; i++) {
				//tempo gets updated to the next node
				tempo = tempo.getNext();
				//the value of tempo gets stored in s
				s +=  " -> "+tempo.toString();
			}
		//return the list
		return s;
	}
	//if the list is empty return the blank s
	return s;
}
}
