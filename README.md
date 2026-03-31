# ASCIIDoom12

Konsolenbasiertes Tile-Dungeon-Spiel – ein Mini-"Doom" im Terminal, gesteuert mit WASD.

## Spielen

Kompilieren und starten, dann WASD + Enter eingeben, `q` zum Beenden.

```
\ 0123456
0 ###   #
1 #@ # ##
2 ##### #
3 ## ### 
...
```

**Steuerung:** `w` ↑  `a` ←  `s` ↓  `d` →  `q` beenden

## Architektur

```
World
├── GameField   ← 2D-Array aus GameTile-Objekten
│   ├── WallTile    ("#")
│   └── EmptyTile   (" ")
└── Player      ← bewegliche GameTile ("@")
```

## Wichtige Konzepte

**Level-Encoding:** Das Level wird als String gespeichert. Jedes Zeichen = eine Kachel.
Index-Formel: `i = x + y * breite`

**Vererbung:** `EmptyTile`, `WallTile`, `Player` erben alle von `GameTile`. Jede Klasse
überschreibt nur `toString()` – die Zeichenlogik ist einmal in `GameTile.draw()` implementiert.

**Kollisionsprüfung** (Bug-Fix in v1.1): Nach jeder Bewegung wird `checkCollision()` aufgerufen.
Steht der Spieler auf einer Wand, wird die Bewegung rückgängig gemacht.

## Lernziele

- Tile-basiertes Spieldesign
- Vererbung und `@Override`
- Komposition (World *hat ein* GameField und einen Player)
- String-Immutability: `draw()` wandelt in `char[]` um und zurück
- Game Loop als grundlegendes Spielprinzip

## Erweiterungsideen

- Mehrere Level (Level-Wechsel bei Erreichen eines Ausgangs)
- Gegner mit einfacher KI
- Schatzkarten / Schlüssel-Tür-Mechanik
- Sichtfeld (Fog of War)
