# Cosmic Combustion: Space Mila

**A Bomber Quest Game**

**Created by:** Kamila Bekkozhina

## Overview

Cosmic Combustion: Space Mila is a thrilling bomber quest game developed using LibGDX. Navigate through intricate maps, strategically place bombs, outsmart enemies, and collect power-ups to conquer each level. This game is inspired by classic bomber games with a modern twist, featuring two player support and challenging AI.

## Project Structure

The project is structured as a standard LibGDX project, with the following key directories:

- **`.gradle`, `.idea`, `.jre`, `.gradlew`, `.gradlew.bat`, `gradle.properties`, `gradlew`, `gradlew.bat`, `settings`:** Standard project files and settings for Gradle, IntelliJ IDEA, and Java Runtime Environment. These are essential for building and managing the project.
- **`assets`:** Contains all game assets, including:
    - **`audio`:** Music and sound effects for the game.
        - `BackgroundTrack.java`: Manages background music tracks.
        - `SoundEffects.java`: Manages sound effects for in-game events.
    - **`maps`:** Game map files in `.properties` format.
        - `map-1.properties`: Example map file (and potentially others).
    - **`skin`:** UI skin resources for styling the game's user interface.
        - `UI skin.json`: JSON file defining the UI skin.
        - `UI skin.png`: Texture atlas for the UI skin.
    - **`texture`:** Texture and sprite sheet assets for game objects.
        - `Animations.java`: Defines and manages all game animations.
        - `Drawable.java`: Interface for drawable game objects.
        - `SpriteSheet.java`: Enumeration for managing and accessing sprite sheets.
        - `Textures.java`: Defines and manages all static textures.
        - `Background.png`, `Bombs and Blasts.png`, `Game Logo.png`, `Hud.png`, `MobileObjects.png`, `StationaryObjects.png`, `UI skin.png`: Image files for various game elements.
- **`core`:** Contains the main game logic and source code.
    - **`src/main/java/de/tum/cit.ase.bomberquest`:** Root package for the game's Java source code.
        - **`audio`:** Classes related to game audio management (described above).
        - **`map`:** Classes related to game map and game objects.
            - `Blast.java`: Represents bomb blast effects.
            - `BlastType.java`: Enumeration of blast types.
            - `Bomb.java`: Represents bomb objects.
            - `DestructibleWall.java`: Represents destructible wall objects.
            - `DirectionType.java`: Enumeration of movement directions.
            - `Enemy.java`: Represents enemy characters with AI.
            - `Exit.java`: Represents the level exit object.
            - `GameContactListener.java`: Handles collision events in the game.
            - `GameMap.java`: Manages the game map and game logic.
            - `Ground.java`: Represents ground tiles.
            - `IndestructibleWall.java`: Represents indestructible wall objects.
            - `MobileObject.java`: Abstract base class for mobile game objects (players, enemies).
            - `Pathfinder.java`: Implements pathfinding algorithms for AI.
            - `Player.java`: Represents player characters.
            - `PlusPoints.java`: Represents visual point effects.
            - `PowerUp.java`: Represents power-up items.
            - `Settings.java`: Manages game settings and configurations.
            - `StationaryObject.java`: Abstract base class for stationary game objects (walls, bombs, power-ups).
            - `Tile.java`: Record representing a tile in the game grid.
            - `WallContentType.java`: Enumeration of content types for destructible walls.
        - **`screen`:** Classes for different game screens (Menu, Game).
            - `GameScreen.java`: Implements the gameplay screen.
            - `Hud.java`: Implements the Heads-Up Display.
            - `MenuScreen.java`: Implements the main menu screen.
        - **`texture`:** Classes for managing game textures and animations (described above).
        - `BomberQuestGame.java`: The main game class, extending LibGDX's `Game` class.
- **`desktop`:** Contains files for desktop deployment.
    - `DesktopLauncher.java`: Entry point for desktop application.
- **`itp2425itp2425projectwork-kamilaelif.exe`:** Executable file to run the game on Windows (likely created for distribution).
- **`README.md`:** This file, providing information about the game.

## How to Run

To play Cosmic Combustion: Space Mila, you can directly run the executable file:

- **`itp2425itp2425projectwork-kamilaelif.exe`** (for Windows users): Double-click this file to start the game.

Alternatively, if you have the development environment set up (e.g., IntelliJ IDEA with Gradle), you can run the game from the `desktop` module.

## Features

- **Classic Bomber Gameplay:** Inspired by retro bomber games with a modern touch.
- **Two-Player Mode:** Enjoy playing with a friend in local multiplayer.
- **Challenging AI Enemies:** Face off against smart enemies that can find and pursue players.
- **Strategic Bomb Placement:** Use bombs to destroy walls and enemies.
- **Power-Ups:** Collect various power-ups to enhance your abilities (bomb count, blast radius, speed, and pass abilities).
- **Level Exit:** Find and reach the exit to clear levels.
- **Settings Customization:** Adjust game settings like enemy AI, bomb availability for enemies, game timer, and power-up drop rates.
- **Custom Map Loading:** Load and play custom maps from `.properties` files.
- **Game Over and Victory Conditions:** Clear levels by defeating all enemies and reaching the exit, or face game over if time runs out or players are eliminated.
- **Score System:** Earn points by defeating enemies.

## Dependencies

- LibGDX (managed by Gradle in the project)

## Credits

Developed by Kamila Bekkozhina.

Enjoy playing Cosmic Combustion: Space Mila!