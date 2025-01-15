package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private final Sprite backgroundSprite; // Added sprite for the background

    private final SpriteBatch batch; //Added batch to draw background

    private Actor overlay; // For the darkened background

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
                if (game.getMap().isStarted()) showNewGameConfirmationDialog(game);
                else {
                    startNewGame(game);
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
                if (game.getMap().isStarted()) showLoadMapConfirmationDialog(game);
                else {
                    if (game.chooseMap()) continueButton.setDisabled(true);
                }

            }
        });

        TextButton rulesButton = new TextButton("Rules", game.getSkin());
        table.add(rulesButton).width(500).padBottom(10).row();
        TextButton quitButton = new TextButton("Quit", game.getSkin());
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                    game.goToGame();
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

        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false);

        dialog.show(stage);

    }

    private void startNewGame(BomberQuestGame game) {
        BackgroundTrack.BACKGROUND.stop();

        showPlayerNameConfirmationDialog(game);
    }
    private void showNewGameConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    startNewGame(game);
                } else {
                    overlay.setVisible(false);
                }
            }
        };
        dialogUI(game, dialog, "Are you sure you want to start a new game? Your unsaved progress will be lost.");

    }

    private void showLoadMapConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    if (game.chooseMap()) continueButton.setDisabled(true);
                }
                overlay.setVisible(false);
            }
        };
        dialogUI(game, dialog, "Are you sure you want to load a new map? Your unsaved progress will be lost.");
    }

    private void showQuitConfirmationDialog(BomberQuestGame game) {
        overlay.setVisible(true);
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    Gdx.app.exit(); // Or do other cleanup and then exit
                } else {
                    overlay.setVisible(false);
                }
            }
        };
        dialogUI(game, dialog, "Are you sure you want to quit the game?");
    }


    private void dialogUI(BomberQuestGame game, Dialog dialog, String labelText) {
        Label messageLabel = new Label(labelText, game.getSkin());
        messageLabel.setAlignment(Align.center);  // Center the text
        dialog.getContentTable().add(messageLabel).pad(20f).row(); // Add padding
        TextButton yesButton = new TextButton("Yes", game.getSkin(), "mini");
        yesButton.getLabel().setFontScale(0.9f);  // Scale down the text
        TextButton noButton = new TextButton("No", game.getSkin(), "mini");
        noButton.getLabel().setFontScale(0.9f);  // Scale down the text

        // Set button result values
        dialog.setObject(yesButton, true);   // "Yes" button sends true
        dialog.setObject(noButton, false);   // "No" button sends false
        // Add buttons with spacing between them
        dialog.getButtonTable().add(yesButton).pad(10f).size(100f, 40f); // Set size and padding
        dialog.getButtonTable().add(noButton).pad(10f).size(100f, 40f);  // Set size and padding
        // Allow keyboard shortcuts
        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false);
        // Show the dialog
        dialog.show(stage);
    }

    /**
     * The render method is called every frame to render the menu screen.
     * It clears the screen and draws the stage.
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

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}