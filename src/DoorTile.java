/**
 * Tür – eine Wand, die sich mit einem {@link Key Schlüssel} öffnen lässt.
 *
 * <p>Eine Tür wechselt zwischen zwei Zuständen:
 * <ul>
 *   <li><b>geschlossen</b>: verhält sich wie eine Wand, {@link #isPassable()}
 *       liefert {@code false}. Darstellung: {@code +}.</li>
 *   <li><b>offen</b>: begehbar wie ein leeres Feld. Darstellung: {@code /}.</li>
 * </ul>
 * Das Umschalten passiert in {@link #onStep(Player, World)} – allerdings
 * betritt der Spieler die Tür ja erst, nachdem {@link #isPassable()} schon
 * {@code true} zurückgegeben hat. Deshalb „entriegelt" die Welt die Tür
 * bereits beim Bewegungsversuch (siehe {@link World#keyPressed(String)}),
 * und {@code onStep} macht hier nichts Besonderes mehr.</p>
 *
 * <p><b>Trick mit dem Schlüssel:</b> Beim Öffnen wird ein Schlüssel aus dem
 * Inventar des Spielers „verbraucht" – also der Zähler {@link Player#schluessel}
 * um 1 verringert. Die Tür bleibt danach dauerhaft offen.</p>
 *
 * @author hibbes
 * @see Key
 */
public class DoorTile extends GameTile {

    /** {@code true}, sobald die Tür mit einem Schlüssel entriegelt wurde. */
    private boolean offen = false;

    /**
     * Versucht, die Tür mit einem Schlüssel aus dem Inventar zu entriegeln.
     *
     * @param player der Spieler (Schlüssel-Zähler wird ggf. reduziert)
     * @param world  für Statusmeldungen
     * @return {@code true}, wenn die Tür jetzt offen ist (entweder war sie
     *         es schon oder ein Schlüssel wurde verbraucht), sonst {@code false}
     */
    public boolean versucheZuOeffnen(Player player, World world) {
        if (offen) return true;
        if (player.schluessel > 0) {
            player.schluessel--;
            offen = true;
            world.log("Tür geöffnet (noch " + player.schluessel + " Schlüssel)");
            return true;
        }
        world.log("Verschlossen – du brauchst einen Schlüssel.");
        return false;
    }

    /** Tür ist nur passierbar, wenn sie offen ist. */
    @Override
    public boolean isPassable() {
        return offen;
    }

    /** @return {@code "/"} wenn offen, sonst {@code "+"} */
    @Override
    public String toString() {
        return offen ? "/" : "+";
    }
}
