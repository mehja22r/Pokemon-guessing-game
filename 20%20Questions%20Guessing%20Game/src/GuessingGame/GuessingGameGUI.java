package GuessingGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import XML.XMLReader;
import UnrestrictedGuessingGame.UnrestrictedGuessingGameApplication;
import XML.XMLWriter;
import GameDataStructures.BinaryTree;
import GameDataStructures.BinaryTreeNode;
import GameDataStructures.LinkedList;
import GameDataStructures.LinkedListNode;


/**GuessingGameGUI launches 20 Questions game**/
public class GuessingGameGUI extends JPanel implements ActionListener {

	/**this panel shows the head of the tree on the JFrame**/
	JPanel panel; 

	/**JLabels that show list of answers to the user before starting the game**/
	JLabel legAnswers = new JLabel();
	JLabel othAnswers = new JLabel();

	/**panel that contains yes, no and play again buttons**/
	JPanel buttonPanel;

	//boolean b = false;

	/**this panel shows the head of the tree on the JFrame**/
	JPanel introQuestionPanel = new JPanel();

	/**firstPage is the starting page of the game that lists all the answers and has start playing button**/
	JPanel firstPage =  new JPanel();

	//XMLWriter writer = new XMLWriter();

	/**r stores the 20Questions tree from the XML reader**/
	BinaryTree<String> r = XMLReader.readCommutativeExpr("20Questions.xml");

	//LinkedList<String> l = XMLReader.answerList;

	/**currentNode starts from the root and traverses along the tree as user plays the game**/
	BinaryTreeNode<String> currentNode = r.getRoot();

	/**gameTree shows the text of the currentNode in the game**/
	JLabel gameTree =  new JLabel();

	/**constructor**/
	public GuessingGameGUI() {

		super();

		this.setLayout(new BorderLayout());

		//add firstPage to border layout
		add(start(), BorderLayout.CENTER);
	}

	/**creates yes, no and play again buttons
	 * returns buttonPanel to add to the main JPanel**/
	public JPanel createButtons() {

		JButton yes = new JButton("yes");

		JButton no = new JButton("no");

		JButton playAgain = new JButton("play again!");

		buttonPanel = new JPanel();

		buttonPanel.add(yes);

		//when clicked, yes button takes currentNode to the left child, and stops game if currentNode is Leaf
		yes.addActionListener(

				new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						//if it's the last node on the tree
						if (currentNode.isLeaf()) {

							//remove the panel text
							remove(panel);

							//call method to show computer guessed right
							guessedRight();

							revalidate(); 

							//disable yes and no buttons because game is over
							yes.setEnabled(false);

							no.setEnabled(false);
						}

						else {

							//user moved past the first question on the tree
							introQuestionPanel.setVisible(false);

							//get rid of text panel
							remove(panel);

							//call method to display next node text
							textOverImage(questions(currentNode.getLeftChild()));

							revalidate();

							repaint();

							//set currentNode to left child
							currentNode=currentNode.getLeftChild();
						}
					}	
				});

		//no button sets currentNode to right child
		no.addActionListener(

				new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						//if currentNode has no children
						if (currentNode.isLeaf()) {

							//remove tree text from screen
							remove(panel);

							//call method to display guessed wrong
							guessedWrong();

							revalidate();

							//disable yes and no buttons because game is over
							yes.setEnabled(false);

							no.setEnabled(false);
						}
						else {

							//make first question panel disappear
							introQuestionPanel.setVisible(false);

							//remove game text from screen
							remove(panel);

							//display right child text
							textOverImage(questions(currentNode.getRightChild()));

							revalidate();

							repaint();

							//update currentNode to its right child
							currentNode=currentNode.getRightChild();
						}
					}	
				});

		buttonPanel.add(no);

		/**play again button sets the currentNode back to root**/
		playAgain.addActionListener(

				new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						//reset method makes the first page reappear
						reset();

						//currentNode is set back to root
						currentNode = r.getRoot();

						//remove panels with yes no etc. buttons so there is o overlap of JPanels
						remove(buttonPanel);

						//remove panel with gameTree text
						remove(panel);

						revalidate();

						repaint();
					}
				});

		buttonPanel.add(playAgain);

		return buttonPanel;
	}

	/**questions get the data from the node passed in as parameter and sets it as text to the gameTree JLabel**/
	public JLabel questions(BinaryTreeNode<String> node) {

		//string to store data from node
		String gameText = "";

		//if the node passed in was a answer
		if (node.isLeaf()) {

			//show this text
			gameTree.setText("Were you thinking of "+ node.getData().toString() + "?");

			return gameTree;
		}

		//if the node was another question
		else {

			//set gameText to be the node data
			gameText  = node.getData().toString();

			//set gameText to JLabel, wrap text so it doesn't exceed frame
			gameTree.setText("<html>"+gameText+"</html>");

			return gameTree;
		}
	}

	/**main method creates a JFrame and intitiates the game**/
	public static void main (String[] args) {

		JFrame frame = new JFrame("Who's that Pokemon?");

		frame.add(new GuessingGameGUI());

		frame.setSize(500,600);

		frame.setVisible(true);

		frame.setFocusable(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**opening page in game that lists answers
	 * has a button called start playing that begins the game**/
	public JPanel start() {

		//create a background image for firstPage
		ImageIcon image = new ImageIcon("background.jpg");

		//this JLabel sets the background image
		JLabel background = new JLabel("", image, JLabel.LEADING);

		firstPage.setLayout(new BorderLayout());

		//get string of other answer list
		String p = "<html>"+ "Cindaquil"+ "<br/>" + "Totodile" + "<br/>" +"Chikorita" + "<br/>" +"Pikachu" + "<br/>" +"Lucario" + "<br/>" +"Umbreon" + "<br/>" +"Glaceon" + "<br/>" +"Leafeon" + "</html>";

		String l = "<html>" + "Mewtwo" + "<br/>" + "Deoxys" + "<br/>"+"Cressilia" + "<br/>"+"Lugia" +"<br/>"+ "Xerneas" + "<br/>"+"Ho-oh" + "<br/>"+"Moltres" +"<br/>"+ "Articuno" ;

		//set answer list text to JLabels 
		legAnswers.setText(l);
		othAnswers.setText(p);

		//set color, font etc.
		legAnswers.setFont(new Font ("Calibri", Font.BOLD, 20));

		othAnswers.setFont(new Font ("Calibri", Font.BOLD, 20));

		legAnswers.setForeground(Color.PINK);

		othAnswers.setForeground(Color.BLUE);

		//add the Jlabels with answer list to the firstPage
		firstPage.add(legAnswers, BorderLayout.EAST);

		firstPage.add(othAnswers, BorderLayout.WEST);

		//create start button and add action listener
		JButton play = new JButton("Start Playing!");

		play.addActionListener(this);

		firstPage.add(play, BorderLayout.SOUTH);

		//add background image to first page and make it visible
		firstPage.add(background, BorderLayout.NORTH);

		firstPage.setOpaque(true);

		firstPage.setFocusable(true);

		return firstPage;

	}

	/**listener for start playing button**/
	@Override
	public void actionPerformed(ActionEvent e) {

		//set border layout to class JPanel
		this.setLayout(new BorderLayout());

		//make first page invisible
		firstPage.setVisible(false);

		//add buttons south
		this.add(createButtons(), BorderLayout.SOUTH);

		//show the first question
		this.add(introQuestion(questions(currentNode)), BorderLayout.CENTER);

	}


	/**restart the game**/
	public void reset() {

		//make firstPage reappear
		firstPage.setVisible(true);

	}

	/**class shows the data of the root in the JFrame**/
	public JPanel introQuestion(JLabel gameTree) {

		//initialize panel
		panel = new JPanel();

		//add an image to a new JLabel
		JLabel pic =  new JLabel(new ImageIcon("yetAnotherPicturejpg.jpg"));

		pic.setLayout(new BorderLayout());

		//set font, color to text in JLabel
		gameTree.setFont(gameTree.getFont().deriveFont(Font.BOLD, 30));

		gameTree.setForeground(Color.BLACK);

		gameTree.setHorizontalAlignment(JLabel.CENTER);

		//add JLabel with node data to image JLabel
		pic.add(gameTree);

		//add image JLabel to panel
		panel.add(pic);

		panel.setVisible(true);

		//add panel to the class's JPanel
		this.add(panel, BorderLayout.CENTER);

		return panel;
	}

	/**random loop to select picture and display text over it**/
	public void textOverImage(JLabel gameTree) {

		//initialize panel
		panel = new JPanel();

		panel.setLayout(new GridLayout());

		//get a random int to select a picture
		double random = Math.random()*2;

		int randomInt = (int) Math.round(random);

		//create a JLabel with an image and the JLabel with text to it's center
		switch (randomInt) {

		case 0: JLabel pic = new JLabel(new ImageIcon("manyPokemon.jpg"));

		pic.setLayout(new BorderLayout());

		gameTree.setFont(gameTree.getFont().deriveFont(Font.BOLD, 30));

		gameTree.setForeground(Color.WHITE);

		gameTree.setHorizontalAlignment(JLabel.CENTER);

		pic.add(gameTree);

		panel.add(pic);

		break;

		//create a JLabel with an image and the JLabel with text to it's center
		case 1: JLabel pic1 = new JLabel(new ImageIcon("officialPokemon.jpg"));

		pic1.setLayout(new BorderLayout());

		gameTree.setFont(gameTree.getFont().deriveFont(Font.BOLD, 30));

		gameTree.setForeground(Color.WHITE);

		gameTree.setHorizontalAlignment(JLabel.CENTER);

		pic1.add(gameTree);

		panel.add(pic1);

		break;

		//create a JLabel with an image and the JLabel with text to it's center
		case 2: JLabel pic2 = new JLabel(new ImageIcon("officialPokemon1.png"));

		pic2.setLayout(new BorderLayout());

		gameTree.setFont(gameTree.getFont().deriveFont(Font.BOLD, 30));

		gameTree.setForeground(Color.WHITE);

		gameTree.setHorizontalAlignment(JLabel.CENTER);

		pic2.add(gameTree);

		panel.add(pic2);

		break;
		}

		//make panel visible
		setVisible(true);

		//add panel to the class JPanel
		this.add(panel, BorderLayout.CENTER);

	}

	/**method is called when computer guesses correctly**/
	public void guessedRight() {

		JLabel right = new JLabel("Yay! I guessed correctly!");

		//create a JLabel with image
		JLabel correctGuess = new JLabel(new ImageIcon("happyPikachu.jpg"));

		right.setFont(gameTree.getFont().deriveFont(Font.ITALIC, 30));

		right.setForeground(Color.GREEN);

		right.setHorizontalAlignment(JLabel.CENTER);

		//initialize panel
		panel = new JPanel();

		panel.setLayout(new BorderLayout());

		panel.add(correctGuess, BorderLayout.NORTH);

		panel.add(right, BorderLayout.CENTER);

		panel.setVisible(true);

		//add panel to the class's JPanel
		this.add(panel, BorderLayout.CENTER);
	}

	/**method is called when computer guesses wrong**/
	public void guessedWrong() {

		//create JLabel with text, set font, color etc.
		JLabel wrong = new JLabel("Oh no! I was wrong :(");

		wrong.setFont(gameTree.getFont().deriveFont(Font.ITALIC, 30));

		wrong.setForeground(Color.BLUE);

		wrong.setHorizontalAlignment(JLabel.CENTER);

		//create JLabel with picture
		JLabel wrongGuess = new JLabel(new ImageIcon("sadPikachu.gif"));

		//initialize panel
		panel = new JPanel();

		panel.setLayout(new BorderLayout());

		panel.add(wrongGuess, BorderLayout.NORTH);

		panel.add(wrong, BorderLayout.CENTER);

		//add panel to the class's JPanel
		this.add(panel, BorderLayout.CENTER);

	}

}

