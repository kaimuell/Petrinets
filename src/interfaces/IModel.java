package interfaces;

import java.util.List;

import exceptions.InvalidEdgeException;
import model.AccGraphMetaData;
import model.AccessabilityGraph;
import model.elements.*;

/**
 * Ein Interface, welches die Methoden bereitstellt mit denen der Contoller auf das {@link model.DataModel} zugreifen darf.
 *
 */

public interface IModel {

    /**
     * Setzt den Ausgangs Zustand auf die aktuelle Markierung
     */
    void setInitialMarkingToActualState();

    /**
     * Getter Methode für den Erreichbarkeitsgraphen des DataModels
     * 
     * @return der Erreichbarkeitsgraph
     */
    AccessabilityGraph getAccessabilityGraph();

    /**
     * Getter Methode für den ausgewählten Knoten des Petrinetzes
     * 
     * @return der ausgewählte Knoten / null wenn keiner gewählt
     */
    PLabeledNode getSelectedNode();

    /**
     * Übergibt die Anfangsmarkierung
     * 
     * @return die Anfangsmarkierung
     */

    PMarking getInitialMarking();

    /**
     * Fügt eine Stelle der Liste der Stellen hinzu
     * 
     * @param f die hinzuzufügende Stelle
     */
    void addField(PField f);

    /**
     * Fügt eine Transition der Liste der Transitionen hinzu
     * 
     * @param t die hinzuzufügende Transition
     */
    void addTransition(PTransition t);

    /**
     * Getter Methode für die Liste der Transitionen
     * 
     * @return die Liste der Transitionen
     */
    List<PTransition> getTransitionList();

    /**
     * Getter Methode für die Liste der Felder
     * 
     * @return die Liste der Felder
     */
    List<PField> getFieldList();

    /**
     * Getter Methode für die Liste der Kanten
     * 
     * @return die Liste
     */
    List<PEdge> getEdgeList();

    /**
     * Setter Methode für den Selektierten Knoten
     * 
     * @param node der Knoten
     */
    void setSelectedNode(PLabeledNode node);

    /**
     * Setzt den Zusatnd aller Stellen auf den Zustand der übergebenen Markierung
     * 
     * @param m Die Markierung
     */
    void setFieldsToMarking(PMarking m);

    /**
     * Setzt die Marken aller Stellen auf den Zustand der Start-Markierung zurück
     */
    void resetFieldsToInitialMarking();

    /**
     * Erschafft eine neue Kante, fügt sie der Liste hinzu und verlinkt sie mit
     * Knoten die bereits im DataModel gespeichert sind
     * 
     * @param id   die Id der neuen Kante
     * @param from die Id des Knotens von der die Kante ausgeht
     * @param to   die Id des Knotens in den die Kante führt
     * @throws InvalidEdgeException die Kante ist nicht zulässig
     */
    void addEdgeAndRelink(String id, String from, String to) throws InvalidEdgeException;

    /**
     * Löscht eine Kante aus dem Petrinetz inklusive aller Einträge in der doppelten
     * Verknüpfung
     * 
     * @param e die Kante
     */
    void deleteEdgeSafely(PEdge e);

    /**
     * Übergibt die Liste der Kanten aus dem Erreichbarkeitsgraphen
     * 
     * @return die Liste der Kanten
     */
    List<PAccEdge> getListOfEdgesOfMarkings();

    /**
     * Übergibt die Liste der Markierungen aus dem Erreichbarkeitsgraphen
     * 
     * @return die Liste der Markierungen
     */
    List<PMarking> getListOfMarkingsInGraph();

    /**
     * Übergibt die MetaDaten des Erreichbarkeitsgraphen. diese bestehen aus M, M#
     * dem Pfad zwischen beiden, sowie die Kante die durch die zuletzt geschaltete
     * Transition erzeugt wurde.
     * 
     * @return Die MetaDaten des ErreichbarkeitsGraphen
     */
    AccGraphMetaData getAccessabilityGraphMetaData();

}
