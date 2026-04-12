/**
 * Leere Kachel – begehbares, neutrales Feld.
 *
 * <p>Hat keine Seiteneffekte beim Betreten. Erbt die Default-Implementierungen
 * von {@link GameTile#isPassable()} ({@code true}) und
 * {@link GameTile#onStep(Player, World)} (leer).</p>
 *
 * @author hibbes
 * @see GameTile
 * @see WallTile
 */
public class EmptyTile extends GameTile {

    /** @return {@code " "} – leeres Feld, begehbar */
    @Override
    public String toString() {
        return " ";
    }
}
