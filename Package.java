import java.util.*;
import java.io.*;

public class Package {

	public Package(String nameArg) {
		name = nameArg;
	}

	public String getName() {
		return name;
	}

	public void setVersion(String versionArg) {
		version = versionArg;
	}

	public String getVersion() {
		return version;
	}

	public void addDepends(Package toDepend) {
		boolean add = true;
		Package tempPackage;
		for (int i=0; i<depends.size(); i++) {
			tempPackage = (Package) depends.get(i);
			if (tempPackage.getName().equals(toDepend.getName())) {
				add = false;
			}
		}
		if (add) {
			depends.add(toDepend);
		}
	}
	
	public void addDepends(Package[] toDepend) {
		boolean add;
		Package tempPackage;
		for (int j=0; j<toDepend.length; j++) {
			add = true;
			for (int i=0; i<depends.size(); i++) {
				tempPackage = (Package) depends.get(i);
				if (tempPackage.getName().equals(toDepend[j].getName())) {
					add = false;
				}
			}
			if (add) {
				depends.add(toDepend[j]);
			}
		}
	}

	public void addDepends(ArrayList toDepend) {
		boolean add;
		Package tempExistingPackage;
		Package tempGivenPackage;
		String currentGivenName;
		String currentExistingName;
		for (int j=0; j<toDepend.size(); j++) {
			add = true;
			tempGivenPackage = (Package) toDepend.get(j);
			currentGivenName = tempGivenPackage.getName();
			for (int i=0; i<depends.size(); i++) {
				tempExistingPackage = (Package) depends.get(i);
				currentExistingName = tempExistingPackage.getName();
				if (currentExistingName.equals(currentGivenName)) {
					add = false;
				}
			}
			if (add) {
				depends.add(((Package) toDepend.get(j)));
			}
		}
	}

	public void addRecommends(Package toRecommend) {
		boolean add = true;
		Package tempPackage;
		for (int i=0; i<recommends.size(); i++) {
			tempPackage = (Package) recommends.get(i);
			if (tempPackage.getName().equals(toRecommend.getName())) {
				add = false;
			}
		}
		if (add) {
			recommends.add(toRecommend);
		}
	}

	public void addRecommends(Package[] toRecommend) {
		boolean add;
		Package tempPackage;
		for (int j=0; j<toRecommend.length; j++) {
			add = true;
			for (int i=0; i<recommends.size(); i++) {
				tempPackage = (Package) recommends.get(i);
				if (tempPackage.getName().equals(toRecommend[j].getName())) {
					add = false;
				}
			}
			if (add) {
				recommends.add(toRecommend[j]);
			}
		}
	}

	public void addSuggests(Package toSuggest) {
		boolean add = true;
		Package tempPackage;
		for (int i=0; i<suggests.size(); i++) {
			tempPackage = (Package) suggests.get(i);
			if (tempPackage.getName().equals(toSuggest.getName())) {
				add = false;
			}
		}
		if (add) {
			suggests.add(toSuggest);
		}
	}

	public void addSuggests(Package[] toSuggest) {
		boolean add;
		Package tempPackage;
		for (int j=0; j<toSuggest.length; j++) {
			add = true;
			for (int i=0; i<suggests.size(); i++) {
				tempPackage = (Package) suggests.get(i);
				if (tempPackage.getName().equals(toSuggest[j].getName())) {
					add = false;
				}
			}
			if (add) {
				suggests.add(toSuggest[j]);
			}
		}
	}

	private File makeControlFile() {
		String packageLine = "Package: " + name;
		String versionLine = "Version: " + 1;
		String sectionLine = "Section: service-packs";
		String priorityLine = "Priority: optional";
		String architectureLine = "Architecture: amd64";
		String dependsLine = "Depends:";
		if (depends.size() > 0) {
			Package currentPackage = (Package) depends.get(0);
			dependsLine = dependsLine.concat(" " + currentPackage.getName());
			if (depends.size() > 1) {
				for (int i=1; i<(depends.size() - 1); i++) {
					currentPackage = (Package) depends.get(i);
					dependsLine = dependsLine.concat(", " + currentPackage.getName());
				}
			}
		}
		String recommendsLine = "Recommends:";
		if (recommends.size() > 0) {
			Package currentPackage = (Package) recommends.get(0);
			recommendsLine = recommendsLine.concat(" " + currentPackage.getName());
			if (recommends.size() > 1) {
				for (int i=1; i<(recommends.size() - 1); i++) {
					currentPackage = (Package) recommends.get(i);
					recommendsLine = recommendsLine.concat(", " + currentPackage.getName());
				}
			}
		}
		String suggestsLine = "Suggests:";
		if (suggests.size() > 0) {
			Package currentPackage = (Package) suggests.get(0);
			suggestsLine = suggestsLine.concat(" " + currentPackage.getName());
			if (suggests.size() > 1) {
				for (int i=1; i<(suggests.size() - 1); i++) {
					currentPackage = (Package) suggests.get(i);
					suggestsLine = suggestsLine.concat(", " + currentPackage.getName());
				}
			}
		}
		String sizeLine = "Installed-size: 1";
		String maintainerLine = "Maintainer: auto-generated";
		String descriptionLine = "Description: This package represents the contents of the service pack " + name + ".";
		BufferedWriter fileOutput;
		try {
			File controlFile =  File.createTempFile("control-", "");
			controlFile.deleteOnExit();
			fileOutput = new BufferedWriter(new FileWriter(controlFile));
			fileOutput.write(packageLine);
			fileOutput.newLine();
			fileOutput.write(versionLine);
			fileOutput.newLine();
			fileOutput.write(sectionLine);
			fileOutput.newLine();
			fileOutput.write(priorityLine);
			fileOutput.newLine();
			fileOutput.write(architectureLine);
			fileOutput.newLine();
			fileOutput.write(dependsLine);
			fileOutput.newLine();
			fileOutput.write(recommendsLine);
			fileOutput.newLine();
			fileOutput.write(suggestsLine);
			fileOutput.newLine();
			fileOutput.write(sizeLine);
			fileOutput.newLine();
			fileOutput.write(maintainerLine);
			fileOutput.newLine();
			fileOutput.write(descriptionLine);
			fileOutput.newLine();
			return controlFile;
		}
		catch (Exception e) {
			System.out.println("makeControlFile: Error " + e);
			System.exit(0);
		}
		return new File("/");
	}
	
	private File makeDebianBinaryFile() {
		BufferedWriter fileOutput;
		try {
			File debianBinaryFile = File.createTempFile("debian-binary-", "");
			debianBinaryFile.deleteOnExit();
			fileOutput = new BufferedWriter(new FileWriter(debianBinaryFile));
			fileOutput.write("2.0");
			return debianBinaryFile;
		}
		catch (Exception e) {
			System.out.println("makeDebianBinaryFile: Error " + e);
			System.exit(0);
		}
		return new File("/");
	}

	public static File makeRepoAddingScript(

	public ArrayList getDependencies() {
		return depends;
	}
	
	public ArrayList getRecommends() {
		return recommends;
	}
	
	public ArrayList getSuggests() {
		return suggests;
	}

	private String name;
	private String version;
	private ArrayList depends;
	private ArrayList recommends;
	private ArrayList suggests; 

}
