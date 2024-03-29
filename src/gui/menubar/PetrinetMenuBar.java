package gui.menubar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.ModeManagerNode;
import controller.filemanager.FileHandler;
import interfaces.ITextWindow;

/**
 * Implementiert die MenuBar des Programmes im {@link gui.PetrinetMainFrame}. Optionen sind Öffnen, nochmal
 * laden, analysiere mehrere Dateien, Schliessen. So wie das umschalten zwischen
 * Ansichts und Beobachtungsmodus
 *
 */
public class PetrinetMenuBar extends JMenuBar {

	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private final OpenPNMLDialog openDialog;
	private final FileHandler fileHandler;
	private final SystemExitDialog closingFrame;
	private File lastFileOpened;
	private final JFrame frameToSetPositionOfDialogs;
	private final ModeManagerNode managerNode;
	private final Color BACKGROUNDCOLOR = new Color(30, 30, 30);
	private final Color FOREGROUNDCOLOR = new Color(230, 230, 230);

	/**
	 * Konstruktor
	 * 
	 * @param controller                  der zugewiesene Controller
	 * @param frameToSetPositionOfDialogs Frame zu dem Fenster mittig Positioniert
	 *                                    werden sollen.
	 * @param txtWindow                   Fenster zur Ausgabe von Text
	 * @param managerNode                 Der Mode Manager der das Umschalten von
	 *                                    Modi managed
	 */
	public PetrinetMenuBar(Controller controller, JFrame frameToSetPositionOfDialogs, ITextWindow txtWindow,
			ModeManagerNode managerNode) {
		super();
		this.setBackground(BACKGROUNDCOLOR);
		this.frameToSetPositionOfDialogs = frameToSetPositionOfDialogs;
		this.fileHandler = new FileHandler(controller, txtWindow);
		this.openDialog = new OpenPNMLDialog();
		this.closingFrame = new SystemExitDialog();
		this.closingFrame.setVisible(false);
		this.lastFileOpened = new File("../ProPra-WS20-Basis/Beispiele/");
		this.managerNode = managerNode;
		
		this.add(initialiseDataMenu());
		this.add(initialiseModeMenu());
	}

	private JMenu initialiseDataMenu() {
		JMenu datMenu = new JMenu("Datei");
		datMenu.setForeground(FOREGROUNDCOLOR);
		datMenu.add(initialiseNew());
		datMenu.add(initialiseOpenFile());
		datMenu.add(initializeLoadAgain());
		datMenu.add(initializeAnalyseMultipleFiles());
		datMenu.add(initialiseSafeFile());
		datMenu.add(initialiseCloseProgram());
		return datMenu;
	}

	private JMenuItem initialiseNew() {
		JMenuItem newGraph = new JMenuItem("Neuer Graph");

		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileHandler.resetGraph();
			}
		});
		return newGraph;
	}


	private JMenu initialiseModeMenu() {
		JMenu modeMenu = new JMenu("Modus");
		modeMenu.setForeground(FOREGROUNDCOLOR);
		modeMenu.add(initialiseViewMode());
		modeMenu.add(initialiseBuildMode());
		return(modeMenu);
	}

	private JMenuItem initialiseViewMode() {
		JMenuItem viewMode = new JMenuItem("Ansichts-Modus");
		viewMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				managerNode.changeToViewMode();
			}
		});
		return viewMode;
	}

	private JMenuItem initialiseBuildMode() {
		JMenuItem buildMode = new JMenuItem("Bearbeiten-Modus");
		buildMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				managerNode.changeToBuildMode();

			}
		});
		return buildMode;
	}

	private JMenuItem initializeAnalyseMultipleFiles() {
		JMenuItem mulDatOpen = new JMenuItem("Analyse mehrerer Dateien");
		mulDatOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openDialog.setMultiSelectionEnabled(true);
				File[] f = null;
				int returnVal = openDialog.showOpenDialog(frameToSetPositionOfDialogs);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = openDialog.getSelectedFiles();
					fileHandler.batchProcessing(f);
				}
			}
		});
		return mulDatOpen;
	}

	private JMenuItem initializeLoadAgain() {
		JMenuItem loadAgain = new JMenuItem("Neu Laden");
		loadAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lastFileOpened != null) {
					fileHandler.loadFile(lastFileOpened);
				}

			}
		});
		return loadAgain;
	}

	private JMenuItem initialiseCloseProgram() {
		JMenuItem close = new JMenuItem("Beenden");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closingFrame.setLocationRelativeTo(frameToSetPositionOfDialogs);
				closingFrame.setVisible(true);
			}
		});
		return close;

	}

	private JMenuItem initialiseOpenFile() {
		JMenuItem datOpen = new JMenuItem("Öffnen");
		datOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openDialog.setMultiSelectionEnabled(false);
				File file = null;
				int returnVal = openDialog.showOpenDialog(frameToSetPositionOfDialogs);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = openDialog.getSelectedFile();
					lastFileOpened = file;
					openDialog.setCurrentDirectory(file);
					fileHandler.loadFile(file);
				}
			}
		});
		return datOpen;
	}

	private JMenuItem initialiseSafeFile() {
		JMenuItem datSave = new JMenuItem("Speichern als");
		datSave.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			openDialog.setMultiSelectionEnabled(false);
			File file = null;
			int returnVal = openDialog.showSaveDialog(frameToSetPositionOfDialogs);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = openDialog.getSelectedFile();
				FileNameExtensionFilter fileFilter = openDialog.getFileNameExtensionFilter();
				if (! fileFilter.accept(file)) {
					// if the filter doesn't accept the filename, that must be because it doesn't have the correct extension
					// so change the extension to the first extension offered by the filter.
					String extension = fileFilter.getExtensions()[0];

					String newName = file.getName() + "." + extension;
					file = new File(file.getParent(), newName);

				}


				lastFileOpened = file;
				openDialog.setCurrentDirectory(file);
				fileHandler.save(file);
			}
		}
	});
	return datSave;
	}
}
