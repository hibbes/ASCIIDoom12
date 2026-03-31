/**
 * Wand-Kachel – unpassierbares Hindernis.
 *
 * <p>Gibt ein Rautezeichen zurück: Der Spieler kann sich nicht auf dieses Feld bewegen.
 * Die Kollisionsprüfung in {@link World#checkCollision(int, int)} verhindert das Betreten.</p>
 *
 * @author hibbes
 * @see GameTile
 * @see EmptyTile
 */
public class WallTile extends GameTile {

    /**
     * @return {@code "#"} – Wand, unpassierbar
     */
    @Override
    public String toString() {
        return "#";
    }
}
