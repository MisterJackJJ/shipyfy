# shipyfy

Schiffe-Versenken in Java (Swing-GUI + Konsole).

## Übersicht

| Klasse | Aufgabe |
|---|---|
| `Cell` | Zellzustände mit Unicode-Symbol und Bild-Dateinamen |
| `Direction` | Ausrichtung (`HORIZONTAL`, `VERTICAL`) |
| `ShipPart` | einzelnes Schiffsfeld mit Position und Treffer-Flag |
| `Ship` | Schiff als `ShipPart[]`, Randprüfung im Konstruktor |
| `Grid` | 2D-Array der Zellzustände mit Bereichsprüfung |
| `Model` | Spiellogik: Platzieren, Schießen, Versenkt-Markierung |
| `ModelView` | Konsole-Ausgabe (zeilenweise) |
| `SwingView` | Grafische Ansicht (JFrame mit JButton pro Zelle, Klick = Schuss) |
| `Game` | Spielablauf: verbindet Model und Views |
| `Main` | Einstiegspunkt |
| `ModelTest` | Assertion-Tests |

## Bauen und Starten

```bash
javac -d out src/*.java
java -cp out:resources Main        # Linux/macOS
java -cp out;resources Main       # Windows
```

Wichtig: `resources` muss im Classpath stehen, damit die PNG-Icons geladen werden.
Fehlen die Bilder, startet das Spiel automatisch im Unicode-Fallback-Modus.

## Bilder (resources/)

- Statische Icons: `NAME_00.png` (z. B. `SHIP_00.png`)
- Animationen: `NAME_00.png`, `NAME_01.png`, ... (z. B. `WATER_00.png` … `WATER_05.png` für eine 6-Frame-Welle)
- Einmal-Effekte: `HIT_00.png` … `HIT_04.png` (Explosion spielt einmal ab, z. B. 5 Frames)

Die Frame-Anzahl je Zellart ist in `Cell` hinterlegt (`frameCount`).

## Tests

```bash
java -ea -cp out ModelTest
```
