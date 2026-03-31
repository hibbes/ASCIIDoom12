/**
 * Basisklasse für alle Spielfeld-Kacheln.
 *
 * <p>Das Spielfeld besteht aus einem 2D-Array von {@code GameTile}-Objekten.
 * Jede Kachel kennt ihre eigene Position und kann sich selbst in einen String
 * "einzeichnen" (Methode {@link #draw(String, int)}).</p>
 *
 * <p><b>Vererbungshierarchie:</b>
 * <pre>
 *   GameTile
 *   ├── EmptyTile  (toString → " ")
 *   ├── WallTile   (toString → "#")
 *   └── Player     (toString → "@")
 * </pre>
 * Jede Unterklasse überschreibt nur {@code toString()} – die Zeichenlogik
 * in {@link #draw(String, int)} ist einmal in der Basisklasse implementiert.</p>
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
     * Zeichnet diese Kachel in einen bestehenden Spielfeld-String ein.
     *
     * <p>Das Spielfeld wird als flacher String verwaltet. Diese Methode
     * ersetzt das Zeichen an der richtigen Stelle durch das Zeichen dieser Kachel
     * (das von {@link #toString()} zurückgegeben wird).</p>
     *
     * <p><b>Technische Umsetzung:</b><br>
     * Strings sind in Java unveränderlich ({@code immutable}). Daher wird der
     * String in ein {@code char[]}-Array umgewandelt, das Zeichen ersetzt und
     * anschließend ein neuer String erstellt.</p>
     *
     * @param s           der aktuelle Spielfeld-String (alle Kacheln hintereinander)
     * @param worldLength Breite des Spielfelds (für die Index-Berechnung)
     * @return der neue String mit dieser Kachel eingezeichnet
     */
    public String draw(String s, int worldLength) {
        int index = position.toIndex(worldLength);  // 2D-Position → 1D-Index
        char[] chars = s.toCharArray();             // String → veränderliches Array
        chars[index] = this.toString().charAt(0);   // Zeichen dieser Kachel einsetzen
        return new String(chars);                   // Array → neuer String
    }
}
