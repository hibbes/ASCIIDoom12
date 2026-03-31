import java.io.IOException;
import java.util.Scanner;

/**
 * Einstiegspunkt und Spielschleife des ASCII-Doom-Spiels.
 *
 * <p><b>Spielprinzip:</b><br>
 * Das Spiel läuft in einer Endlosschleife in der Konsole. Nach jeder Tastatureingabe
 * wird die Spielwelt neu gerendert und ausgegeben.</p>
 *
 * <p><b>Steuerung:</b>
 * <ul>
 *   <li>{@code w} – hoch</li>
 *   <li>{@code s} – runter</li>
 *   <li>{@code a} – links</li>
 *   <li>{@code d} – rechts</li>
 *   <li>{@code q} – Spiel beenden</li>
 * </ul>
 * </p>
 *
 * <p><b>Spielschleife (Game Loop):</b><br>
 * Das Muster "Zeichnen → Eingabe lesen → Zustand aktualisieren → Wiederholen"
 * ist die Grundlage jedes Spiels. Hier in seiner einfachsten Form:
 * Die Schleife wartet auf eine Eingabe und setzt sie sofort um.</p>
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
        World w = new World();

        // Startansicht: Welt einmal ausgeben
        System.out.println(w);

        // Erste Eingabe lesen
        String key = scanner.nextLine();

        // Spielschleife: läuft bis der Spieler 'q' drückt
        while (!"q".equals(key)) {
            w.keyPressed(key);          // Eingabe verarbeiten, Spieler bewegen
            System.out.println(w);      // aktualisierten Zustand ausgeben

            // Nächste Eingabe (Schleife abbrechen wenn keine weiteren Zeilen)
            if (scanner.hasNextLine()) {
                key = scanner.nextLine();
            } else {
                break;
            }
        }

        System.out.println("Spiel beendet.");
    }
}
