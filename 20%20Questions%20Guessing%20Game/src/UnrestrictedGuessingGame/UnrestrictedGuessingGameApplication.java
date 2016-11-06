package UnrestrictedGuessingGame;

import javax.swing.JFrame;


/**class executes the program**/
public class UnrestrictedGuessingGameApplication {
	
	/**creates JFrame to run the application
	 * @param args
	 */
	public static void main (String[] args) {

		JFrame frame = new JFrame("Who's that Pokemon?");

		frame.add(new UnrestrictedGuessingGameGUI());

		frame.setSize(500,600);

		frame.setVisible(true);

		frame.setFocusable(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
