/**
 * Wand-Kachel – unpassierbares Hindernis.
 *
 * <p>Wände blockieren Spieler und Gegner. Die Kollisionsprüfung erfolgt
 * direkt über {@link #isPassable()}, das {@code false} zurückgibt.</p>
 *
 * @author hibbes
 * @see GameTile
 * @see EmptyTile
 */
public class WallTile extends GameTile {

    /** Wände sind nie betretbar. */
    @Override
    public boolean isPassable() {
        return false;
    }

    /** @return {@code "#"} – Wand */
    @Override
    public String toString() {
        return "#";
    }
}
