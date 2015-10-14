import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class PackageList {

	public PackageList() {
		listStorage = "/var/lib/apt/lists";
		listDirectory = new File(URI.create("file:" + listStorage));
	}
	
	public PackageList(String listLocation) {
		listStorage = listLocation;
		listDirectory = new File(URI.create("file:" + listStorage));
	}
	
	public void getListFiles() {
		String[] listString = listDirectory.list();
		listFiles = new ArrayList();
		try {
			for (int i=0; i<listString.length; i++) {
				if (!(listString[i].equals("partial")) && !(listString[i].equals("lock"))) {
					listFiles.add(new File(listStorage + "/" + listString[i]));
				}
			}
		}
		catch (Exception e) {
			System.out.println("getListFiles: Error");
		}
	}
	
	public void combineContents() {
		try {
			fullList = File.createTempFile("service-pack-temp-", "");
			fullList.deleteOnExit();
		}
		catch (IOException e) {
			System.out.println("concatenateContents: Error. Could not create temporary file.");
			System.exit(0);
		}
		BufferedReader fileParser;
		BufferedWriter fileOutput;
		StringBuffer totalContents = new StringBuffer();
		boolean check = true;
		File tempFile;
		try {
			fileOutput = new BufferedWriter(new FileWriter(fullList));
			for (int i=0; i<listFiles.size(); i++) {
				tempFile = (File) listFiles.get(i);
				fileParser = new BufferedReader(new FileReader(tempFile));	
				while (fileParser.ready()) {
					fileOutput.write(fileParser.readLine());
					fileOutput.newLine();
				}
				fileParser.close();
			}
		}
		catch (Exception e) {
			System.out.println("combineContents: Error " + e);
			System.exit(0);
		}
	}
	
	public boolean[] checkPackagesExist(Package[] givenPackages) {
		boolean[] existOrNot = new boolean[givenPackages.length];
		String currentName;
		BufferedReader fileParser;
		try {
			for (int i=0; i<givenPackages.length; i++) {
				fileParser = new BufferedReader(new FileReader(fullList));
				existOrNot[i] = false;
				currentName = givenPackages[i].getName();
				while (fileParser.ready() && !(existOrNot[i])) {
					if (fileParser.readLine().contains("Package: " + currentName)) {
						existOrNot[i] = true;
					}
				}
				fileParser.close();
			}
		}
		catch (Exception e) {
			System.out.println("checkPackagesExist: Error " + e);
			System.exit(0);
		}
		return existOrNot;
	}
	
	public void printFullList() {
		try {
			BufferedReader fileParser = new BufferedReader(new FileReader(fullList));
			String nextLine;
			do {
				nextLine = fileParser.readLine();
				System.out.println(nextLine);
			}
			while (nextLine != null);
		}
		catch (Exception e) {}
	}
	
	private String listStorage;
	private File listDirectory;
	private ArrayList listFiles;
	private File fullList;
}
