/**
 * Basisklasse für alle Spielfeld-Kacheln.
 *
 * <p>Das Spielfeld besteht aus einem 2D-Array von {@code GameTile}-Objekten.
 * Jede Kachel kennt ihre eigene Position und kann sich selbst in einen String
 * einzeichnen ({@link #draw(String, int)}).</p>
 *
 * <h2>Vererbungshierarchie</h2>
 * <pre>
 *   GameTile
 *   ├── EmptyTile   (toString → " ",  begehbar, neutral)
 *   ├── WallTile    (toString → "#",  blockiert)
 *   ├── DoorTile    (toString → "+",  nur mit Schlüssel begehbar)
 *   ├── GoalTile    (toString → "X",  Level-Ausgang)
 *   ├── Gold        (toString → "$",  Item: Punkte)
 *   ├── Health      (toString → "♥",  Item: Heilung)
 *   ├── Key         (toString → "k",  Item: Schlüssel)
 *   ├── Enemy       (toString → "E",  bewegt sich, greift an)
 *   └── Player      (toString → "@",  vom Benutzer gesteuert)
 * </pre>
 *
 * <h2>Zwei didaktisch zentrale Methoden</h2>
 * <ul>
 *   <li>{@link #isPassable()} – darf der Spieler diese Kachel betreten?</li>
 *   <li>{@link #onStep(Player, World)} – was passiert, wenn der Spieler
 *       diese Kachel betritt? Template-Method-Pattern: jede Unterklasse
 *       kann ihr eigenes Verhalten einhängen (Item aufheben, heilen,
 *       Level beenden, ...), ohne dass {@link World#keyPressed} geändert
 *       werden muss.</li>
 * </ul>
 *
 * @author hibbes
 */
public class GameTile {

    /** Position dieser Kachel im Spielfeld. */
    public Position position;

    /**
     * Erstellt eine Kachel an der angegebenen Position.
     *
     * @param position Koordinate im Spielfeld
     */
    public GameTile(Position position) {
        this.position = position;
    }

    /**
     * Erstellt eine Kachel an Position (0, 0) (Standard-Konstruktor).
     * Delegiert an {@link #GameTile(Position)}.
     */
    public GameTile() {
        this(new Position(0, 0));
    }

    /**
     * Darf der Spieler auf diese Kachel laufen?
     *
     * <p>Default: <b>ja</b>. Wände und (verschlossene) Türen überschreiben
     * diese Methode und geben {@code false} zurück.</p>
     *
     * @return {@code true}, wenn die Kachel betreten werden darf
     */
    public boolean isPassable() {
        return true;
    }

    /**
     * Wird aufgerufen, sobald der Spieler diese Kachel <b>betritt</b>.
     *
     * <p>Default: macht nichts (für leere Felder und Wände sinnvoll).
     * Items überschreiben diese Methode, um sich aufheben zu lassen,
     * Heil-Potions heilen den Spieler, die {@link GoalTile} beendet das
     * Level, usw.</p>
     *
     * <p><b>Ersetzen statt Löschen:</b> Items entfernen sich beim Aufheben
     * selbst, indem sie über {@link World#replaceTile(int, int, GameTile)}
     * durch eine {@link EmptyTile} ersetzt werden.</p>
     *
     * @param player der Spieler, der die Kachel betritt
     * @param world  die Welt, in der die Interaktion stattfindet
     */
    public void onStep(Player player, World world) {
        // Default: keine Aktion
    }

    /**
     * Zeichnet diese Kachel in einen bestehenden Spielfeld-String ein.
     *
     * <p>Strings sind in Java <b>unveränderlich</b> ({@code immutable}).
     * Daher wird der String in ein {@code char[]} umgewandelt, das Zeichen
     * ersetzt und anschließend ein neuer String erstellt.</p>
     *
     * @param s           der aktuelle Spielfeld-String (alle Kacheln hintereinander)
     * @param worldLength Breite des Spielfelds (für die Index-Berechnung)
     * @return der neue String mit dieser Kachel eingezeichnet
     */
    public String draw(String s, int worldLength) {
        int index = position.toIndex(worldLength);
        char[] chars = s.toCharArray();
        chars[index] = this.toString().charAt(0);
        return new String(chars);
    }

    /**
     * @return Symbol dieser Kachel (wird in die Konsolenausgabe eingezeichnet).
     *         Default: ein Punkt – konkrete Kacheln überschreiben das.
     */
    @Override
    public String toString() {
        return ".";
    }
}
