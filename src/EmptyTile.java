/**
 * Leere Kachel – begehbares, freies Feld.
 *
 * <p>Gibt ein Leerzeichen zurück: Der Spieler kann sich auf diesem Feld bewegen.</p>
 *
 * @author hibbes
 * @see GameTile
 * @see WallTile
 */
public class EmptyTile extends GameTile {

    /**
     * @return {@code " "} – leeres Feld, begehbar
     */
    @Override
    public String toString() {
        return " ";
    }
}
