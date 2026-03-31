/**
 * Das Spielfeld: ein 2D-Array aus {@link GameTile}-Objekten, das aus einem
 * String-Code erstellt wird.
 *
 * <p><b>Level-Encoding:</b><br>
 * Das Level wird als einfacher String übergeben, in dem jedes Zeichen für eine
 * Kachel steht:
 * <ul>
 *   <li>{@code '#'} → {@link WallTile} (Wand)</li>
 *   <li>{@code ' '} → {@link EmptyTile} (freies Feld)</li>
 * </ul>
 * Die Zeichen werden zeilenweise gelesen: Index {@code x + y * width} entspricht
 * der Position (x, y).</p>
 *
 * <p><b>Beispiel:</b>
 * <pre>
 *   String: "###   ### # ..."   (Breite=7)
 *   Zeile 0: # # #   (Index 0–6)
 *   Zeile 1: # # #   (Index 7–13)
 * </pre></p>
 *
 * @author hibbes
 */
public class GameField {

    /**
     * 2D-Array der Spielfeld-Kacheln.
     * Zugriff: {@code Level[x][y]}, wobei x = Spalte, y = Zeile.
     */
    public GameTile[][] Level;

    /** Anzahl der Spalten. */
    public int width;

    /** Anzahl der Zeilen. */
    public int height;

    /**
     * Erstellt das Spielfeld aus einem Level-String.
     *
     * <p>Jedes Zeichen des Strings entspricht einer Kachel.
     * Die Kacheln werden von links oben zeilenweise gelesen.</p>
     *
     * @param width  Anzahl der Spalten
     * @param height Anzahl der Zeilen
     * @param level  Level-String ({@code '#'} = Wand, {@code ' '} = frei)
     */
    public GameField(int width, int height, String level) {
        this.width  = width;
        this.height = height;
        Level = new GameTile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // String-Index: x + y * width (Zeile-für-Zeile-Encoding)
                switch (level.charAt(x + y * width)) {
                    case '#':
                        Level[x][y] = new WallTile();
                        break;
                    case ' ':
                        Level[x][y] = new EmptyTile();
                        break;
                    default:
                        System.out.println("Unbekanntes Zeichen an Position " + (x + y * width));
                }
            }
        }
    }

    /**
     * Gibt das gesamte Spielfeld als String zurück (alle Zeilen aneinandergehängt,
     * ohne Zeilenumbrüche – diese werden in {@link World#toString()} eingefügt).
     *
     * @return flacher String des Spielfelds
     */
    public String toString() {
        String result = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result += Level[x][y].toString();
            }
        }
        return result;
    }
}
