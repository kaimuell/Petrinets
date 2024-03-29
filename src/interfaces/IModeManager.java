package interfaces;

/**
 * Interface um die Modi der Operation von Controllern und Inputs zu ändern. Und
 * so zwischen verschiedenen Bearbeitungsmodi umzuschalten.
 *
 */
public interface IModeManager {
	/**
	 * Ändere den Modus auf den "Bearbeitungsmodus"
	 */
    void changeToBuildMode();

	/**
	 * Ändere den Modus auf den "Beobachtungsmodus"
	 */
    void changeToViewMode();
}
