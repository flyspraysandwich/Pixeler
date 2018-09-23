package com.mygdx.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DrawScreen extends ApplicationAdapter {

    //Initialize stuff
    static ShapeRenderer renderer = new ShapeRenderer();
    static SpriteBatch batch = new SpriteBatch();

    //canvas variables
    static Float canvasX = CanvasDrawer.canvasX;
    static Float canvasY = CanvasDrawer.canvasY;
    static Float canvasSize = CanvasDrawer.canvasSize;
    static String canvasSprite;
    static String fakeCanvas = CanvasDrawer.emptySprite(CanvasDrawer.spriteSize);
    static Float fakeCanvasAlpha = 1f;

    //Textures
    static Texture saveIcon = new Texture("saveicon.png");
    static Texture deleteIcon = new Texture("deleteicon.png");

    //Right hand menu variables
    static Float rightCentreX = canvasX + canvasSize + (Gdx.graphics.getWidth() / 8f);
    static Float centreY = Gdx.graphics.getHeight() / 2f;
    static boolean smallTouched = false;
    static boolean deleteTouch = false;
    static boolean saveTouch = false;
    static Float smallTouchX = 0f;
    static Float smallTouchY = 0f;
    static Float smallSize = canvasSize / 7f;
    static Float circleShrink = 0f;
    static Float spriteGrow = 0f;
    static Float saveDeleteAlpha = 0f;
    static Float saveDeleteScale = 0.5f;
    static Float saveScale = 1f;
    static Float deleteScale = 1f;
    static Float iconWidth = canvasSize/4f;
    static Float saveDeleteX = rightCentreX - iconWidth/2f;
    static Float saveY = centreY + (centreY/2f) - iconWidth/2f;
    static Float deleteY = centreY - (centreY/2f) - iconWidth/2f;
    static boolean spriteDeleted = false;

    //Dialog variables
    static boolean deletePermission;
    static boolean savePermission;
    static String menuPermission = "false";

    static void Main() {

        //Initialize canvas
        CanvasDrawer.Canvas();
        canvasSprite = CanvasDrawer.canvasSprite;

        //Initialize colour palette
        ColourPalette.Palette();

        //Initialize right hand buttons
        RightButtons.Main();

        //start renderer
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draw lil canvas sprite circle
        renderer.setColor(new Color(1,1,1,0.8f));
        renderer.circle(rightCentreX, centreY, (smallSize/1.4f)-circleShrink);

        //Draw Save circle
        saveScale = MainController.clamp(saveScale,1,1.3f);
        if (saveTouch) {
            if (saveScale < 1.3f)
                saveScale += MainController.dt/25f;
        }
        else {
            if (saveScale > 1)
                saveScale -= MainController.dt/25f;
        }
        renderer.setColor(new Color(1, 1, 1, saveDeleteAlpha));
        renderer.circle(saveDeleteX + iconWidth/2f, saveY + iconWidth/2f, saveScale * (saveDeleteScale * (iconWidth/1.5f)));

        //Draw Delete circle
        deleteScale = MainController.clamp(deleteScale,1,1.3f);
        if (deleteTouch) {
            if (deleteScale < 1.3f)
                deleteScale += MainController.dt/25f;
        }
        else {
            if (deleteScale > 1)
                deleteScale -= MainController.dt/25f;
        }
        renderer.setColor(new Color(1, 1, 1, saveDeleteAlpha));
        renderer.circle(saveDeleteX + iconWidth/2f, deleteY + iconWidth/2f, deleteScale * (saveDeleteScale * (iconWidth/1.5f)));

        //End renderer
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //small sprite is let go
        spriteGrow = MainController.clamp(spriteGrow,0,smallSize/4);
        if (!smallTouched) {

            if (!CanvasDrawer.spriteEmpty(CanvasDrawer.canvasSprite) && RightButtons.backAlpha == 0) {
                if (circleShrink > 0)
                    circleShrink -= MainController.dt * 8f;
                //draw sprite fixed
                SpriteDrawer.Draw(canvasSprite, (smallSize+spriteGrow), rightCentreX + 5, centreY - 5, Color.BLACK, 0.15f);
                SpriteDrawer.Draw(canvasSprite, (smallSize+spriteGrow), rightCentreX, centreY, Color.TEAL, 1f);
            }
            else {
                if (circleShrink <= smallSize/1.4f)
                    circleShrink += MainController.dt*8f;
            }
            if (spriteGrow > 0)
                spriteGrow -= MainController.dt*2f;

            //fade out save/delete icons
            if (saveDeleteAlpha > 0)
                saveDeleteAlpha -= MainController.dt/7.5f;

            if (saveDeleteScale > 0)
                saveDeleteScale -= MainController.dt/15;
        }

        //fade in save/delete icons when touched
        if (smallTouched) {
            if (saveDeleteAlpha < 1)
                saveDeleteAlpha += MainController.dt/7.5f;

            if (saveDeleteScale < 1)
                saveDeleteScale += MainController.dt/15;
        }

        //Draw save/delete icons
        saveDeleteScale = MainController.clamp(saveDeleteScale,0.5f,1);
        saveDeleteAlpha = MainController.clamp(saveDeleteAlpha,0,1);
        DrawSprite.Draw(saveIcon,saveDeleteX,saveY,iconWidth,iconWidth,saveDeleteScale,saveDeleteAlpha,Color.TEAL,false);
        DrawSprite.Draw(deleteIcon,saveDeleteX,deleteY,iconWidth,iconWidth,saveDeleteScale,saveDeleteAlpha,Color.TEAL,false);

        //Draw dead canvas sprite
        if (fakeCanvasAlpha > 0 && spriteDeleted)
            fakeCanvasAlpha -= MainController.dt/20;
        fakeCanvasAlpha = MainController.clamp(fakeCanvasAlpha,0,1);
        SpriteDrawer.Draw(fakeCanvas, canvasSize, CanvasDrawer.centreX + 5, CanvasDrawer.centreY - 5, Color.TEAL, fakeCanvasAlpha*0.2f);
        SpriteDrawer.Draw(fakeCanvas, canvasSize, CanvasDrawer.centreX, CanvasDrawer.centreY, Color.TEAL, fakeCanvasAlpha);

        //small sprite is touched
        if (smallTouched) {
            CanvasDrawer.canDraw = false;
            if (circleShrink < smallSize/1.4f)
                circleShrink += MainController.dt*4f;
            if (spriteGrow < smallSize/4)
                spriteGrow += MainController.dt*4f;
            //draw sprite at touch positions
            SpriteDrawer.Draw(canvasSprite, (smallSize+spriteGrow), smallTouchX + 5, smallTouchY - 5, Color.BLACK, 0.15f);
            SpriteDrawer.Draw(canvasSprite, (smallSize+spriteGrow), smallTouchX, smallTouchY, Color.TEAL, 1f);
        }
        circleShrink = MainController.clamp(circleShrink, 0, smallSize/1.4f);

        //Check if delete
        if (deletePermission) {
            DialogBox.ConfirmDialog("Clear the canvas?",0);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                deletePermission = false;
                fakeCanvasAlpha = 1f;
                fakeCanvas = canvasSprite;
                CanvasDrawer.canvasSprite = CanvasDrawer.emptySprite(CanvasDrawer.spriteSize);
                UndoRedo.makeChange();
                spriteDeleted = true;
            }
            else if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                deletePermission = false;
            }
        }

        //Check if save
        if (savePermission) {
            DialogBox.ConfirmDialog("Save over the existing sprite?",0);
            if (DialogBox.dialogResult == 1) {
                SaveData.SaveOver(CanvasDrawer.canvasSprite, CanvasDrawer.oldSprite);
                DialogBox.dialogResult = 0;
                menuPermission = "Sprite saved! Go back to menu?";
                savePermission = false;
            }
            else if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                menuPermission = "Go back to menu without saving?";
                savePermission = false;
            }
        }

        //Check if menu
        if (menuPermission != "false") {
            DialogBox.ConfirmDialog(menuPermission,0);
            if (DialogBox.dialogResult == 1) {

                //Delete the recovered sprite
                MainController.prefs.clear();
                MainController.prefs.flush();

                MainMenu.dataLoaded = false;
                if (CanvasDrawer.oldSprite != -1)
                    MainMenu.scrolled = MainMenu.optionSpaced * (MainMenu.spritesAmount - CanvasDrawer.oldSprite + 1);
                MainController.screenChange = 2;
                DialogBox.dialogResult = 0;
                menuPermission = "false";
            }
            else
            if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                menuPermission = "false";
            }
        }

    }

    //Reset the draw screen
    public static void resetDrawScreen(int oldSprite, String canvasSprite, int spriteSize) {

        //Reset draw screen variables, set oldsprite and canvassprite
        if (oldSprite == -1) {
            circleShrink = smallSize/1.4f;
            RightButtons.backAlpha = 1f;
            RightButtons.backScale = 1f;
        }
        else {
            circleShrink = 0f;
            RightButtons.backAlpha = 0f;
            RightButtons.backScale = 0f;
        }

        ColourPalette.scrolled = 0f;
        ColourPalette.currentColour = 'x';
        ColourPalette.buttonSelected = 1;
        CanvasDrawer.gridDrawn = false;
        CanvasDrawer.spriteSize = spriteSize;
        CanvasDrawer.oldSprite = oldSprite;
        CanvasDrawer.canvasSprite = canvasSprite;
        UndoRedo.changePosition = 0;
        UndoRedo.spriteChanges.clear();
        UndoRedo.timelineScrolled = 0;
        UndoRedo.timelineScrollable = 0;
        UndoRedo.scrollGoingTo = 0;
        UndoRedo.scrollPos = 0;
        UndoRedo.makeChange();

        //Go to draw screen
        MainController.screenChange = 3;
        DialogBox.dialogResult = 0;
    }

    //CONTROLS ------------------------------------------------------------------------------------------CONTROLS------------------------------------------------------------------------------------------------------------>

    //Drawscreen Controls - drag
    public static void ControllerDrag(int screenX, int screenY, int pointer) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //Small sprite being dragged
        if (smallTouched) {
            smallTouchX = screenX * 1f;
            smallTouchY = screenY * 1f;

            //If touching save icon
            if (screenX > saveDeleteX && screenX < saveDeleteX + iconWidth && screenY > saveY && screenY < saveY + iconWidth) {
                saveTouch = true;
            }
            //If touching delete icon
            else if (screenX > saveDeleteX && screenX < saveDeleteX + iconWidth && screenY > deleteY && screenY < deleteY + iconWidth) {
                deleteTouch = true;
            }
            else {
                deleteTouch = false;
                saveTouch = false;
            }
        }
    }

    //Drawscreen Controls - down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //Small sprite touched
        if (screenX > rightCentreX - smallSize/2 && screenX < rightCentreX - smallSize/2 + smallSize && screenY > centreY - smallSize/2 && screenY < centreY - smallSize/2 + smallSize && !CanvasDrawer.spriteEmpty(CanvasDrawer.canvasSprite) && MainController.screenChange == 0) {
            smallTouchX = screenX * 1f;
            smallTouchY = screenY * 1f;
            smallTouched = true;
        }

    }

    //Drawscreen controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //If let go after touching save
        if (saveTouch) {
            //If user is overwriting a sprite
            if (CanvasDrawer.oldSprite != -1) {
                if (!CanvasDrawer.canvasSprite.equals(MainMenu.spriteList.get(CanvasDrawer.oldSprite)))
                    savePermission = true;
                else
                    menuPermission = "No changes, go back to menu?";
            }
            //else save new
            else {
                SaveData.SaveNew(CanvasDrawer.canvasSprite);
                SaveData.LoadData();
                CanvasDrawer.oldSprite = MainMenu.spriteList.size()-1;
                menuPermission = "Sprite saved! Go back to menu?";
            }
            saveTouch = false;
        }

        //If let go after touching delete
        if (deleteTouch) {

            //ask for delete permission
            deletePermission = true;
            deleteTouch = false;
        }

        //Let go of small sprite if it was touched
        if (smallTouched) {
            smallTouched = false;
            saveTouch = false;
            deleteTouch = false;
            CanvasDrawer.canDraw = true;
        }
    }

    //Dispose
    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();

        //Dispose of textures
        saveIcon.dispose();
        deleteIcon.dispose();
    }
}
