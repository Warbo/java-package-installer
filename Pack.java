import java.util.*;

public class Pack {

	/* This class represents a service Pack. It has a name, a
	 * location (folder to save itself into), and includes
	 * Packages.
	 */

	public Pack() {
		// Make a new Pack
		includes = new ArrayList(0);	// This stores the Packages included in this service Pack
		wanted = new ArrayList(0);
	}
	
	public void setName(String nameArg) {
		// This sets the name of the Pack and subsequently its meta-package
		String tempName = nameArg.toLowerCase();	// The Pack name must be in lowercase
		tempName = tempName.replace(' ', '-');		// Spaces are not allowed, so use dashes in their place
		name = tempName;		// Set the name
	}
	
	public String getName() {
		// This allows access to the name from outside the Pack
		return name;
	}
	
	public void setLocation(String locationArg) {
		// This sets the folder to save this Pack into
		location = locationArg;
	}
	
	public String getLocation() {
		// This allows access to the save location from outside the Pack
		return location;
	}
	
	public void clearIncludes() {
		// Forget about all included Packages
		includes.clear();
	}
	
	public void addIncludes(Package includeArg) {
		// This takes a given Package and adds it to the includes
		// includes list if it is not already there
		String currentName = includeArg.getName();	// The name of the current includeArg Package
		Package currentPackage;		// The current Package in the includes list
		boolean include = true;	// Assume this Package is to be added
		for (int i=0; i<includes.size(); i++) {		// For the included Packages we already have...
			currentPackage = (Package) includes.get(i);	// Get each already included Package in turn
			if (currentName.equals(currentPackage.getName())) {	// If the new Package is already in the includes list...
				include = false;	// Don't bother adding it again
			}
		}
		if (include) {		// If we are still adding it...
			includes.add(includeArg);	// Then go ahead and add it
		}
	}
	
	public void addIncludes(Package[] includeArg) {
		// This steps through a given array of Packages and
		// adds them to the includes list if not already there
		String currentName;	// The name of the current includeArg Package
		Package currentPackage;		// The current Package in the includes list
		boolean include;	// Whether to add this Package or not
		for (int i=0; i<includeArg.length; i++) {	// For each of the given Packages...
			include = true;		// Assume that it is needed
			currentName = includeArg[i].getName();		// Find its name
			for (int j=0; j<includes.size(); j++) {		// For the included Packages we already have...
				currentPackage = (Package) includes.get(j);	// Get each already included Package in turn
				if (currentName.equals(currentPackage.getName())) {	// If the new Package is already in the includes list...
					include = false;	// Don't bother adding it again
				}
			}
			if (include) {		// If we are still adding it...
				includes.add(includeArg[i]);	// Then go ahead and add it
			}
		}
	}
	
	public void addIncludes(ArrayList includeArg) {
		// This steps through a given ArrayList of Packages and
		// adds them to the includes list if not already there
		String currentArgumentName;	// The name of the current includeArg Package
		String currentExistingName;
		Package currentArgumentPackage;		// The current Package in the includes list
		Package currentExistingPackage;
		boolean include;	// Whether to add this Package or not
		for (int i=0; i<includeArg.size(); i++) {	// For each of the given Packages...
			include = true;		// Assume that it is needed
			currentArgumentPackage = (Package) includeArg.get(i);		// Find its name
			currentArgumentName = currentArgumentPackage.getName(); 
			for (int j=0; j<includes.size(); j++) {		// For the included Packages we already have...
				currentExistingPackage = (Package) includes.get(j);	// Get each already included Package in turn
				currentExistingName = currentExistingPackage.getName();
				if (currentArgumentName.equals(currentExistingName)) {	// If the new Package is already in the includes list...
					include = false;	// Don't bother adding it again
				}
			}
			if (include) {		// If we are still adding it...
				includes.add(currentArgumentPackage);	// Then go ahead and add it
			}
		}
	}
	
	public void addWanted(Package[] wantedArg) {
		String currentName;
		Package currentPackage;
		boolean include;
		for (int i=0; i<wantedArg.length; i++) {
			include = true;
			currentName = wantedArg[i].getName();
			for (int j=0; j<wanted.size(); j++) {
				currentPackage = (Package) wanted.get(j);
				if (currentName.equals(currentPackage.getName())) {
					include = false;
				}
			}
			if (include) {
				wanted.add(wantedArg[i]);
				includes.add(wantedArg[i]);
			}
		}
	}
	
	public void addWanted(ArrayList wantedArg) {
		String currentExistingName;
		String currentGivenName;
		Package currentExistingPackage;
		Package currentGivenPackage;
		boolean include;
		for (int i=0; i<wantedArg.size(); i++) {
			include = true;
			currentGivenPackage = (Package) wantedArg.get(i);
			currentGivenName = currentGivenPackage.getName();
			for (int j=0; j<wanted.size(); j++) {
				currentExistingPackage = (Package) wanted.get(j);
				currentExistingName = currentExistingPackage.getName();
				if (currentExistingName.equals(currentGivenName)) {
					include = false;
				}
			}
			if (include) {
				wanted.add(((Package) wantedArg.get(i)));
				includes.add(((Package)wantedArg.get(i)));
			}
		}
	}

	public static Package[] getPackages(String packageArg) {
		// This is a useful method which takes a line of text and splits it at each space it finds.
		// Since Package names cannot contain spaces, this can find the names of Packages in a
		// space-separated list of Package names
		packageArg.toLowerCase();	// Standardise the text to all lowercase
		Package[] returnPackages;	// Set up the list of Packages to return
		if (packageArg.contains(" ")) {		// First see if there are any spaces in the input line
			int spaces = 0;		// If so then start a counter for the spaces
			for (int i=0; i<packageArg.length(); i++) {	// Go through the whole line a character at a time...
				if ((packageArg.charAt(i) == ' ')) {	// If a space is found...
					spaces++;	// Add one to the space counter
				}
			}
			returnPackages = new Package[spaces + 1];	// The number of Packages in the list is one more than the number of spaces
			Scanner packageScanner = new Scanner(packageArg);	// This will split the text for us (uses space as split-point by default)
			for (int i=0; i<spaces + 1; i++) {	// For each Package in the input text...
				returnPackages[i] = new Package(packageScanner.next());		// Make it into a proper Package and add it to the list of found Packages 
				//msg(returnPackages[i].getName());
			}
		}
		else {		// If no spaces are found...
			returnPackages = new Package[1];	// There is only one Package specified
			returnPackages[0] = new Package(packageArg);	// Turn the text into a Package
		}
		return returnPackages;		// Send the found Packages back to whatever asked for them
	}

	public String toString() {
		// This is useful for debugging
		Package tempPackage;	// Make a temporary Package
		String readOut = "Service Pack Name: " + name + ", Location: " + location + ", Includes: ";	// Give out this information about the Pack
		if (includes.size()>0) {	// If there are any included Packages...
			for (int i=0; i<includes.size(); i++) {		// Go through each of them...
				tempPackage = (Package) includes.get(i);	// Make the temporary Package into it
				readOut = readOut + tempPackage.getName();	// Add the temporary Package's name to the output
				readOut = readOut + " ";	// Add a space to the output
			}
		}
		return readOut;		// Send the output to whatever asked for it
	}
	
	private static void msg(String toPrint) {
		// This is used for debugging. Disabing this method will make most debugging messages go away
		System.out.println(toPrint);	// Output the given text to the terminal
	}

	private Package makeMetaPackage() {
		metaPackage = new Package(name);
		metaPackage.addDepends(wanted);
		return metaPackage;
	}

	private Package metaPackage;
	private String name;		// Service Pack name
	private String location;	// The directory to save it to
	private ArrayList includes;	// The Packages it includes
	private ArrayList wanted;	// The Packages specifically wanted in this Pack (excluding dependencies)

}
