# ASCIIDoom12

Konsolen-Dungeon-Crawler im Terminal – ein rundenbasierter Mini-Roguelike mit Gegnern, Items, Türen, mehreren Levels und einer echten Spielschleife.

## Spielen

```bash
cd src
javac -d build *.java
java -cp build GameMain
```

Dann Bewegung per `w` / `a` / `s` / `d` (jeweils mit Enter bestätigen), `Leertaste` zum Warten, `q` zum Beenden.

```
════════════════════════════════════════════
   ASCIIDoom12 – Konsolen-Dungeon-Crawler
════════════════════════════════════════════
  @ Du    # Wand   E Gegner   $ Gold
  * HP    k Key    + Tür      X Ziel
════════════════════════════════════════════

HP 100/100   Gold 0   Schlüssel 0   [Level 1 – Einstieg]
  012345678901
0 ############
1 #@  $     X#
2 #  ####    #
3 #  #  #  $ #
4 #  #  #    #
5 #       E  #
6 #  $    *  #
7 ############
```

**Steuerung:** `w` ↑  `a` ←  `s` ↓  `d` →  `Space` warten  `q` beenden

## Spielmechanik

- **Items aufsammeln:** Wer auf ein `$` läuft, bekommt Gold; auf ein `*`, wird geheilt; auf ein `k`, bekommt einen Schlüssel.
- **Türen:** Eine geschlossene Tür `+` wirkt wie eine Wand. Ein Schritt in die Tür verbraucht einen Schlüssel und öffnet sie (`/`).
- **Gegner:** `E`-Gegner patroullieren, greifen an, wenn sie direkt neben dem Spieler stehen, und verursachen Schaden. Der Spieler kann zurückschlagen, indem er in sie hineinläuft.
- **Level-Ausgang:** Das `X` beendet das aktuelle Level und lädt das nächste. Wer alle drei Level schafft, gewinnt das Spiel.
- **Game Over:** Sinken die Trefferpunkte des Spielers auf 0, ist das Spiel vorbei.

## Architektur

```
GameMain                 Spielschleife: Zeichnen → Eingabe → Tick → wiederholen
   │
   ▼
World                    Hält Spielzustand und steuert das Spielgeschehen
├── GameField            Statisches Spielfeld (2D-Array aus GameTile)
├── Player               Bewegliche GameTile mit HP, Gold, Schlüsseln
└── List<Enemy>          Gegner mit eigenem Tick

GameTile (Basis)         isPassable(), onStep(player, world), draw(...)
├── EmptyTile            " "   begehbar, neutral
├── WallTile             "#"   blockiert
├── DoorTile             "+"→"/"  verschlossen → geöffnet (verbraucht Schlüssel)
├── GoalTile             "X"   Level-Ausgang
├── Gold                 "$"   Item: +10 Gold
├── Health               "*"   Item: +20 HP (bis Maximum)
├── Key                  "k"   Item: +1 Schlüssel
├── Enemy                "E"   Patrol-AI, Nahkampfschaden
└── Player               "@"   vom Benutzer gesteuert

Position                 2D-Koordinate + Umrechnung in 1D-Stringindex
Level                    Level-Definition (Layout + Gegner-Spawns)
```

## Didaktische Konzepte

- **Vererbung**: Jeder Tile-Typ erbt `draw()` aus `GameTile` und überschreibt nur `toString()` sowie bei Bedarf `isPassable()` / `onStep()`.
- **Template-Method-Pattern**: `onStep(Player, World)` ist ein Template, das jede Kachel selbst befüllen kann (Gold erhöht den Zähler, Health heilt, Goal setzt ein Flag, …). Die `World` muss dafür keine `if/else`-Kaskade nach Kacheltyp pflegen.
- **Factory-Methode**: `GameField.createTile(char)` wandelt ein Zeichen aus dem Level-String in das passende Objekt um – eine einzige Stelle, an der neue Kacheltypen registriert werden müssen.
- **Komposition vs. Vererbung**: `World` *hat ein* `GameField` und einen `Player`, aber *ist kein* Spielfeld – ein kanonisches Beispiel für "Komposition statt Vererbung".
- **Spielschleife (Game Loop)**: Das Zeichnen/Eingabe/Update/Wiederholen-Muster liegt jedem Spiel zugrunde, hier in seiner einfachsten rundenbasierten Form.
- **Polymorphismus in der Praxis**: In `World.keyPressed` wird `field.Level[x][y].onStep(player, this)` aufgerufen, ohne zu wissen, ob die Kachel ein Item, eine Tür oder sonst etwas ist – zur Laufzeit wird die richtige Methode gewählt.
- **Patrol-AI**: `Enemy.tick()` zeigt exemplarisch einfache Gegner-KI (Richtung merken, bei Blockade umdrehen, bei Spieler-Nachbarschaft angreifen).

## Lernziele

- Tile-basiertes Spieldesign und Spielfeld-Repräsentation
- Vererbung, `@Override`, `instanceof` (als Sonderfall bei der Tür)
- Komposition von Objekten zu einer Spielwelt
- Entwurf einer einfachen Spielschleife mit Eingabe, Update und Rendering
- Polymorphe Interaktionen statt `switch/case`-Logik
- Datengetriebene Levelgestaltung (Layout als String)
- Zustands­management (Level-geschafft-Flag, HP, Inventar)

## Erweiterungsideen

- Sichtfeld / Fog of War (nur Kacheln in Radius N anzeigen)
- Reichweiten­waffen (Bogen, Schusslinien prüfen)
- Mehr Gegnertypen mit unterschiedlichen Bewegungsmustern
- Random-generierte Dungeons statt fester Level-Strings
- Speichern/Laden des Spielzustands
- Farbausgabe mit ANSI-Escape-Codes
- Soundeffekte über Java-System-Beep oder externe Library
