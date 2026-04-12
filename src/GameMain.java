import java.io.IOException;
import java.util.Scanner;

/**
 * Einstiegspunkt und Haupt-Spielschleife des ASCII-Doom-Spiels.
 *
 * <h2>Spielprinzip</h2>
 * <p>Der Spieler bewegt sich durch eine Abfolge kleiner Konsolen-Levels,
 * sammelt Gold, findet Schlüssel, öffnet Türen, kämpft gegen Gegner und
 * muss zum Ziel-Feld ({@code X}) laufen, um ins nächste Level zu kommen.</p>
 *
 * <h2>Steuerung</h2>
 * <ul>
 *   <li>{@code w} – hoch</li>
 *   <li>{@code s} – runter</li>
 *   <li>{@code a} – links</li>
 *   <li>{@code d} – rechts</li>
 *   <li>{@code Leerzeichen} – warten (lässt die Gegner ziehen, ohne selbst zu laufen)</li>
 *   <li>{@code q} – Spiel beenden</li>
 * </ul>
 *
 * <h2>Spielschleife (Game Loop)</h2>
 * <p>Das Muster „Zeichnen → Eingabe → Update → Gegner-Tick → Wiederholen"
 * ist die Grundlage jedes rundenbasierten Spiels. Diese Implementierung
 * blockiert bei jeder Eingabe auf die nächste Zeile – es gibt also keine
 * „Echtzeit", sondern pro Spieler-Eingabe zieht genau einmal die Welt.</p>
 *
 * @author hibbes
 */
public class GameMain {

    /**
     * Startet das Spiel.
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     * @throws IOException wenn die Konsoleneingabe fehlschlägt
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Level[]  levels = Level.allLevels();
        int      levelIdx = 0;
        World    world = new World();
        world.ladeLevel(levels[levelIdx]);

        druckeIntro();
        System.out.println(world);

        while (true) {
            if (!scanner.hasNextLine()) break;
            String key = scanner.nextLine();

            // Spielabbruch
            if ("q".equals(key)) {
                System.out.println("Du gibst auf. Auf Wiedersehen!");
                break;
            }

            // 1) Spielereingabe verarbeiten
            world.keyPressed(key);

            // 2) Gegner ziehen (nur wenn Level noch läuft)
            if (!world.istLevelGeschafft() && !world.istVerloren()) {
                world.tickGegner();
            }

            // 3) Spielzustand neu zeichnen
            System.out.println(world);

            // 4) Level geschafft?
            if (world.istLevelGeschafft()) {
                levelIdx++;
                if (levelIdx >= levels.length) {
                    druckeGewonnen(world);
                    break;
                }
                System.out.println("═══ Level geschafft! Weiter zu " + levels[levelIdx].name + " ═══");
                world.ladeLevel(levels[levelIdx]);
                System.out.println(world);
            }

            // 5) Tot?
            if (world.istVerloren()) {
                druckeGameOver(world);
                break;
            }
        }
    }

    // ── Hilfs-Ausgaben ────────────────────────────────────────────────────────

    private static void druckeIntro() {
        System.out.println("════════════════════════════════════════════");
        System.out.println("   ASCIIDoom12 – Konsolen-Dungeon-Crawler");
        System.out.println("════════════════════════════════════════════");
        System.out.println("  Bewegung : w a s d");
        System.out.println("  Warten   : Leertaste");
        System.out.println("  Beenden  : q");
        System.out.println();
        System.out.println("  @ Du    # Wand   E Gegner   $ Gold");
        System.out.println("  * HP    k Key    + Tür      X Ziel");
        System.out.println("════════════════════════════════════════════");
        System.out.println();
    }

    private static void druckeGameOver(World world) {
        System.out.println();
        System.out.println("    ╔════════════════════════════╗");
        System.out.println("    ║        GAME OVER           ║");
        System.out.println("    ║  Du wurdest besiegt.       ║");
        System.out.println("    ╚════════════════════════════╝");
        System.out.println("    Gesammeltes Gold: " + world.player.gold);
    }

    private static void druckeGewonnen(World world) {
        System.out.println();
        System.out.println("    ╔════════════════════════════╗");
        System.out.println("    ║       GEWONNEN!            ║");
        System.out.println("    ║  Du hast alle Level        ║");
        System.out.println("    ║  abgeschlossen.            ║");
        System.out.println("    ╚════════════════════════════╝");
        System.out.println("    Endstand: " + world.player.gold + " Gold, "
                         + world.player.hp + "/" + world.player.maxHp + " HP");
    }
}
