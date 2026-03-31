/**
 * Repräsentiert eine 2D-Position (Koordinate) im Spielfeld.
 *
 * <p>Jede {@code Position} besitzt zwei ganzzahlige Koordinaten {@code x} und {@code y}.
 * Konvention: x = Spalte (→ rechts), y = Zeile (↓ unten).</p>
 *
 * <p>Die Methode {@link #toIndex(int)} ist entscheidend für das Spieldesign:
 * Sie wandelt eine 2D-Koordinate in einen 1D-Index um, damit das Spielfeld
 * als flacher String gespeichert werden kann.</p>
 *
 * @author hibbes
 */
public class Position {

    /** Spalte (0 = ganz links). */
    public int x;

    /** Zeile (0 = ganz oben). */
    public int y;

    /**
     * Erstellt eine Position an den Koordinaten (x, y).
     *
     * @param x Spalte
     * @param y Zeile
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** @return x-Koordinate */
    public int getX() { return x; }

    /** @return y-Koordinate */
    public int getY() { return y; }

    /** @param x neue x-Koordinate */
    public void setX(int x) { this.x = x; }

    /** @param y neue y-Koordinate */
    public void setY(int y) { this.y = y; }

    /**
     * Prüft, ob zwei Positionen gleich sind.
     *
     * @param that Vergleichsposition
     * @return {@code true} wenn x und y übereinstimmen
     */
    public boolean equals(Position that) {
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Wandelt eine 2D-Position in einen 1D-Index um.
     *
     * <p>Das Spielfeld wird als flacher String gespeichert
     * (Zeile 0 zuerst, dann Zeile 1, usw.). Der Index ergibt sich aus:
     * <pre>
     *   index = x + y * breite
     *
     *   Beispiel: Spielfeld 7 Spalten breit, Position (2, 3)
     *   index = 2 + 3 * 7 = 23
     * </pre></p>
     *
     * @param iWidth Breite des Spielfelds (Anzahl Spalten)
     * @return linearer Index im String
     */
    public int toIndex(int iWidth) {
        return x + y * iWidth;
    }
}
