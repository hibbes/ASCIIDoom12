import java.util.ArrayList;
import java.util.List;

/**
 * Definition eines einzelnen Levels – Layout, Start­position und Gegner.
 *
 * <p>Eine Levelinstanz ist ein reines Daten­objekt ohne Spiellogik:
 * sie kennt nur ihre Map und die Start­bedingungen. Die {@link World}
 * „frisst" ein {@code Level} und erzeugt daraus ihren Spielzustand.</p>
 *
 * <p>Gegner werden als {@code (x, y, hp, schaden)}-Tupel abgelegt und
 * beim Laden des Levels instanziert – so kann ein Level klonbar in
 * den Anfangs­zustand zurückgesetzt werden (praktisch, wenn der
 * Spieler stirbt und das Level neu startet).</p>
 *
 * @author hibbes
 */
public class Level {

    public final String name;
    public final int width;
    public final int height;
    public final String layout;
    public final int startX;
    public final int startY;
    private final List<int[]> gegnerDefs = new ArrayList<>();

    public Level(String name, int width, int height, String layout, int startX, int startY) {
        this.name   = name;
        this.width  = width;
        this.height = height;
        this.layout = layout;
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * Fügt einen Gegner-Eintrag hinzu.
     *
     * @param x       Start-Spalte
     * @param y       Start-Zeile
     * @param hp      Trefferpunkte
     * @param schaden Schaden pro Angriff
     * @return {@code this} für Method-Chaining
     */
    public Level mitGegner(int x, int y, int hp, int schaden) {
        gegnerDefs.add(new int[] { x, y, hp, schaden });
        return this;
    }

    /**
     * Erzeugt eine frische Liste Gegner-Objekte für diesen Level.
     * Jeder Aufruf liefert neue Instanzen – das macht Level-Resets trivial.
     */
    public List<Enemy> erzeugeGegner() {
        List<Enemy> liste = new ArrayList<>();
        for (int[] def : gegnerDefs) {
            liste.add(new Enemy(def[0], def[1], def[2], def[3]));
        }
        return liste;
    }

    // ── Fertige Beispiel-Level ────────────────────────────────────────────────
    //
    // Drei Level mit steigendem Schwierigkeits­grad. Die Layouts sind je
    // 12 × 8 Zeichen – groß genug, dass sich „Bewegung" lohnt, aber klein
    // genug für den Konsolen­anzeige­bereich.

    public static Level level1() {
        String map =
            "############" +
            "#@  $     X#" +
            "#  ####    #" +
            "#  #  #  $ #" +
            "#  #  #    #" +
            "#       E  #" +
            "#  $    *  #" +
            "############";
        return new Level("Level 1 – Einstieg", 12, 8, map.replace('@', ' ').replace('E', ' '), 1, 1)
                .mitGegner(8, 5, 30, 10);
    }

    public static Level level2() {
        // Schlüssel liegt links, Tür öffnet zur rechten Hälfte, Ziel rechts.
        // Der Spieler muss zuerst den Schlüssel holen und kommt erst dann
        // durch die Tür (col 6, row 3) in die rechte Kammer mit Ziel X.
        String map =
            "############" +
            "#     #   X#" +
            "#  k  #    #" +
            "# ### +    #" +
            "#  $  #  $ #" +
            "#     #  E #" +
            "#  *  #  $ #" +
            "############";
        return new Level("Level 2 – Tür & Schlüssel", 12, 8, map.replace('@', ' ').replace('E', ' '), 1, 1)
                .mitGegner(9, 5, 40, 15);
    }

    public static Level level3() {
        String map =
            "############" +
            "#@ $ E $ $ #" +
            "# ## ## ## #" +
            "# k  +  *  #" +
            "# ## +# ## #" +
            "# E  k  E  #" +
            "# $$$ * $$X#" +
            "############";
        return new Level("Level 3 – Finale", 12, 8, map.replace('@', ' ').replace('E', ' '), 1, 1)
                .mitGegner(5, 1, 30, 10)
                .mitGegner(2, 5, 50, 15)
                .mitGegner(9, 5, 50, 15);
    }

    /** @return alle verfügbaren Level in Reihenfolge */
    public static Level[] allLevels() {
        return new Level[] { level1(), level2(), level3() };
    }
}
