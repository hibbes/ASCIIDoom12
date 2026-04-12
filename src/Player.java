/**
 * Der Spieler – eine bewegliche Kachel, die mit WASD gesteuert wird und
 * einen Zustand (HP, Gold, Schlüssel) mit sich herumträgt.
 *
 * <p>Erbt von {@link GameTile}: der Spieler hat eine Position im Spielfeld
 * und kann sich selbst einzeichnen. Zusätzlich besitzt er:
 * <ul>
 *   <li>{@link #hp} / {@link #maxHp}  – Trefferpunkte (Tod bei 0)</li>
 *   <li>{@link #gold}                – gesammelte Punkte</li>
 *   <li>{@link #schluessel}          – Schlüssel im Inventar</li>
 *   <li>{@link #schaden}             – Schaden, den er im Nahkampf austeilt</li>
 * </ul>
 * </p>
 *
 * <p>Die Bewegungs­methoden ändern nur die Koordinate. Die eigentliche
 * Kollisions- und Interaktions­logik steckt in {@link World#keyPressed(String)}.
 * Wer die Kollision dort und die Seiten­effekte in {@link GameTile#onStep}
 * sauber getrennt hält, kann beliebige neue Kacheln einführen, ohne die
 * Spielschleife anfassen zu müssen.</p>
 *
 * @author hibbes
 * @see World
 * @see GameTile
 */
public class Player extends GameTile {

    /** Maximale Trefferpunkte. */
    public final int maxHp;

    /** Aktuelle Trefferpunkte. Tot bei {@code hp <= 0}. */
    public int hp;

    /** Eingesammeltes Gold. */
    public int gold;

    /** Anzahl der Schlüssel im Inventar. */
    public int schluessel;

    /** Schaden, den der Spieler einem Gegner zufügt, wenn er in ihn hineinläuft. */
    public final int schaden;

    /**
     * Erzeugt einen Spieler mit Standardwerten (100 HP, 20 Schaden).
     */
    public Player() {
        this(100, 20);
    }

    /**
     * Erzeugt einen Spieler mit frei wählbaren Startwerten.
     */
    public Player(int maxHp, int schaden) {
        this.maxHp    = maxHp;
        this.hp       = maxHp;
        this.schaden  = schaden;
        this.gold     = 0;
        this.schluessel = 0;
    }

    /** @return {@code true}, wenn der Spieler noch lebt */
    public boolean lebt() {
        return hp > 0;
    }

    /** @return {@code "@"} – Symbol des Spielers */
    @Override
    public String toString() {
        return "@";
    }

    // ── Bewegungsmethoden ────────────────────────────────────────────────────
    // Die Methoden verändern nur die Position, ohne Kollisionsprüfung.
    // Koordinatensystem: x wächst nach rechts, y wächst nach unten.

    /** Bewegt den Spieler eine Einheit nach links. */
    public void moveLeft()  { position.x--; }

    /** Bewegt den Spieler eine Einheit nach rechts. */
    public void moveRight() { position.x++; }

    /** Bewegt den Spieler eine Einheit nach oben (y nimmt ab). */
    public void moveUp()    { position.y--; }

    /** Bewegt den Spieler eine Einheit nach unten. */
    public void moveDown()  { position.y++; }
}
