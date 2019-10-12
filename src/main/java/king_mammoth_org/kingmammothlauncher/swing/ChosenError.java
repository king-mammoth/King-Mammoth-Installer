package king_mammoth_org.kingmammothlauncher.swing;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChosenError {

	/**
	 * Create the application.
	 */
	public ChosenError() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JOptionPane.showMessageDialog(new JFrame(), "Please select a modpack version and a directory to install the modpack in.");
	}

}
