/**
 * Ziel-Kachel – der Ausgang des aktuellen Levels.
 *
 * <p>Sobald der Spieler auf eine {@code GoalTile} läuft, wird das Level als
 * „geschafft" markiert. Die Spielschleife in {@link GameMain} erkennt das
 * am Zustand der {@link World} und lädt das nächste Level (oder beendet
 * das Spiel, wenn es keins mehr gibt).</p>
 *
 * @author hibbes
 * @see World#istLevelGeschafft()
 */
public class GoalTile extends GameTile {

    /** Ziel darf natürlich betreten werden. */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Markiert das aktuelle Level als abgeschlossen.
     *
     * <p>Die Welt kümmert sich darum, ob jetzt ein neues Level geladen oder
     * das Spiel beendet wird – hier wird nur das Flag gesetzt.</p>
     */
    @Override
    public void onStep(Player player, World world) {
        world.log("🏁 Ziel erreicht!");
        world.setLevelGeschafft(true);
    }

    /** @return {@code "X"} – Zielsymbol */
    @Override
    public String toString() {
        return "X";
    }
}
