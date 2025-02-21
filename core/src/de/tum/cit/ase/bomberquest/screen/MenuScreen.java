package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.audio.BackgroundTrack;
import de.tum.cit.ase.bomberquest.audio.SoundEffects;
import de.tum.cit.ase.bomberquest.map.Settings;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 * This screen provides options to start a new game, continue a game, load a map, adjust settings, view rules, and quit the game.
 */
public class MenuScreen implements Screen {

    /**
     * The stage handles UI elements and input events for the menu.
     */
    private final Stage stage;
    /**
     * The background sprite for the menu screen.
     */
    private final Sprite backgroundSprite; // Added sprite for the background

    /**
     * The SpriteBatch used to draw the background.
     */
    private final SpriteBatch batch; //Added batch to draw background

    /**
     * An overlay actor for creating visual effects like dialog backgrounds.
     */
    private Actor overlay; // For the darkened background

    /**
     * Button to continue a previously started game.
     */
    private TextButton continueButton;


    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(BomberQuestGame game) {
        batch = game.getSpriteBatch();
        var camera = new OrthographicCamera();

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, batch); // Create a stage for UI elements

        // Load background texture and create sprite
        backgroundSprite = new Sprite(Textures.BACKGROUND);
        // You may need to adjust background sprite size and position depending on your background.
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Make it fill the screen

        addUI(game, camera);
    }

    /**
     * Adds all UI elements to the menu screen.
     * This includes buttons for starting a new game, continuing, loading a map, settings, rules, and quitting,
     * as well as the game logo and an overlay for dialogs.
     *
     * @param game   The main game class instance.
     * @param camera The OrthographicCamera used for the menu screen.
     */
    private void addUI(BomberQuestGame game, OrthographicCamera camera) {
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        Image logoImage = new Image(Textures.GAME_LOGO);
        logoImage.setScaling(Scaling.stretch);
        logoImage.setSize(400, 340);
        table.add(logoImage).size(600, 510).padBottom(50).row(); // Set cell size


        // Create and add a button to go to the game screen
        TextButton newGameButton = new TextButton("New Game", game.getSkin());
        table.add(newGameButton).width(500).padBottom(10).row();

        if (game.getMap() == null || !game.getMap().isStarted()) {
            continueButton = new TextButton("Continue", game.getSkin(), "gray");
            continueButton.setDisabled(true);
        } else continueButton = new TextButton("Continue", game.getSkin());
        table.add(continueButton).width(500).padBottom(10).row();


        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { // Change to the game screen when button is pressed
                SoundEffects.BUTTON_CLICK.play();
                if (game.getMap().isStarted()) showNewGameConfirmationDialog(game);
                else {
                    showPlayerNameConfirmationDialog(game);
                }
            }
        });

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToGame(); // Change to the game screen when button is pressed
            }
        });


        TextButton loadMapButton = new TextButton("Load Map", game.getSkin());
        table.add(loadMapButton).width(500).padBottom(10).row();
        loadMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                if (game.getMap().isStarted()) showLoadMapConfirmationDialog(game);
                else {
                    if (game.chooseMap()) continueButton.setDisabled(true);
                }

            }
        });

        TextButton settingsButton = new TextButton("Settings", game.getSkin());
        table.add(settingsButton).width(500).padBottom(10).row();
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                if (game.getMap().isStarted()) showSettingsConfirmationDialog(game);
                else showSettingsDialog(game);
            }
        });

        TextButton rulesButton = new TextButton("Rules", game.getSkin());
        table.add(rulesButton).width(500).padBottom(10).row();
        TextButton quitButton = new TextButton("Quit", game.getSkin());
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                showQuitConfirmationDialog(game);
            }
        });
        table.add(quitButton).width(500).padBottom(20).row();

        // Create a semi-transparent overlay
        overlay = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color oldColor = batch.getColor();
                batch.setColor(0, 0, 0, 0.5f); // Black with 50% transparency
                batch.draw(game.getSkin().getRegion("white"), 0, 0, camera.viewportWidth, camera.viewportHeight);
                batch.setColor(oldColor);
            }
        };
        overlay.setVisible(false); // Hide it initially
        stage.addActor(overlay);
    }

    /**
     * Shows an error dialog for map loading failures.
     * Provides options to reload the map or use a default map.
     *
     * @param game  The main game class instance.
     * @param error The error message to display in the dialog.
     */
    public void showErrorMapDialog(BomberQuestGame game, String error) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                overlay.setVisible(false);
                if (object.equals(true)) {
                    if (game.chooseMap()) continueButton.setDisabled(true);
                }

            }
        };
        Label messageLabel = new Label("ERROR", game.getSkin(), "bold");
        messageLabel.setAlignment(Align.center);  // Center the text
        messageLabel.setColor(Color.RED);
        messageLabel.setFontScale(2);
        dialog.getContentTable().add(messageLabel).pad(1f).row(); // Add padding

        Label errorLabel = new Label(error, game.getSkin());
        errorLabel.setAlignment(Align.center);  // Center the text
        errorLabel.setFontScale(0.85f);
        errorLabel.setColor(Color.RED);
        dialog.getContentTable().add(errorLabel).pad(1f).row(); // Add padding

        Label reloadLabel = new Label("Please reload the map or choose a default one.", game.getSkin());
        reloadLabel.setAlignment(Align.center);
        dialog.getContentTable().add(reloadLabel).pad(10f).row();

        TextButton reloadButton = new TextButton("Reload", game.getSkin());
        reloadButton.getLabel().setFontScale(0.9f);  // Scale down the text
        TextButton useDefaultButton = new TextButton("Use Default", game.getSkin());
        useDefaultButton.getLabel().setFontScale(0.9f);  // Scale down the text

        // Set button result values
        dialog.setObject(reloadButton, true);   // "Yes" button sends true
        dialog.setObject(useDefaultButton, false);   // "No" button sends false
        // Add buttons with spacing between them
        dialog.getButtonTable().add(reloadButton).pad(10f).size(200f, 50f); // Set size and padding
        dialog.getButtonTable().add(useDefaultButton).pad(10f).size(200f, 50f);  // Set size and padding
        // Show the dialog
        dialog.show(stage);

        dialog.show(stage);

    }

    /**
     * Shows a dialog to get player names before starting a new game.
     * Allows players to enter their names, with validation for empty or too long names.
     *
     * @param game The main game class instance.
     */
    private void showPlayerNameConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        boolean players2 = game.getMap().getPlayer2() != null;
        final TextField[] playerNameFields = new TextField[2];
        Label textFieldError = new Label("", game.getSkin());
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                overlay.setVisible(false);
                if (object.equals(true)) {
                    if (game.getMap().isStarted()) game.createNewMap();
                    continueButton.setDisabled(true);
                    if (players2) {
                        game.renamePlayers(playerNameFields[0].getText(), playerNameFields[1].getText());
                    } else {
                        game.renamePlayer(playerNameFields[0].getText());
                    }
                    BackgroundTrack.BACKGROUND.stop();
                    game.goToGame();
                } else {
                    SoundEffects.BUTTON_CLICK.play();
                }
            }
        };
        Label messageLabel = new Label("Enter your name", game.getSkin(), "bold");
        messageLabel.setFontScale(1.2f);
        messageLabel.setAlignment(Align.center);  // Center the text
        dialog.getContentTable().add(messageLabel).pad(10f).row(); // Add padding
        TextField player1Name = new TextField("Player 1", game.getSkin());
        player1Name.setAlignment(Align.center);
        dialog.getContentTable().add(player1Name).size(300f, 60f).pad(1f).row();
        playerNameFields[0] = player1Name;
        if (players2) {
            TextField player2Name = new TextField("Player 2", game.getSkin());
            player2Name.setAlignment(Align.center);
            dialog.getContentTable().add(player2Name).size(300f, 60f).pad(1f).row();
            playerNameFields[1] = player2Name;
        }

        textFieldError.setColor(Color.RED);
        textFieldError.setFontScale(0.8f);
        dialog.getContentTable().add(textFieldError).pad(5f).row();


        TextButton startButton = new TextButton("Start", game.getSkin(), "mini");
        TextButton cancelButton = new TextButton("Cancel", game.getSkin(), "mini");
        startButton.getLabel().setFontScale(0.9f);  // Scale down the text
        cancelButton.getLabel().setFontScale(0.9f);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                textFieldError.setText("");
                for (TextField field : playerNameFields) {
                    if (field != null) {  //Check for null in case of single player
                        String name = field.getText();
                        if (name.isEmpty()) {
                            textFieldError.setText("Your name is empty!");
                            break;
                        } else if (name.length() > 8) {
                            textFieldError.setText("Your name is too long!");
                            break;
                        }
                    }
                }

                if (textFieldError.getText().isEmpty()) { // Validate names
                    dialog.setObject(startButton, true);
                }
            }
        });

        dialog.setObject(cancelButton, false);
        dialog.getButtonTable().add(cancelButton).pad(10f).size(160f, 55f); // Set size and padding
        dialog.getButtonTable().add(startButton).pad(10f).size(160f, 55f);  // Set size and padding

        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false);

        dialog.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    // Trigger the start button's change listener
                    startButton.getClickListener().clicked(null, 0, 0); // Simulate a click
                    return true; // Consume the enter key event
                }
                return false; // Don't consume other key events
            }
        });
        dialog.show(stage);

    }

    /**
     * Shows a confirmation dialog for starting a new game, warning about unsaved progress loss.
     *
     * @param game The main game class instance.
     */
    private void showNewGameConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                SoundEffects.BUTTON_CLICK.play();
                if (object.equals(true)) {
                    showPlayerNameConfirmationDialog(game);
                } else {
                    overlay.setVisible(false);
                }
            }
        };
        dialogUI(game, dialog, "Are you sure you want to start a new game? Your unsaved progress will be lost.", "OK");

    }

    /**
     * Shows a confirmation dialog for changing game settings, warning about unsaved progress loss.
     *
     * @param game The main game class instance.
     */
    private void showSettingsConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                SoundEffects.BUTTON_CLICK.play();
                if (object.equals(true)) {
                    showSettingsDialog(game);
                } else overlay.setVisible(false);
            }
        };
        dialogUI(game, dialog, "Are you sure you want to change the settings? Your unsaved progress will be lost.", "OK");
    }

    /**
     * Shows the settings dialog, allowing users to modify game settings like AI, bombs, timer, and power-up chance.
     *
     * @param game The main game class instance.
     */
    private void showSettingsDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Settings settings = game.getSettings();
        TextButton aliensSmartButton = new TextButton((settings.isAliensSmart() ? "Yes" : "No"), game.getSkin(), "mini");
        TextButton aliensBombsButton = new TextButton((settings.isAliensBombs() ? "Yes" : "No"), game.getSkin(), "mini");
        Slider timerSlider = new Slider(250, 550, 50, false, game.getSkin());
        timerSlider.setValue(settings.getTimer());
        Slider powerUpChanceSlider = new Slider(10, 40, 5, false, game.getSkin());
        powerUpChanceSlider.setValue(settings.getPowerUpChance());
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                SoundEffects.BUTTON_CLICK.play();
                if (object.equals(true)) {
                    settings.setAliensSmart(aliensSmartButton.getText().toString().trim().equals("Yes"));
                    settings.setAliensBombs(aliensBombsButton.getText().toString().trim().equals("Yes"));
                    settings.setTimer((int) timerSlider.getValue());
                    settings.setPowerUpChance((int) powerUpChanceSlider.getValue());
                    game.createNewMap();
                    continueButton.setDisabled(true);
                }
                overlay.setVisible(false);
            }
        };
        Label messageLabel = new Label("Settings", game.getSkin(), "bold");
        messageLabel.setFontScale(1.5f);
        messageLabel.setAlignment(Align.center);  // Center the text
        dialog.getContentTable().add(messageLabel).colspan(2).row(); // Add padding

        Label aliensSmartLabel = new Label("Aliens are Smart", game.getSkin());
        dialog.getContentTable().add(aliensSmartLabel).pad(20f);
        aliensSmartButton.getLabel().setFontScale(0.85f);
        dialog.getContentTable().add(aliensSmartButton).pad(20f).size(120f, 50f).row();
        aliensSmartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                if (aliensSmartButton.getText().toString().trim().equals("Yes")) {
                    aliensSmartButton.setText("No");
                } else {
                    aliensSmartButton.setText("Yes");
                }
            }
        });

        Label aliensBombsLabel = new Label("Aliens place Bombs", game.getSkin());
        dialog.getContentTable().add(aliensBombsLabel).pad(20f);
        aliensBombsButton.getLabel().setFontScale(0.85f);
        dialog.getContentTable().add(aliensBombsButton).pad(20f).size(120f, 50f).row();
        aliensBombsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SoundEffects.BUTTON_CLICK.play();
                if (aliensBombsButton.getText().toString().trim().equals("Yes")) aliensBombsButton.setText("No");
                else aliensBombsButton.setText("Yes");
            }
        });

        Label timerLabel = new Label("Timer: " + settings.getTimer() + "s", game.getSkin());
        dialog.getContentTable().add(timerLabel).pad(20f);
        dialog.getContentTable().add(timerSlider).pad(20f).row();
        timerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                timerLabel.setText("Timer: " + (int) timerSlider.getValue() + "s");
            }
        });

        Label powerUpChanceLabel = new Label("PowerUp Chance: " + settings.getPowerUpChance() + "%", game.getSkin());
        dialog.getContentTable().add(powerUpChanceLabel).pad(20f);
        dialog.getContentTable().add(powerUpChanceSlider).pad(20f).row();
        powerUpChanceSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                powerUpChanceLabel.setText("PowerUp Chance: " + (int) powerUpChanceSlider.getValue() + "%");
            }
        });
        dialogUI(game, dialog, "", "Save");
    }

    /**
     * Shows a confirmation dialog for loading a new map, warning about unsaved progress loss.
     *
     * @param game The main game class instance.
     */
    private void showLoadMapConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                SoundEffects.BUTTON_CLICK.play();
                if (object.equals(true)) {
                    if (game.chooseMap()) continueButton.setDisabled(true);
                }
                overlay.setVisible(false);
            }
        };
        dialogUI(game, dialog, "Are you sure you want to load a new map? Your unsaved progress will be lost.", "OK");
    }

    /**
     * Shows a confirmation dialog for quitting the game.
     *
     * @param game The main game class instance.
     */
    private void showQuitConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                SoundEffects.BUTTON_CLICK.play();
                if (object.equals(true)) {
                    Gdx.app.exit(); // Or do other cleanup and then exit
                } else {
                    overlay.setVisible(false);
                }
            }
        };
        dialogUI(game, dialog, "Are you sure you want to quit the game?", "OK");
    }

    /**
     * Helper method to create and display a standardized dialog with yes/no buttons.
     *
     * @param game      The main game class instance.
     * @param dialog    The Dialog object to configure.
     * @param labelText The message text to display in the dialog.
     * @param yesText   The text for the 'yes' or confirmation button.
     */
    private void dialogUI(BomberQuestGame game, Dialog dialog, String labelText, String yesText) {
        if (!labelText.isEmpty()) {
            Label messageLabel = new Label(labelText, game.getSkin());
            messageLabel.setAlignment(Align.center);  // Center the text
            dialog.getContentTable().add(messageLabel).pad(20f).row(); // Add padding
        }

        TextButton yesButton = new TextButton(yesText, game.getSkin(), "mini");
        yesButton.getLabel().setFontScale(0.85f);  // Scale down the text
        TextButton noButton = new TextButton("Cancel", game.getSkin(), "mini");
        noButton.getLabel().setFontScale(0.85f);  // Scale down the text

        // Set button result values
        dialog.setObject(yesButton, true);   // "Yes" button sends true
        dialog.setObject(noButton, false);   // "No" button sends false
        // Add buttons with spacing between them
        dialog.getButtonTable().add(noButton).pad(10f).size(120f, 50f);  // Set size and padding
        dialog.getButtonTable().add(yesButton).pad(10f).size(120f, 50f); // Set size and padding
        // Allow keyboard shortcuts
        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        // Show the dialog
        dialog.show(stage);
    }

    /**
     * The render method is called every frame to render the menu screen.
     * It clears the screen, draws the background, updates and draws the stage (UI elements).
     *
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.250f);
        ScreenUtils.clear(Color.BLACK);
        batch.begin(); // Begin drawing with the batch
        backgroundSprite.draw(batch); // Draw the background sprite
        batch.end(); // End drawing with the batch
        stage.act(frameTime); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Resize the stage when the screen is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
        backgroundSprite.setSize(width, height);
    }

    /**
     * Disposes of resources when the screen is no longer needed.
     * Disposes of the stage and its actors, freeing up memory.
     */
    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    /**
     * Called when this screen becomes the current screen.
     * Sets the input processor to the stage so UI elements can receive input.
     */
    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen is no longer the current screen.
     * Not used in this implementation.
     */
    @Override
    public void pause() {
    }

    /**
     * Called when the application is resumed from a paused state.
     * Not used in this implementation.
     */
    @Override
    public void resume() {
    }

    /**
     * Called when this screen is no longer the current screen.
     * Not used in this implementation.
     */
    @Override
    public void hide() {
    }
}