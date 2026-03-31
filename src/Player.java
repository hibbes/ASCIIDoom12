/**
 * Der Spieler – eine bewegliche Kachel, die mit WASD gesteuert wird.
 *
 * <p>Der Spieler erbt von {@link GameTile}: Er ist wie eine Kachel, hat eine
 * Position im Spielfeld und kann sich selbst einzeichnen. Zusätzlich hat er
 * Bewegungsmethoden.</p>
 *
 * <p><b>Wichtig:</b> Die Bewegungsmethoden ändern nur die Position, ohne
 * Kollisionen zu prüfen. Die Kollisionsprüfung findet in {@link World#keyPressed(String)}
 * statt: Wenn der Spieler nach dem Bewegen auf einer Wand steht, wird die Bewegung
 * rückgängig gemacht.</p>
 *
 * @author hibbes
 * @see World
 * @see GameTile
 */
class Player extends GameTile {

    /**
     * @return {@code "@"} – Symbol des Spielers auf dem Spielfeld
     */
    @Override
    public String toString() {
        return "@";
    }

    /** Bewegt den Spieler eine Einheit nach links (x nimmt ab). */
    public void moveLeft()  { position.x--; }

    /** Bewegt den Spieler eine Einheit nach rechts (x nimmt zu). */
    public void moveRight() { position.x++; }

    /** Bewegt den Spieler eine Einheit nach oben (y nimmt ab – y=0 ist oben). */
    public void moveUp()    { position.y--; }

    /** Bewegt den Spieler eine Einheit nach unten (y nimmt zu). */
    public void moveDown()  { position.y++; }
}
