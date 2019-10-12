package king_mammoth_org.kingmammothlauncher.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Desktop;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Color;
import java.awt.Toolkit;

public class DownloadModpack {

	int currentSelection = -1;

	private JFrame frmDownloadKingMammoth;
	private JTextField textField;

	File downloadDirectory;
	File fileToDownload;

	Release[] releases;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadModpack window = new DownloadModpack();
					window.frmDownloadKingMammoth.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DownloadModpack() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDownloadKingMammoth = new JFrame();
		frmDownloadKingMammoth.setIconImage(Toolkit.getDefaultToolkit().getImage(DownloadModpack.class.getResource("/king_mammoth_org/kingmammothlauncher/swing/launcher.png")));
		frmDownloadKingMammoth.setTitle("Download King Mammoth");
		frmDownloadKingMammoth.setBounds(100, 100, 450, 300);
		frmDownloadKingMammoth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDownloadKingMammoth.getContentPane().setLayout(null);
		frmDownloadKingMammoth.setResizable(false);

		JLabel lblNewLabel = new JLabel("Download King Mammoth");
		lblNewLabel.setBounds(0, 5, 434, 41);
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmDownloadKingMammoth.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(
				"<html> Please select your version to download, a download directory, and press Ok.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 50, 414, 53);
		frmDownloadKingMammoth.getContentPane().add(lblNewLabel_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 50, 414, 2);
		frmDownloadKingMammoth.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 102, 414, 2);
		frmDownloadKingMammoth.getContentPane().add(separator_1);

		JLabel lblNewLabel_2 = new JLabel("Directory: ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(10, 114, 56, 14);
		frmDownloadKingMammoth.getContentPane().add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(70, 111, 255, 20);
		frmDownloadKingMammoth.getContentPane().add(textField);
		textField.setColumns(10);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 139, 414, 2);
		frmDownloadKingMammoth.getContentPane().add(separator_2);

		DefaultListModel<String> model = new DefaultListModel<String>();

		File releaseFile = null;

		try {
			releaseFile = Release.getReleaseFile();
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		try {
			releases = Release.getReleases(releaseFile);

			for (int i = 0; i < releases.length; i++) {

				System.out.println(releases[i].name);
				System.out.println(releases[i].date);

				model.addElement(releases[i].name + "     | " + releases[i].date);

			}

		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		final JList<String> list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setToolTipText("Choose Modpack Version");
		list.setBounds(10, 150, 414, 23);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 147, 414, 26);
		frmDownloadKingMammoth.getContentPane().add(scrollPane);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 184, 414, 2);
		frmDownloadKingMammoth.getContentPane().add(separator_3);

		JButton btnNewButton = new JButton("Download");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				if (list.isSelectionEmpty() || textField.getText().isEmpty()) {

					new ChosenError();

				}

				else {

					currentSelection = list.getSelectedIndex();

					Release chosenFile = releases[currentSelection];

					frmDownloadKingMammoth.setVisible(false);
					EnchancedProgressBar.dir = textField.getText();
					EnchancedProgressBar.url = chosenFile.url;

					System.out.println("Loaded File");

					try {
						EnchancedProgressBar bar = new EnchancedProgressBar();
						bar.start();
						bar.backgroundTask();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(10, 203, 131, 47);
		frmDownloadKingMammoth.getContentPane().add(btnNewButton);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setFocusable(false);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				frmDownloadKingMammoth.setVisible(false);
				frmDownloadKingMammoth.dispose();
				System.exit(0);
			}
		});
		btnCancel.setBounds(293, 203, 131, 47);
		frmDownloadKingMammoth.getContentPane().add(btnCancel);

		JButton btnSupportUs = new JButton("  Support Us");
		btnSupportUs.setBackground(Color.LIGHT_GRAY);
		btnSupportUs.setFocusable(false);
		btnSupportUs.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSupportUs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				String url = "https://www.patreon.com/kingmammoth";

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException | URISyntaxException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} else {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSupportUs.setIcon(new ImageIcon(
				DownloadModpack.class.getResource("/king_mammoth_org/kingmammothlauncher/swing/patreon.png")));
		btnSupportUs.setBounds(151, 203, 131, 47);
		frmDownloadKingMammoth.getContentPane().add(btnSupportUs);

		JButton btnNewButton_1 = new JButton("Choose File");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choose Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					downloadDirectory = chooser.getSelectedFile();
					textField.setText(downloadDirectory.getAbsolutePath());

				} else {
					System.out.println("No Selection");
				}
			}
		});
		btnNewButton_1.setBounds(335, 109, 89, 23);
		frmDownloadKingMammoth.getContentPane().add(btnNewButton_1);
	}
}
