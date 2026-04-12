/**
 * Ein Gegner – bewegt sich nach jeder Spielrunde und greift den Spieler an,
 * sobald er auf dem Nachbarfeld steht.
 *
 * <p>Gegner sind selbst {@link GameTile}-Objekte, werden aber nicht im
 * Spielfeld-Grid gespeichert, sondern in einer separaten Liste in der
 * {@link World}. Das vereinfacht die Bewegung (keine Tile-Swaps) und
 * entspricht der Art, wie der Spieler gehandhabt wird.</p>
 *
 * <h2>KI-Verhalten</h2>
 * <p>Vereinfachtes Patrol-Muster: Der Gegner merkt sich seine aktuelle
 * Bewegungs­richtung und läuft stur in diese Richtung. Stößt er an eine Wand
 * oder ans Spielfeldende, dreht er um (180°). Wenn er sich direkt neben dem
 * Spieler befindet, greift er <b>stattdessen</b> an, statt sich zu bewegen.</p>
 *
 * <p>Das ist natürlich eine sehr einfache KI – aber didaktisch nützlich,
 * weil sie alle wichtigen Bausteine zeigt: Zustand (Richtung), Wahrnehmung
 * (Spielerposition), Entscheidung (Angreifen vs. Laufen), Reaktion auf
 * die Umgebung (Wand → Umkehren).</p>
 *
 * @author hibbes
 */
public class Enemy extends GameTile {

    /** Bewegungs­richtungen. */
    public enum Richtung { LINKS, RECHTS, HOCH, RUNTER }

    /** Aktuelle Bewegungsrichtung. */
    private Richtung richtung;

    /** Trefferpunkte des Gegners – bei 0 ist er besiegt. */
    public int hp;

    /** Schaden pro Angriff. */
    public final int schaden;

    /**
     * Erzeugt einen Gegner an der angegebenen Position.
     *
     * @param x        Start-Spalte
     * @param y        Start-Zeile
     * @param hp       Trefferpunkte
     * @param schaden  Schaden pro Angriff auf den Spieler
     */
    public Enemy(int x, int y, int hp, int schaden) {
        super(new Position(x, y));
        this.hp       = hp;
        this.schaden  = schaden;
        this.richtung = Richtung.RECHTS;   // Default-Richtung
    }

    /** @return {@code true}, wenn der Gegner noch lebt */
    public boolean lebt() {
        return hp > 0;
    }

    /**
     * Wird 1× pro Runde von der {@link World} aufgerufen. Der Gegner
     * entscheidet, ob er angreift oder sich bewegt.
     */
    public void tick(World world) {
        if (!lebt()) return;

        Player p = world.player;

        // 1) Angreifen, wenn der Spieler auf einem Nachbarfeld steht
        if (istNachbarVon(p)) {
            p.hp -= schaden;
            world.log("E greift an! Du verlierst " + schaden + " HP (jetzt " + p.hp + ")");
            return;
        }

        // 2) Sonst: ein Feld in aktueller Richtung gehen
        int nx = position.x;
        int ny = position.y;
        switch (richtung) {
            case LINKS:  nx--; break;
            case RECHTS: nx++; break;
            case HOCH:   ny--; break;
            case RUNTER: ny++; break;
        }

        if (world.istBegehbar(nx, ny) && !world.istGegnerAuf(nx, ny) &&
            !(p.position.x == nx && p.position.y == ny)) {
            position.x = nx;
            position.y = ny;
        } else {
            // Weg blockiert → umdrehen. In der nächsten Runde wird es erneut versucht.
            richtung = gegenrichtung(richtung);
        }
    }

    /** Prüft Manhattan-Nachbarschaft mit dem Spieler. */
    private boolean istNachbarVon(Player p) {
        int dx = Math.abs(position.x - p.position.x);
        int dy = Math.abs(position.y - p.position.y);
        return (dx + dy) == 1;
    }

    /** Liefert die entgegengesetzte Richtung. */
    private Richtung gegenrichtung(Richtung r) {
        switch (r) {
            case LINKS:  return Richtung.RECHTS;
            case RECHTS: return Richtung.LINKS;
            case HOCH:   return Richtung.RUNTER;
            case RUNTER: return Richtung.HOCH;
            default:     return r;
        }
    }

    /** @return {@code "E"} – Gegner-Symbol */
    @Override
    public String toString() {
        return "E";
    }
}
