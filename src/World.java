/**
 * Die Spielwelt: verbindet Spielfeld und Spieler, verarbeitet Eingaben
 * und zeichnet den aktuellen Zustand.
 *
 * <p><b>Architektur:</b><br>
 * World enthält (Komposition):
 * <ul>
 *   <li>{@link GameField} – das statische Spielfeld (Wände, freie Felder)</li>
 *   <li>{@link Player} – die bewegliche Spielerfigur</li>
 * </ul>
 * </p>
 *
 * <p><b>Spielschleife (in {@link GameMain}):</b>
 * <ol>
 *   <li>Welt zeichnen ({@code System.out.println(world)})</li>
 *   <li>Tastatureingabe lesen</li>
 *   <li>{@link #keyPressed(String)} aufrufen</li>
 *   <li>Weiter bei 1.</li>
 * </ol>
 * </p>
 *
 * @author hibbes
 */
public class World {

    /** Das statische Spielfeld mit Wänden und freien Feldern. */
    public GameField field;

    /** Die steuerbare Spielerfigur. */
    public Player player;

    /**
     * Erstellt die Startwelt mit einem fest codierten Level.
     *
     * <p>Level-Layout (7 × 8 Zeichen):</p>
     * <pre>
     *   ###   #
     *   ## # ##
     *   ##### #
     *   ## ### #
     *   ###  ####
     *    #### ###
     *   ##### ####
     *    #### ####
     * </pre>
     * Spieler startet an Position (1, 1) – erstes freies Feld links oben.
     */
    public World() {
        field  = new GameField(7, 8, "###   ### # ####### ### ### ####  #### ####### #### ####");
        player = new Player();
        player.position.x = 1;
        player.position.y = 1;
    }

    /**
     * Gibt die Spielwelt als beschriftetes ASCII-Raster zurück.
     *
     * <p>Der Spieler wird über das Spielfeld "gezeichnet": Sein Zeichen {@code @}
     * ersetzt das Zeichen an seiner aktuellen Position im Spielfeld-String.</p>
     *
     * @return formatiertes ASCII-Spielfeld mit Zeilen- und Spaltennummern
     */
    @Override
    public String toString() {
        String s = field.toString();              // Spielfeld ohne Spieler
        s = player.draw(s, field.width);         // Spieler einzeichnen

        // Spaltenköpfe: 0–6 (Einerstellen der Spaltennummern)
        String result = "\ ";
        for (int column = 0; column < field.width; column++) {
            result += (column % 10);
        }
        result += "\n";

        // Zeilen mit Zeilennummer
        for (int row = 0; row < s.length() / field.width; row++) {
            result += (row % 10) + " "
                    + s.substring(row * field.width, (row + 1) * field.width) + "\n";
        }
        return result;
    }

    /**
     * Verarbeitet eine Tastatureingabe und bewegt den Spieler entsprechend.
     *
     * <p>Steuerung: {@code w} = hoch, {@code s} = runter, {@code a} = links, {@code d} = rechts.</p>
     *
     * <p>Kollisionsprüfung: Nach der Bewegung wird geprüft, ob der Spieler auf einer
     * Wand steht. Falls ja, wird die Bewegung rückgängig gemacht.</p>
     *
     * @param s die gedrückte Taste als String (nur das erste Zeichen wird ausgewertet)
     */
    public void keyPressed(String s) {
        if (s.isEmpty()) return;

        // Spieler bewegen (noch ohne Kollisionsprüfung)
        switch (s.charAt(0)) {
            case 'w': player.moveUp();    break;
            case 's': player.moveDown();  break;
            case 'a': player.moveLeft();  break;
            case 'd': player.moveRight(); break;
            default:
                System.out.println("Unbekannte Taste: " + s.charAt(0));
                return;
        }

        // Kollisionsprüfung: Spieler auf Wand? → Bewegung rückgängig
        if (checkCollision(player.position.x, player.position.y)) {
            // Bewegung rückgängig machen (umgekehrte Richtung)
            switch (s.charAt(0)) {
                case 'w': player.moveDown();  break;
                case 's': player.moveUp();    break;
                case 'a': player.moveRight(); break;
                case 'd': player.moveLeft();  break;
            }
        }
    }

    /**
     * Prüft, ob die angegebene Position eine Wand enthält.
     *
     * <p>Zusätzlich werden Positionen außerhalb des Spielfelds als Kollision
     * gewertet (Grenzkontrolle), um ArrayIndexOutOfBoundsException zu vermeiden.</p>
     *
     * @param x Spalte
     * @param y Zeile
     * @return {@code true} wenn Position eine Wand ist oder außerhalb des Felds liegt
     */
    boolean checkCollision(int x, int y) {
        // Grenzprüfung: außerhalb des Spielfelds
        if (x < 0 || x >= field.width || y < 0 || y >= field.height) {
            return true;
        }
        // Kachelprüfung: ist die Kachel an (x, y) eine Wand?
        return field.Level[x][y] instanceof WallTile;
    }
}
