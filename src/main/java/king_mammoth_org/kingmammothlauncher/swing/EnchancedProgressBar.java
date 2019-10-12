package king_mammoth_org.kingmammothlauncher.swing;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import java.awt.Toolkit;

public class EnchancedProgressBar {

	public static JFrame frmDownloadModpack;
	public static JProgressBar progressBar;
	public static JLabel lblNewLabel_2;

	public static String url;
	public static String dir;
	
	public static long fileSize;
	public static long bytesDownloaded;
	public static int bytesRead = -1;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */

	public void start() {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				frmDownloadModpack.setVisible(true);

			}
		});

	}

	public EnchancedProgressBar() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */

	static void initialize() throws IOException {
		frmDownloadModpack = new JFrame();
		frmDownloadModpack.setIconImage(Toolkit.getDefaultToolkit().getImage(EnchancedProgressBar.class.getResource("/king_mammoth_org/kingmammothlauncher/swing/download.png")));
		frmDownloadModpack.setTitle("Download Modpack");
		frmDownloadModpack.setBounds(100, 100, 450, 300);
		frmDownloadModpack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDownloadModpack.getContentPane().setLayout(null);
		frmDownloadModpack.setResizable(false);

		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setBounds(43, 175, 350, 50);
		frmDownloadModpack.getContentPane().add(progressBar);

		JLabel lblNewLabel = new JLabel("Downloading modpack, please wait.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(0, 24, 434, 32);
		frmDownloadModpack.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html> Please wait as we download the modpack and extract all files in the directory.");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(34, 64, 390, 50);
		frmDownloadModpack.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(43, 150, 350, 14);
		frmDownloadModpack.getContentPane().add(lblNewLabel_2);

	}

	public void backgroundTask() {

		SwingWorker<Integer, String> sw1 = new SwingWorker<Integer, String>() {

			@Override
			protected Integer doInBackground() throws Exception {
				
				URL website = new URL(url);
				
				lblNewLabel_2.setText("Downloading Required Modpack Files");
				
				HttpURLConnection conn = (HttpURLConnection) website.openConnection();
				conn.setRequestMethod("HEAD");
				long fileSize = conn.getContentLengthLong();
				
				EnchancedProgressBar.fileSize = fileSize;
				progressBar.setMaximum((int)fileSize);
				
				HTTPDownloadUtil util = new HTTPDownloadUtil();
	            util.downloadFile(url);
				
				FileOutputStream fos = new FileOutputStream(dir + "/modpack.zip");
				InputStream inputStream = util.getInputStream();
				
				byte[] buffer = new byte[8096];

	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                fos.write(buffer, 0, bytesRead);
	                bytesDownloaded += bytesRead;
	                progressBar.setValue((int)bytesDownloaded);
	                
	            }
	             
				fos.close();
				
				return 1;
				
			}

			@Override
			protected void done() {
				
				lblNewLabel_2.setText("Extracting Zip File");
				
				ZipFile zipFile = new ZipFile(dir + "/modpack.zip");
				try {
					zipFile.extractAll(dir);
				} catch (ZipException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				frmDownloadModpack.setVisible(false);
				new Installed();
				
			}
		};

		sw1.execute();

	}
}
