package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Textures;
import org.w3c.dom.Text;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private final Sprite backgroundSprite; // Added sprite for the background

    private final SpriteBatch batch; //Added batch to draw background



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

        addUI(game);
    }

    private void addUI(BomberQuestGame game) {
        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        Image logoImage = new Image(Textures.GAME_LOGO);
        logoImage.setScaling(Scaling.stretch);
        logoImage.setSize(400, 340);
        table.add(logoImage).size(600, 510).padBottom(50).row(); // Set cell size

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("New Game", game.getSkin());
        table.add(goToGameButton).width(500).padBottom(10).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.createNewMap();
                game.goToGame(); // Change to the game screen when button is pressed
            }
        });
        TextButton continueButton;
        if (game.getMap() == null) {
            continueButton = new TextButton("Continue", game.getSkin(), "gray");
            continueButton.setDisabled(true);
        }
        else continueButton = new TextButton("Continue", game.getSkin());
        table.add(continueButton).width(500).padBottom(10).row();
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
                System.out.println("Load Map");
                game.chooseMap();
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
    }

    private void showQuitConfirmationDialog(BomberQuestGame game) {
        Dialog dialog = new Dialog("Confirm Quit", game.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    Gdx.app.exit(); // Or do other cleanup and then exit
                }
            }
        };

        Label messageLabel = new Label("Are you sure you want to quit?", game.getSkin());
        messageLabel.setAlignment(Align.center);  // Center the text
        dialog.getContentTable().add(messageLabel).pad(10f); // Add padding

        dialog.button("Yes", true); // Sends true if clicked
        dialog.button("No", false); // Sends false if clicked
        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true); // Allows enter to confirm
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, false); // Allows escape to cancel

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
        backgroundSprite.setSize(width,height);
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