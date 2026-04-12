import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Die Spielwelt: verbindet Spielfeld, Spieler und Gegner, verarbeitet
 * Eingaben und verwaltet den Spielzustand eines einzelnen Levels.
 *
 * <h2>Verantwortlichkeiten</h2>
 * <ul>
 *   <li>Spielfeld ({@link GameField}) aufbauen und halten</li>
 *   <li>Spieler- und Gegner­liste verwalten</li>
 *   <li>Eingabe­verarbeitung ({@link #keyPressed(String)})</li>
 *   <li>Kollisionen, Interaktionen, Gegner-Ticks auslösen</li>
 *   <li>Zustands-Flags: {@link #istLevelGeschafft()}, {@link #istVerloren()}</li>
 *   <li>Rendering per {@link #toString()} inklusive Statuszeile und Log</li>
 * </ul>
 *
 * <h2>Spielschleife</h2>
 * <p>Eine Runde läuft so ab (siehe {@link GameMain#main}):
 * <ol>
 *   <li>Spielfeld zeichnen</li>
 *   <li>Tastatureingabe lesen</li>
 *   <li>{@link #keyPressed(String)} → Spieler­bewegung, Kachel-Interaktion</li>
 *   <li>{@link #tickGegner()} → jeder Gegner bewegt sich / greift an</li>
 *   <li>{@link #istLevelGeschafft()}? → Level wechseln</li>
 *   <li>{@link #istVerloren()}? → Spiel beenden</li>
 * </ol>
 * </p>
 *
 * @author hibbes
 */
public class World {

    /** Das statische Spielfeld (Wände, Items, Türen, Ziel). */
    public GameField field;

    /** Die Spielerfigur. */
    public Player player;

    /** Alle lebenden Gegner des aktuellen Levels. */
    public List<Enemy> gegner = new ArrayList<>();

    /** Name des aktuellen Levels (für die Status­zeile). */
    public String levelName = "";

    /** Wird auf {@code true} gesetzt, sobald der Spieler die Zielkachel erreicht. */
    private boolean levelGeschafft = false;

    /** Ring-Puffer für die letzten Ereignis­meldungen (für die Ausgabe unterhalb der Karte). */
    private final List<String> log = new ArrayList<>();

    /** Wie viele Log-Zeilen in {@link #toString()} eingeblendet werden. */
    private static final int LOG_LINES = 4;

    /**
     * Erzeugt eine Startwelt mit einem fest codierten Mini-Level.
     *
     * <p>Dieser Konstruktor bleibt als Abwärts­kompatibilität stehen –
     * im eigentlichen Spiel lädt {@link GameMain} stattdessen echte
     * {@link Level}-Definitionen über {@link #ladeLevel(Level)}.</p>
     */
    public World() {
        player = new Player();
        player.position = new Position(1, 1);
        field  = new GameField(7, 8,
                "###   #"
              + "#  # ##"
              + "##### #"
              + "## ### "
              + "###  ##"
              + " #### #"
              + "##### #"
              + " #### #");
        levelName = "Debug-Level";
    }

    /**
     * Lädt ein echtes {@link Level}-Objekt in die Welt.
     *
     * <p>Setzt das Spielfeld neu auf, spawnt die Gegner an ihren
     * Startpositionen, platziert den Spieler und setzt die
     * Zustands-Flags zurück. Trefferpunkte und Gold des Spielers
     * bleiben erhalten – der Spieler „nimmt" also seine Ausrüstung
     * ins nächste Level mit.</p>
     */
    public void ladeLevel(Level level) {
        this.field     = new GameField(level.width, level.height, level.layout);
        this.gegner    = level.erzeugeGegner();
        this.levelName = level.name;
        if (player == null) player = new Player();
        player.position = new Position(level.startX, level.startY);
        levelGeschafft = false;
        log.clear();
        log("Willkommen in: " + levelName);
    }

    // ── Status-Flags ──────────────────────────────────────────────────────────

    public boolean istLevelGeschafft() { return levelGeschafft; }
    public void setLevelGeschafft(boolean b) { levelGeschafft = b; }
    public boolean istVerloren() { return player == null || !player.lebt(); }

    // ── Log-Hilfsmethoden (Kachel-Callbacks rufen diese auf) ──────────────────

    /**
     * Hängt eine Meldung an den Log-Ring-Puffer. Die neueste Meldung ist
     * zuletzt; ältere fallen automatisch heraus.
     */
    public void log(String msg) {
        log.add(msg);
        while (log.size() > LOG_LINES) {
            log.remove(0);
        }
    }

    /**
     * Ersetzt eine Kachel durch eine neue. Wird von Items aufgerufen,
     * wenn sie beim Aufheben vom Spielfeld verschwinden sollen.
     */
    public void replaceTile(int x, int y, GameTile neu) {
        neu.position = new Position(x, y);
        field.Level[x][y] = neu;
    }

    // ── Kollisions- und Bewegungs-Helfer ──────────────────────────────────────

    /**
     * Prüft, ob ein Feld begehbar ist. Berücksichtigt Spielfeld­grenzen
     * <b>und</b> den {@link GameTile#isPassable()}-Wert der Kachel.
     */
    public boolean istBegehbar(int x, int y) {
        if (x < 0 || x >= field.width || y < 0 || y >= field.height) return false;
        return field.Level[x][y].isPassable();
    }

    /** @return {@code true}, wenn auf ({@code x},{@code y}) ein lebender Gegner steht */
    public boolean istGegnerAuf(int x, int y) {
        for (Enemy e : gegner) {
            if (e.lebt() && e.position.x == x && e.position.y == y) return true;
        }
        return false;
    }

    /** @return den Gegner an ({@code x},{@code y}) oder {@code null} */
    public Enemy gegnerAuf(int x, int y) {
        for (Enemy e : gegner) {
            if (e.lebt() && e.position.x == x && e.position.y == y) return e;
        }
        return null;
    }

    // ── Eingabeverarbeitung ───────────────────────────────────────────────────

    /**
     * Verarbeitet eine einzelne Tastatureingabe. Unterstützt WASD sowie das
     * Warten (Leertaste) – eine „Pass"-Runde, in der der Spieler stehen
     * bleibt, während sich die Gegner bewegen.
     *
     * @param s Eingabezeile (es wird nur das erste Zeichen ausgewertet)
     */
    public void keyPressed(String s) {
        if (s == null || s.isEmpty()) return;

        int dx = 0, dy = 0;
        switch (s.charAt(0)) {
            case 'w': dy = -1; break;
            case 's': dy =  1; break;
            case 'a': dx = -1; break;
            case 'd': dx =  1; break;
            case ' ': break;                 // Warten / Pass
            default:
                log("Unbekannte Taste: " + s.charAt(0));
                return;
        }

        int zielX = player.position.x + dx;
        int zielY = player.position.y + dy;

        // 1) Gegner auf dem Zielfeld? → angreifen, nicht bewegen
        Enemy e = gegnerAuf(zielX, zielY);
        if (e != null) {
            e.hp -= player.schaden;
            log("Du triffst E für " + player.schaden + " Schaden (E hat noch " + Math.max(0, e.hp) + " HP)");
            if (!e.lebt()) log("E besiegt!");
            return;
        }

        // 2) Verschlossene Tür vor uns? → versuchen zu öffnen
        if (zielX >= 0 && zielX < field.width && zielY >= 0 && zielY < field.height) {
            GameTile ziel = field.Level[zielX][zielY];
            if (ziel instanceof DoorTile) {
                DoorTile t = (DoorTile) ziel;
                if (!t.isPassable()) {
                    t.versucheZuOeffnen(player, this);
                    return;                  // Tür öffnen kostet eine Runde
                }
            }
        }

        // 3) Bewegung versuchen (Grenzen + Passierbarkeit)
        if (!istBegehbar(zielX, zielY)) {
            if (dx != 0 || dy != 0) log("Blockiert.");
            return;
        }

        player.position.x = zielX;
        player.position.y = zielY;

        // 4) Effekt der betretenen Kachel auslösen
        field.Level[zielX][zielY].onStep(player, this);
    }

    /**
     * Lässt alle Gegner eine Runde ziehen und räumt tote Gegner anschließend
     * aus der Liste. Wird nach jedem Spielerzug aufgerufen.
     */
    public void tickGegner() {
        for (Enemy e : gegner) {
            e.tick(this);
        }
        Iterator<Enemy> it = gegner.iterator();
        while (it.hasNext()) {
            if (!it.next().lebt()) it.remove();
        }
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    /**
     * Zeichnet das Spielfeld inkl. Status­zeile und den letzten Log-Meldungen.
     *
     * <p>Reihenfolge des Zeichnens:
     * <ol>
     *   <li>Status­zeile (HP, Gold, Schlüssel, Level-Name)</li>
     *   <li>Spalten­beschriftung</li>
     *   <li>Kacheln + Gegner + Spieler</li>
     *   <li>Log-Meldungen</li>
     * </ol>
     * </p>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // ── Statuszeile ─────────────────────────────────────────────
        sb.append(String.format(
            "HP %d/%d   Gold %d   Schlüssel %d   [%s]%n",
            player.hp, player.maxHp, player.gold, player.schluessel, levelName));

        // ── Spaltenköpfe ────────────────────────────────────────────
        sb.append("  ");
        for (int column = 0; column < field.width; column++) {
            sb.append(column % 10);
        }
        sb.append('\n');

        // ── Kacheln mit Gegnern + Spieler darüber gezeichnet ────────
        String flat = field.toString();
        for (Enemy e : gegner) {
            if (e.lebt()) flat = e.draw(flat, field.width);
        }
        flat = player.draw(flat, field.width);

        for (int row = 0; row < field.height; row++) {
            sb.append(row % 10).append(' ');
            sb.append(flat, row * field.width, (row + 1) * field.width);
            sb.append('\n');
        }

        // ── Log-Meldungen ───────────────────────────────────────────
        for (String msg : log) {
            sb.append("  ").append(msg).append('\n');
        }
        return sb.toString();
    }
}
