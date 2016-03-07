package Filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class CreateDirectories extends Filter {

	ConcurrentHashMap<String, String> hm;

	public CreateDirectories() {
		this.hm = new ConcurrentHashMap<String, String>();
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " started");
		while (true) {
			try {
				Pause();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Failed to pause");
			}
			
			if(stop)
			{
				System.out.println(Thread.currentThread().getName() + " Stopped");
				break;
			}
			String data;
			String[] url;
			try {
				data = read();
			} catch (Exception e) {
				write(null);
				System.out.println("CreateDirectories Filter Closed");
				break;
			}

			url = data.split(" ");
			try {
				String currUrl = url[0];
				int currLayer = Integer.parseInt(url[1]);
				String parentUrl;
				if (url.length < 3) {
					parentUrl = "";
				} else {
					parentUrl = url[2];
				}
				String currFolder = getFolderName(currUrl) + "-" + currLayer;
				String parentFolder = getFolderName(parentUrl);

				hm.put(currFolder, currUrl + " " + parentFolder);

				String fullPath = getFullPath(currFolder);
				makeDirectory(fullPath, currUrl);
				write(currLayer + " " + currUrl);
			} catch (Exception ex) {
			}
		}
	}

	private String getFolderName(String url) {
		try {
			String[] path = url.split("/");
			String folderName = path[path.length - 1];
			return folderName;
		} catch (Exception ex) {
			return "";
		}
	}

	private void makeDirectory(String fullPath, String url) {

		try {
			File file = new File(fullPath);

			file.mkdirs();

			Thread.sleep(500);
			URL page = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));

			try (Writer writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file + "/index.html"), "utf-8"))) {

				String inputLine;
				while ((inputLine = in.readLine()) != null) {

					writer.write(inputLine);
					writer.write(System.lineSeparator());
				}
				in.close();

				writer.close();
			} catch (Exception ex) {

				//System.err.println(ex.toString());
			}
		} catch (Exception ex) {
			System.out.println("Error creating directory");
		}

	}

	private String getFullPath(String currFolder) {

		try {
			String currentFolder = hm.get(currFolder);
			String[] folderInfo = currentFolder.split(" ");
			if (folderInfo.length < 2) {
				return currFolder;
			}
			String url = folderInfo[0];
			String parent = folderInfo[1];

			return getFullPath(parent) + "/" + currFolder;

		} catch (Exception ex) {
			return currFolder;
		}
	}

}
