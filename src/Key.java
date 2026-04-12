/**
 * Schlüssel – öffnet später eine {@link DoorTile}.
 *
 * <p>Darstellung: {@code k}. Beim Aufheben wird der Schlüssel­zähler des
 * Spielers erhöht und die Kachel durch eine {@link EmptyTile} ersetzt.
 * Der Schlüssel verschwindet erst dann aus dem Inventar, wenn er verbraucht
 * wird ({@link DoorTile#onStep(Player, World)}).</p>
 *
 * @author hibbes
 * @see DoorTile
 */
public class Key extends GameTile {

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public void onStep(Player player, World world) {
        player.schluessel++;
        world.log("Schlüssel aufgehoben (" + player.schluessel + " insgesamt)");
        world.replaceTile(position.x, position.y, new EmptyTile());
    }

    /** @return {@code "k"} – Schlüssel-Symbol */
    @Override
    public String toString() {
        return "k";
    }
}
