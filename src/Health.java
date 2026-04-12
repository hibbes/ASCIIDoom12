/**
 * Heil-Potion – stellt dem Spieler Trefferpunkte wieder her.
 *
 * <p>Darstellung: {@code *}. Heilt den Spieler beim Aufheben um {@link #HEILUNG}
 * Trefferpunkte, kann aber nicht über sein {@link Player#maxHp Maximum} hinaus
 * aufgeladen werden.</p>
 *
 * @author hibbes
 * @see GameTile
 */
public class Health extends GameTile {

    /** Anzahl der Trefferpunkte, die eine einzelne Potion heilt. */
    public static final int HEILUNG = 20;

    /** Heilung kann immer betreten werden. */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Spieler heilen (nicht über das Maximum hinaus) und Kachel entfernen.
     */
    @Override
    public void onStep(Player player, World world) {
        int alt  = player.hp;
        player.hp = Math.min(player.hp + HEILUNG, player.maxHp);
        int geheilt = player.hp - alt;
        world.log("+ " + geheilt + " HP (jetzt " + player.hp + "/" + player.maxHp + ")");
        world.replaceTile(position.x, position.y, new EmptyTile());
    }

    /** @return {@code "*"} – Symbol der Heilpotion */
    @Override
    public String toString() {
        return "*";
    }
}
