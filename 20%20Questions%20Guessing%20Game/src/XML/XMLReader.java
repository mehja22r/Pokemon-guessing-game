package XML;

/**CommutativeExpressionReader.java
 * CS 211 
 * Audrey St. John
 **/

// XML
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  
import org.w3c.dom.*;

import GameDataStructures.BinaryTree;
import GameDataStructures.BinaryTreeNode;
import GameDataStructures.DefaultBinaryTree;
import GameDataStructures.DefaultBinaryTreeNode;
import GameDataStructures.LinkedList;
import GameDataStructures.LinkedListNode;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;  







// io
import java.io.*;

/**
 * CommutativeExpressionReader reads xml files of expressions with
 * binary, commutative operators.
 *
 * @author Audrey Lee
 */
public class XMLReader
{

	/**guessingGame contains the game tree**/
	public static BinaryTree<String> guessingGame;

	/**answer list has list of answers to 20Questions**/
	public static LinkedList<String> answerList = new LinkedList<String>();

	/**
	 * Parses XML file.
	 * @return expression BinaryTree corresponding to file.
	 **/
	public static BinaryTree<String> readCommutativeExpr( String file )
	{
		return readCommutativeExpr( new File( file ) );
	}

	/**
	 * Parses XML file
	 * @return expression BinTree corresponding to file.
	 **/
	public static BinaryTree<String> readCommutativeExpr( File file )
	{
		DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

		try 
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse( file );

			return parseExprTree( document );	    

		} 
		catch (SAXException sxe) 
		{
			// Error generated during parsing)
			Exception  x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();         
		} 
		catch (ParserConfigurationException pce) 
		{
			// Parser with specified options can't be built
			pce.printStackTrace();
		}   
		catch (IOException ioe) 
		{
			// I/O error
			ioe.printStackTrace();
		}

		return null;
	}

	/**
	 * Parses XML Document. 
	 * @return parsed BinTree.
	 **/
	private static BinaryTree<String> parseExprTree( Document document )
	{
		BinaryTree<String> tree = new DefaultBinaryTree<String>();

		// parse root
		Element root = 
				(Element)document.getDocumentElement();

		tree.setRoot( parseQuestionNode( root ) );

		return tree;
	}

	/**
	 * Parses expr element.
	 * @return BinTreeNode represented by element.
	 **/
	private static BinaryTreeNode<String> parseQuestionNode( Element element )
	{
		if (!element.getTagName().equals("question")) {
			System.err.println("Error: expecting question tag");
		}

		String questionText=  element.getAttribute("text");

		BinaryTreeNode<String> questionNode = new DefaultBinaryTreeNode<String>();
		questionNode.setData(questionText);

		NodeList children = element.getChildNodes();
		for (int i = 0; i<children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				Element childNode = (Element)children.item(i);

				String user_response = childNode.getAttribute("user_answer");

				if (user_response.equals("yes")) {
					questionNode.setLeftChild(parseAnswerNode(childNode));
				}
				else if (user_response.equals("no")) {
					questionNode.setRightChild(parseAnswerNode(childNode));
				}
				else {
					System.err.println("Error in XML file: No valid answer");
				}
			}
		}
		return questionNode;
	}

	/**parses answer nodes and adds them to the tree**/
	private static BinaryTreeNode<String> parseAnswerNode(Element element) {
		if (!element.getTagName().equals("answer")) {
			System.err.println("Error: expecting answer tag");
		}

		NodeList children = element.getChildNodes();
		for (int i = 0; i<children.getLength(); i++) {
			if (children.item(i) instanceof Element) {

				Element child = (Element)children.item(i);

				if (child.getTagName().equals("thing")) {
					BinaryTreeNode<String> node = new DefaultBinaryTreeNode<String>();
					node.setData(child.getAttribute("value"));
					return node;
				}
				else if (child.getTagName().equals("question")) {
					return parseQuestionNode(child);
				}
				else {
					System.err.println("Error: child of answer should be a question");
				}
			}
		}
		return null;
	}

	/**look for nodes in the binary tree which are answers
	 * and add them to the answerList
	 * @param element
	 * @return
	 */
	public static LinkedList<String> updateAnswerList(Element element) {
		NodeList children = element.getChildNodes();
		for (int i = 0; i<children.getLength(); i++) {
			if (children.item(i) instanceof Element) {

				Element child = (Element)children.item(i);

				if (child.getTagName().equals("thing")) {
					LinkedListNode<String> node = new LinkedListNode<String>();
					//node.setData(child.getAttribute("value"));
					answerList.insertFirst(child.getAttribute("value"));
				}
			}
		}
		System.out.println("print answerList = "+answerList.toString());
		return answerList;
	}
	
	/**main method stores tree in GuessingGame and prints a preorder traversal of it**/
	public static void main (String[] args) {
		//XMLReader tree = new XMLReader("20Question.xml");
		
		//store tree in guessingGame
		guessingGame = readCommutativeExpr("20Questions_copy.xml");
		System.out.println("Preorder: "+ guessingGame.preorderTraversal());
	}		
}

