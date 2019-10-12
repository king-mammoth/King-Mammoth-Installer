package king_mammoth_org.kingmammothlauncher.swing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Release {

	String name;
	String url;
	String date;

	public Release(String name, String url, String date) {

		this.name = name;
		this.url = url;
		this.date = date;

	}

	public static Release[] getReleases(File file) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
		Release[] releases = new Release[lines.size()];

		for (int i = 0; i < releases.length; i++) {

			String[] data = lines.get(i).split(",");

			Release release = new Release(data[0], data[1], data[2]);
			releases[i] = release;

		}

		return releases;

	}

	public static File getReleaseFile() throws IOException {

		String cwd = System.getProperty("user.dir");

		URL website = new URL("http://king-mammoth.org//releases//releases.txt");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(cwd + "/releases.txt");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();

		return new File(cwd + "/releases.txt");

	}

}
