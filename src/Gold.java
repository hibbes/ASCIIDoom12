/**
 * Gold-Item – gibt dem Spieler Punkte und verschwindet beim Aufheben.
 *
 * <p>Gold wird im Level mit dem Zeichen {@code $} dargestellt. Sobald der
 * Spieler ein Feld mit Gold betritt, wird sein Goldbetrag erhöht und die
 * Kachel durch eine {@link EmptyTile} ersetzt – das Gold ist weg.</p>
 *
 * @author hibbes
 * @see GameTile
 */
public class Gold extends GameTile {

    /** Wert dieses Goldstücks (in „Punkten"). */
    public static final int WERT = 10;

    /** Gold kann betreten werden – ohne Begrenzung. */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Gold dem Spieler gutschreiben und die Kachel entfernen.
     *
     * <p>Der „Trick": Die Welt speichert eine Kopie dieser Kachel im
     * Spielfeld. Beim Aufheben wird sie durch eine {@link EmptyTile}
     * ersetzt, damit das Gold nicht zweimal eingesammelt werden kann.</p>
     */
    @Override
    public void onStep(Player player, World world) {
        player.gold += WERT;
        world.log("+ " + WERT + " Gold (" + player.gold + " gesamt)");
        world.replaceTile(position.x, position.y, new EmptyTile());
    }

    /** @return {@code "$"} – Gold-Symbol */
    @Override
    public String toString() {
        return "$";
    }
}
