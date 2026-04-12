/**
 * Das statische Spielfeld: ein 2D-Array aus {@link GameTile}-Objekten,
 * das aus einem Level-String erzeugt wird.
 *
 * <h2>Level-Encoding</h2>
 * <p>Das Level wird als <b>eine einzige Zeichenkette</b> übergeben. Jedes
 * Zeichen entspricht einer Kachel; die Zeichen werden zeilenweise von
 * links oben ausgelesen. Mögliche Symbole:
 * <ul>
 *   <li>{@code '#'} → {@link WallTile}  (Wand)</li>
 *   <li>{@code ' '} → {@link EmptyTile} (freies Feld)</li>
 *   <li>{@code '$'} → {@link Gold}      (Gold-Item)</li>
 *   <li>{@code '*'} → {@link Health}    (Heil-Potion)</li>
 *   <li>{@code 'k'} → {@link Key}       (Schlüssel)</li>
 *   <li>{@code '+'} → {@link DoorTile}  (Tür, verschlossen)</li>
 *   <li>{@code 'X'} → {@link GoalTile}  (Level-Ausgang)</li>
 * </ul>
 * </p>
 *
 * <p>Gegner und der Spieler werden <b>nicht</b> im Grid gespeichert,
 * sondern separat von {@link World} verwaltet. Ihre Startpositionen
 * können zusätzlich übergeben werden.</p>
 *
 * <h2>Index-Umrechnung</h2>
 * <p>Zugriff: {@code Level[x][y]}, wobei {@code x} = Spalte, {@code y} = Zeile.
 * Für den flachen String gilt {@code index = x + y * width}.</p>
 *
 * @author hibbes
 */
public class GameField {

    /**
     * 2D-Array der Spielfeld-Kacheln.
     * Zugriff: {@code Level[x][y]}.
     */
    public GameTile[][] Level;

    /** Anzahl der Spalten. */
    public int width;

    /** Anzahl der Zeilen. */
    public int height;

    /**
     * Erstellt das Spielfeld aus einem Level-String.
     *
     * <p>Die übergebene Zeichenkette muss exakt {@code width * height}
     * Zeichen lang sein. Unbekannte Zeichen werden als leeres Feld
     * interpretiert und auf der Konsole protokolliert.</p>
     *
     * @param width  Anzahl der Spalten
     * @param height Anzahl der Zeilen
     * @param level  Level-String (Tile-Symbole, zeilenweise)
     */
    public GameField(int width, int height, String level) {
        this.width  = width;
        this.height = height;
        Level = new GameTile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = x + y * width;
                char c = (index < level.length()) ? level.charAt(index) : ' ';
                Level[x][y] = createTile(c);
                Level[x][y].position = new Position(x, y);
            }
        }
    }

    /**
     * Fabrikmethode: wandelt ein Symbol aus dem Level-String in eine
     * passende {@link GameTile}-Instanz um.
     *
     * <p><b>Didaktisch wichtig:</b> hier hängt eine klassische
     * <i>Factory</i>-Struktur – eine zentrale Stelle, die aus Daten
     * (hier: Zeichen) passende Objekte baut. Wer einen neuen Tile-Typ
     * hinzufügt, muss nur diese Methode um einen Fall ergänzen.</p>
     */
    private static GameTile createTile(char c) {
        switch (c) {
            case '#': return new WallTile();
            case ' ': return new EmptyTile();
            case '$': return new Gold();
            case '*': return new Health();
            case 'k': return new Key();
            case '+': return new DoorTile();
            case 'X': return new GoalTile();
            default:
                System.out.println("Unbekanntes Zeichen im Level: '" + c + "' – wird als Leerfeld behandelt.");
                return new EmptyTile();
        }
    }

    /**
     * Gibt das gesamte Spielfeld als flachen String zurück
     * (Zeilen ohne Umbruch aneinandergehängt).
     *
     * <p>Zeilen­umbrüche und Rahmen­beschriftung werden erst in
     * {@link World#toString()} eingefügt.</p>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(width * height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(Level[x][y].toString());
            }
        }
        return sb.toString();
    }
}
