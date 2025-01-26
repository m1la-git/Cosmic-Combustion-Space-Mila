# Cosmic Combustion: Space Mila

**Author:** Kamila Bekkozhina

## About

Cosmic Combustion: Space Mila is a bomber-quest style game developed using the LibGDX game development framework. Navigate through maze-like levels, strategically place bombs to overcome obstacles and enemies, and aim for victory!

This project demonstrates game development concepts including:

*   **Game Logic:** Implementing gameplay mechanics such as player movement, enemy AI, bomb placement and explosions, power-ups, and level completion.
*   **Physics Engine:** Utilizing Box2D for realistic physics simulations and collision detection.
*   **Animation and Textures:** Using sprite sheets and animations to bring the game world to life.
*   **Audio Integration:** Incorporating background music and sound effects to enhance the gaming experience.
*   **UI Design:** Creating a user-friendly menu and Heads-Up Display (HUD) using LibGDX Scene2D UI.
*   **Game Settings:** Allowing players to customize game difficulty and other parameters.

## File Structure

The project follows a standard LibGDX project structure, which is organized as follows:
content_copy
download
Use code with caution.
Markdown

Cosmic Combustion: Space Mila/
├── .gradle/ # Gradle wrapper files and settings
├── .idea/ # IntelliJ IDEA project files (if using IntelliJ)
├── assets/ # Game assets (textures, audio, maps, skin)
│ ├── audio/ # Sound effects and background music files
│ ├── map/ # Game map files (e.g., properties files)
│ ├── screen/ # (Potentially unused folder from template, may be empty or deprecated)
│ └── texture/ # Texture files (spritesheets, background, logo)
├── core/ # Core game logic and source code
│ ├── build/ # Build output directory for core module
│ └── src/ # Source code for the core game logic
│ └── de/tum/cit/ase/bomberquest/
│ ├── audio/ # Audio management classes (BackgroundTrack, SoundEffects)
│ ├── map/ # Game map related classes (GameMap, Tile, Entities, etc.)
│ ├── screen/ # Screen classes (GameScreen, MenuScreen, Hud)
│ └── texture/ # Texture and animation management classes (Animations, SpriteSheet, Textures, Drawable interface)
├── desktop/ # Desktop-specific launcher and build configurations
│ ├── build/ # Build output directory for desktop launcher
│ └── out/ # Output directory for executable JAR
│ └── src/ # Source code for desktop launcher
│ └── de/tum/cit/ase/bomberquest/
│ └── DesktopLauncher.java # Main class to launch the game on desktop
├── gradle/ # Gradle wrapper configuration
├── gradlew # Gradle wrapper script for Linux/macOS
├── gradlew.bat # Gradle wrapper script for Windows
├── itp2425itp2425projectwork-kamilaelief.iml # IntelliJ IDEA module file
├── itp2425itp2425projectwork-kamilaelief.jar # Executable Jar file of the game
├── LICENSE # (If any, otherwise remove or specify "No License")
├── README.md # This file - Project description and instructions
└── settings.gradle # Gradle settings file

**Key Folders:**

*   **`assets/`**: Contains all game resources such as textures, audio files, and map data.
*   **`core/src/de/tum/cit/ase/bomberquest/`**:  Holds the main game source code, separated into packages for audio, map, screen, and texture management.
*   **`desktop/src/de/tum/cit/ase/bomberquest/`**: Contains the `DesktopLauncher.java` file, which is the entry point for running the game on desktop platforms.

## Executable

*   `Cosmic Combustion Space Mila.exe`: This is the executable file to run the game on Windows.
*   `itp2425itp2425projectwork-kamilaelief.jar`:  Executable JAR file, runnable on any platform with Java installed.

## License

This project is distributed without a specific license. All rights are reserved by the author, Kamila Bekkozhina.

---

Thank you for checking out Cosmic Combustion: Space Mila! Enjoy the game!