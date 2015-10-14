import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainInterface extends JPanel {

	/* This GUI was created using the AbeilleForms RAD tool, basically because
	 * my coding skills are not as developed as my ability to take on more than
	 * I can handle.
	 */

	JFormattedTextField ui_jformattedtextfield1 = new JFormattedTextField();
	JFormattedTextField ui_jformattedtextfield2 = new JFormattedTextField();
	JFormattedTextField ui_jformattedtextfield3 = new JFormattedTextField();
	JCheckBox ui_jcheckbox1 = new JCheckBox();
	JButton ui_nameGo = new JButton();
	JButton ui_locationGo = new JButton();
	JButton ui_includeGo = new JButton();
	JButton ui_jbutton1 = new JButton();
	JButton ui_createButton = new JButton();
	JButton ui_cancelButton = new JButton();

	/* Default constructor
	 */

	public MainInterface() {
		packageList = new PackageList();
		packageList.getListFiles();
		packageList.combineContents();
		//packageList.printFullList();
		initializePanel();
	}

	/* Main method for panel
	 */

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocation(100, 100);
		frame.getContentPane().add(new MainInterface());
		frame.setVisible(true);

		frame.addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent evt ) {
				System.exit(0);
			}
		});
	}

	/* Adds fill components to empty cells in the first row and first column of the grid.
	 * This ensures that the grid spacing will be the same as shown in the designer.
	 * @param cols an array of column indices in the first row where fill components should be added.
	 * @param rows an array of row indices in the first column where fill components should be added.
	 */

	void addFillComponents( Container panel, int[] cols, int[] rows ) {
		Dimension filler = new Dimension(10,10);

		boolean filled_cell_11 = false;
		CellConstraints cc = new CellConstraints();
		if ( cols.length > 0 && rows.length > 0 ) {
			if ( cols[0] == 1 && rows[0] == 1 ) {
				// add a rigid area
				panel.add( Box.createRigidArea( filler ), cc.xy(1,1) );
				filled_cell_11 = true;
			}
		}

		for( int index = 0; index < cols.length; index++ ) {
			if ( cols[index] == 1 && filled_cell_11 ) {
				continue;
			}
			panel.add( Box.createRigidArea( filler ), cc.xy(cols[index],1) );
		}

		for( int index = 0; index < rows.length; index++ ) {
			if ( rows[index] == 1 && filled_cell_11 ) {
				continue;
			}
			panel.add( Box.createRigidArea( filler ), cc.xy(1,rows[index]) );
		}
	}

	/* Helper method to load an image file from the CLASSPATH
	 * @param imageName the package and name of the file to load relative to the CLASSPATH
	 * @return an ImageIcon instance with the specified image file
	 * @throws IllegalArgumentException if the image resource cannot be loaded.
	 */

	public ImageIcon loadImage( String imageName ) {
		try {
			ClassLoader classloader = getClass().getClassLoader();
			java.net.URL url = classloader.getResource( imageName );
			if ( url != null ) {
				ImageIcon icon = new ImageIcon( url );
				return icon;
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException( "Unable to load image: " + imageName );
	}

	public JPanel createPanel() {
		JPanel jpanel1 = new JPanel();
		FormLayout formlayout1 = new FormLayout("FILL:4DLU:GROW(1.0),FILL:DEFAULT:NONE,FILL:4DLU:GROW(1.0),FILL:100DLU:GROW(1.0),FILL:DEFAULT:NONE,FILL:4DLU:GROW(1.0)","CENTER:2DLU:GROW(1.0),CENTER:DEFAULT:NONE,CENTER:2DLU:GROW(1.0),CENTER:DEFAULT:NONE,CENTER:2DLU:GROW(1.0),CENTER:DEFAULT:NONE,CENTER:2DLU:GROW(1.0),CENTER:DEFAULT:NONE,CENTER:DEFAULT:GROW(1.0),CENTER:DEFAULT:NONE,CENTER:2DLU:GROW(1.0)");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);

		JLabel jlabel1 = new JLabel();
		jlabel1.setText("Service Pack Name:");
		jlabel1.setToolTipText("The name used to identify this service pack");
		jlabel1.setHorizontalAlignment(JLabel.TRAILING);
		jpanel1.add(jlabel1,cc.xy(2,2));

		JLabel jlabel2 = new JLabel();
		jlabel2.setText("Save Location:");
		jlabel2.setToolTipText("The folder where files will be saved");
		jlabel2.setHorizontalAlignment(JLabel.TRAILING);
		jpanel1.add(jlabel2,cc.xy(2,4));

		JLabel includeLabel = new JLabel();	////////////////////////////////////////////////////////// jlabel3
		includeLabel.setText("Include Packages:");
		includeLabel.setToolTipText("Packages to include in this service pack (verisons optional)");
		includeLabel.setHorizontalAlignment(JLabel.TRAILING);
		jpanel1.add(includeLabel,cc.xy(2,8));

		jpanel1.add(ui_jformattedtextfield1,cc.xy(4,2));

		jpanel1.add(ui_jformattedtextfield2,cc.xy(4,4));

		jpanel1.add(ui_jformattedtextfield3,cc.xy(4,8));

		ui_jcheckbox1.setActionCommand("Include Packages On This System");
		ui_jcheckbox1.setText("Include Packages On This System");
		ui_jcheckbox1.setToolTipText("Put the non-default packages installed on this system into the service pack");
		ui_jcheckbox1.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
   				if (includeNonDefaults) {
   					includeNonDefaults = false;
   				}
   				else {
   					includeNonDefaults = true;
   				}
   			}
		});
		jpanel1.add(ui_jcheckbox1,cc.xy(2,6));

		// These buttons were used in testing
	
		/*ui_nameGo.setActionCommand("Enter");
		ui_nameGo.setName("nameGo");
		ui_nameGo.setText("Enter");
		ui_nameGo.setToolTipText("This only exists because I don't know how to get the entered text automatically");

		ui_nameGo.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				workingPack.setName(ui_jformattedtextfield1.getText());
				msg(workingPack.toString());
   			}
		});

		jpanel1.add(ui_nameGo,cc.xy(5,2));*/
      

		/*ui_locationGo.setName("locationGo");
		ui_locationGo.setText("Enter");
		ui_locationGo.setToolTipText("This only exists because I don't know how to get the entered text automatically");
      
		ui_locationGo.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				workingPack.setLocation(ui_jformattedtextfield2.getText());
				msg(workingPack.toString());
   			}
		});
      
		jpanel1.add(ui_locationGo,cc.xy(5,4));*/
      

		/*ui_includeGo.setActionCommand("Enter");
		ui_includeGo.setName("includeGo");
		ui_includeGo.setText("Enter");
		ui_includeGo.setToolTipText("This only exists because I don't know how to get the entered text directly");
      
		ui_includeGo.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
   				workingPack.clearIncludes();
				workingPack.addIncludes(Pack.getPackages(ui_jformattedtextfield3.getText()));
				msg(workingPack.toString());
   			}
		});
      
		jpanel1.add(ui_includeGo,cc.xy(5,8));*/

		// This should be a tab in the final version, but is a button here for ease
		ui_jbutton1.setActionCommand("Advanced");
		ui_jbutton1.setText("Advanced");
		ui_jbutton1.setToolTipText("Advanced options allow greater control over service packs");
      
		ui_jbutton1.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				msg("Not implemented yet.");
   			}
		});
		
      
		jpanel1.add(ui_jbutton1,cc.xy(2,10));

		// This button starts the actual creation
		ui_createButton.setActionCommand("Create");
		ui_createButton.setName("createButton");
		ui_createButton.setText("Create");
		ui_createButton.setToolTipText("Make the service pack with the options entered");
      
		ui_createButton.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				createPack();
   			}
		});
      
		jpanel1.add(ui_createButton,cc.xy(5,10));

		// This closes the application
		ui_cancelButton.setActionCommand("Cancel");
		ui_cancelButton.setName("cancelButton");
		ui_cancelButton.setText("Cancel");
		ui_cancelButton.setToolTipText("Exit without making a service pack");
      
		ui_cancelButton.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
   			}
		});
      
		jpanel1.add(ui_cancelButton,cc.xy(4,10));

		addFillComponents(jpanel1,new int[]{ 1,2,3,4,5,6 },new int[]{ 1,2,3,4,5,6,7,8,9,10,11 });
		return jpanel1;
	}

	private void createPack() {
		// This is run when the "Create" button is pressed and does the actual work
		//if (ui_jformattedtextfield1.getText().equals(null)) {
		//	popupWarning("Please enter a name for the service pack");
		//}
		//else {
		//	if (ui_jformattedtextfield2.getText().equals(null)) {
		//		popupWarning("Please enter a location to save the service pack");
		//	}
		//	else {
				workingPack.setName(ui_jformattedtextfield1.getText());
   				workingPack.setLocation(ui_jformattedtextfield2.getText());
   				workingPack.clearIncludes();
   				Package[] givenPackages = Pack.getPackages(ui_jformattedtextfield3.getText());
   				boolean[] existOrNot = packageList.checkPackagesExist(givenPackages);
   				int indexThatExists = -1;
   				int doNotExist = 0;
   				for (int i=0; i<givenPackages.length; i++) {
   					if (existOrNot[i]) {
   						indexThatExists = i;
   					}
   					else {
   						doNotExist++;
   					}
   				}
   				String[] packagesNotExisting = new String[doNotExist];
   				doNotExist = 0;
   				for (int i=0; i<givenPackages.length; i++) {
   					if (!(existOrNot[i])) {
   						packagesNotExisting[doNotExist] = givenPackages[i].getName();
   						doNotExist++;
   						givenPackages[i] = givenPackages[indexThatExists];
   					}
   				}
   				if (doNotExist == 0) {
					workingPack.addWanted(givenPackages);
					msg(workingPack.toString());
					if (includeNonDefaults) {
						msg("Include Non-Defaults: true");
					}
					else {
						msg("Include Non-Defaults: false");
					}
				}
				else {
					String message = "The following packages could not be found: ";
					message = message.concat(" " + packagesNotExisting[0]);
					for (int i=1; i<packagesNotExisting.length; i++) {
						message = message.concat(", " + packagesNotExisting[i]);
					}
					popupWarning(message);
				}
		//	}
		//}
	}

	private void popupWarning(String message) {
		final JFrame popupFrame = new JFrame();
		popupFrame.setPreferredSize(new Dimension(600, 100));
		JPanel popupPanel = new JPanel();
		popupFrame.add(popupPanel);
		JLabel messageLabel = new JLabel(message);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent ae) {
				popupFrame.setVisible(false);
   			}
		});
		popupPanel.add(messageLabel, BorderLayout.NORTH);
   		popupPanel.add(okButton, BorderLayout.SOUTH);
   		popupFrame.pack();
   		popupFrame.setVisible(true);
	}
	
	/* Initializer
	 */
	protected void initializePanel() {
		setLayout(new BorderLayout());
		add(createPanel(), BorderLayout.CENTER);
	}
   
	private static void msg(String toPrint) {
		System.out.println(toPrint);
	}

	private Pack workingPack = new Pack();
	private boolean includeNonDefaults = false;
	private PackageList packageList;
	
}
