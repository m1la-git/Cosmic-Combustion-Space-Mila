# Cosmic Combustion: Space Mila

**Developed by:** Kamila Bekkozhina

## Overview

Cosmic Combustion: Space Mila is a 2D top-down bomber-style game developed using LibGDX. Players navigate maze-like maps, strategically placing bombs to defeat enemies and clear paths. The game features single-player and potentially multiplayer modes, power-ups to enhance gameplay, and a timer-based challenge.

This project is built with a modular structure, separating game logic into distinct packages for audio, map management, screen handling, and texture management, making it easy to understand and extend.

## Features

*   **Classic Bomber Gameplay:** Engage in strategic bomb placement to destroy obstacles and enemies.
*   **Single and Multiplayer Modes:**  Enjoy the game solo or with a friend (based on code structure).
*   **Diverse Enemy Types:** Face off against AI-controlled enemies with varying behaviors (like smart pathfinding and bomb placement, configurable in settings).
*   **Power-Ups:** Collect various power-ups to enhance your abilities:
    *   Increased Bomb Capacity
    *   Extended Blast Radius
    *   Speed Boost
    *   Wall Pass, Bomb Pass, and Flame Pass abilities
*   **Destructible and Indestructible Walls:** Navigate through levels with strategic wall placement.
*   **Level Exit:** Find and reach the exit to clear levels.
*   **Timer-Based Challenge:** Race against the clock to complete levels.
*   **Game Settings:** Customize gameplay experience with settings for enemy AI, bomb placement, timer duration, and power-up drop rates.
*   **Custom Map Loading:** Load external map files in `.properties` format to play on user-created levels.
*   **Score System:** Earn points by defeating enemies.
*   **Engaging Sound Effects and Background Music:** Immerse yourself in the game's atmosphere with custom audio.
*   **Game Over and Victory Conditions:** Clear win and lose states with clear messaging and options to restart or return to the menu.

## File Structure

The project is organized into key packages within the `core/src/main/java/de/tum/cit/ase.bomberquest/` directory:

*   **`audio`**: Manages all game audio, including background music tracks and sound effects.
*   **`map`**: Contains core game logic related to the map, game objects (players, enemies, walls, bombs, blasts, power-ups), pathfinding, game settings, and collision handling.
*   **`screen`**: Implements different game screens such as the main menu, gameplay screen, and HUD.
*   **`texture`**: Handles texture and animation management, including spritesheet loading and animation definitions.
*   **`BomberQuestGame.java`**: (Located directly in `de.tum.cit.ase.bomberquest`) The main game class, responsible for game initialization, screen management, and global resource handling.

The `assets` folder (resources root) contains all game assets like audio files, textures, UI skin, and example map files.

The root project folder includes the executable JAR (`Cosmic Combustion Space Mila.exe`), the `README.md` file, project settings, and Gradle build files.

## How to Run

1.  **Executable JAR:**
    *   Double-click the `Cosmic Combustion Space Mila.exe` file located in the project root directory to run the game directly on Windows.

2.  **From IntelliJ IDEA (or other IDE with Gradle support):**
    *   Open the project in IntelliJ IDEA.
    *   Navigate to the `desktop` module.
    *   Run the `DesktopLauncher` class located in `desktop/src/main/java/de/tum.cit.ase.bomberquest.desktop/`.
    *   Alternatively, use the Gradle tasks in the IDE to build and run the `desktop` application.

## License

No license. All rights reserved by Kamila Bekkozhina.

---

Enjoy playing Cosmic Combustion: Space Mila!