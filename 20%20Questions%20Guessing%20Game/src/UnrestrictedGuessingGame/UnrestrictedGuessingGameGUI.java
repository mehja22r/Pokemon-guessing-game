package UnrestrictedGuessingGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameDataStructures.BinaryTree;
import GameDataStructures.BinaryTreeNode;
import GameDataStructures.DefaultBinaryTreeNode;
import GameDataStructures.LinkedList;
import XML.XMLReader;

/**UnrestrictedGameGUI creates the framework of the unrestricted guessing game
 * it takes input from user to add new questions and answers to the tree
 * Had assistance with ImageIcon class from StackOverFlow
 * @author mehja22r
 *
 */
public class UnrestrictedGuessingGameGUI extends JPanel implements ActionListener {

	/**isAnswerValid checks if the user entered a question and a yes/no option with their answer**/
	boolean isAnswerValid = false;
	
	/**this panel shows the head of the tree on the JFrame**/
	JPanel introQuestionPanel = new JPanel();

	/**panel shows all other nodes of the tree on the JFrame**/
	JPanel panel;

	/**JLabels that show list of answers to the user before starting the game**/
	JLabel legAnswers = new JLabel();
	JLabel othAnswers = new JLabel();

	/**this JLabel shows the list of answers user entered**/
	JLabel inputAnswers;

	/**userAnswer is the string that stores user inputed answer and displays it on the front page on a JLabel**/
	String userAnswer = "";


	/**JButtons that control yes, no answers to questions and restart option**/
	JButton yes; 
	JButton no;
	JButton playAgain; 

	/**panel that contains yes, no and play again buttons**/
	JPanel buttonPanel;


	/**firstPage is the starting page of the game that lists all the answers and has start playing button**/
	JPanel firstPage =  new JPanel();


	/**r stores the 20Questions tree from the XML reader**/
	BinaryTree<String> r = XMLReader.readCommutativeExpr("20Questions_copy.xml");


	/**answer and question get user input and implement it to the tree**/
	String answer = "";
	String question = "";
	String option = "";

	/**currentNode starts from the root and traverses along the tree as user plays the game**/
	BinaryTreeNode<String> currentNode = r.getRoot();

	/**gameTree shows the text of the currentNode in the game**/
	JLabel gameTree =  new JLabel();

	/**constructor**/
	public UnrestrictedGuessingGameGUI() {

		super();

		//setting BorderLayout
		this.setLayout(new BorderLayout());

		//adding the firstPage
		add(start(), BorderLayout.CENTER);
	}

	/**creates yes, no and play again buttons
	 * returns buttonPanel to add to the main JPanel**/
	public JPanel createButtons() {

		//intializing buttons and button panel
		yes = new JButton("yes");

		no = new JButton("no");

		playAgain = new JButton("Play Again");

		buttonPanel = new JPanel();

		buttonPanel.add(playAgain);

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

		buttonPanel.add(no);

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

							//get what user guessed instead
							enterUserInput();
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
			gameTree.setText("<html>" + gameText + "</html>");

			return gameTree;
		}
	}


	/**pop up after user selects no to computer's guess**/
	public void enterUserInput() {

		//pop JOptionPane to get user input
		userEntryPanels();

		//update guessing game tree
		updateGuessingGame();
	}

	/**get answer and take out the current node of tree
	 * replace currentNode with question
	 * set LeftChild and RightChild new values**/
	public void updateGuessingGame() {

		//if the answer, question and yes/no option entered by user was not null
		if (answer != null && question!= null && option != null) {

			//if user didn't enter text for either of the entries
			if (answer.length() == 0 || question.length() == 0 || option.length() == 0) {
				JOptionPane.showMessageDialog(null, "You didn't fill in the inputs correctly so your entries weren't considered", "Info Needed", JOptionPane.ERROR_MESSAGE);
			}

			//if user did fill in entries with something
			else {

				//create two temp nodes
				BinaryTreeNode<String> tempAnswer = new DefaultBinaryTreeNode<String>();

				BinaryTreeNode<String> tempCurrentNode = new DefaultBinaryTreeNode<String>();

				//set the data in tempAnswer to be the answer entered by user
				tempAnswer.setData(answer);

				//store last answer in the tree
				String tempo = currentNode.getData();

				//set the data of currentNode to tempCurrentNode
				tempCurrentNode.setData(tempo);

				//if option is yes
				if (option.equals("yes") || option.equals("Yes")) {

					//set currentNode data to user entered question
					currentNode.setData(question);

					//set the left child to be user entered answer
					currentNode.setLeftChild(tempAnswer);

					//set old answer to be the right child
					currentNode.setRightChild(tempCurrentNode);
					
					//answer is valid because user entered properly
					isAnswerValid = true;
				}

				//if option is no
				else if (option.equals("no") || option.equals("No")){

					//set currentNode to be the user input question
					currentNode.setData(question);

					//set right child to be user entered answer
					currentNode.setRightChild(tempAnswer);

					//right child becomes old answer
					currentNode.setLeftChild(tempCurrentNode);
					
					//answer is valid because user entered properly
					isAnswerValid = true;
				}

				//is user didn't enter yes or no
				else {
					JOptionPane.showMessageDialog(null, "You didn't put in yes or no so your entries weren't considered", "Info Needed", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		//if user didn't fill in the answer, question or yes/no option box
		else {
			JOptionPane.showMessageDialog(null, "You didn't fill in the inputs correctly so your entries weren't considered", "Info Needed", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**restart the game**/
	public void reset() {

		//make firstPage reappear and refresh entries to accomodate for user entered answers
		start();

		firstPage.setVisible(true);

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

	/**opening page in game that lists answers
	 * has a button called start playing that begins the game**/
	public JPanel start() {

		//create a background image for firstPage
		ImageIcon image = new ImageIcon("background.jpg");

		//this JLabel sets the background image
		JLabel background = new JLabel("", image, JLabel.LEADING);

		//initialize inputAnswers which shows the user entered answer list
		inputAnswers =  new JLabel();

		firstPage.setLayout(new BorderLayout());

		//if user entered answer is not null
		if (answer != null && isAnswerValid == true) {

			//add the answer to userAnswer
			userAnswer += " " + answer;

			//set answer to JLabel
			inputAnswers.setText(userAnswer);

			//reset answer
			answer = "";
			
			isAnswerValid = false;
		}

		//get string of other answer list
		String p = "<html>"+ "Cindaquil"+ "<br/>" + "Totodile" + "<br/>" +"Chikorita" + "<br/>" +"Pikachu" + "<br/>" +"Lucario" + "<br/>" +"Umbreon" + "<br/>" +"Glaceon" + "<br/>" +"Leafeon" + "</html>";

		String l = "<html>" + "Mewtwo" + "<br/>" + "Deoxys" + "<br/>"+"Cressilia" + "<br/>"+"Lugia" +"<br/>"+ "Xerneas" + "<br/>"+"Ho-oh" + "<br/>"+"Moltres" +"<br/>"+ "Articuno" ;

		//set answer list text to JLabels 
		legAnswers.setText(l);
		othAnswers.setText(p);

		//set color, font etc.
		inputAnswers.setVerticalAlignment(JLabel.TOP);

		legAnswers.setFont(new Font ("Calibri", Font.BOLD, 20));

		othAnswers.setFont(new Font ("Calibri", Font.BOLD, 20));

		inputAnswers.setFont(new Font ("Calibri", Font.BOLD, 20));

		legAnswers.setForeground(Color.PINK);

		othAnswers.setForeground(Color.BLUE);

		inputAnswers.setForeground(Color.ORANGE);

		//add the Jlabels with answer list to the firstPage
		firstPage.add(legAnswers, BorderLayout.EAST);

		firstPage.add(othAnswers, BorderLayout.WEST);

		firstPage.add(inputAnswers, BorderLayout.CENTER);

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

	/**ask user what Pokemon, question and yes/no they were thinking of**/
	public void userEntryPanels() {
		askAnswer();
		askQuestion();
		askYesOrNo();
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

		case 1: JLabel pic1 = new JLabel(new ImageIcon("officialPokemon.jpg"));

		pic1.setLayout(new BorderLayout());

		gameTree.setFont(gameTree.getFont().deriveFont(Font.BOLD, 30));

		gameTree.setForeground(Color.WHITE);

		gameTree.setHorizontalAlignment(JLabel.CENTER);

		pic1.add(gameTree);

		panel.add(pic1);

		break;

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

	/**asks user what Pokemon they were thinking of if they selected no to computer's answer**/
	public void askAnswer() {
		answer = JOptionPane.showInputDialog(null, "Which Pokemon were you thinking about?", "Info Needed", JOptionPane.QUESTION_MESSAGE);
	}

	/**ask the user to input a question**/
	public void askQuestion() {
		question = JOptionPane.showInputDialog(null, "Ask a yes/no question for this Pokemon", "Info Needed", JOptionPane.QUESTION_MESSAGE);
	}

	/**user needs to type in yes or no with their answer and question**/
	public void askYesOrNo() {
		option = JOptionPane.showInputDialog(null, "Is the answer to this question yes or no for the Pokemon?", "Info Needed", JOptionPane.QUESTION_MESSAGE);
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