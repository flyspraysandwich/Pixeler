package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenu extends ApplicationAdapter{

    //Variables
    static SpriteBatch batch = new SpriteBatch();
    static BitmapFont font = MainController.cyrillicFont2;
    static Float centreX = Gdx.graphics.getWidth()/2f;
    static Float scrollY = Gdx.graphics.getHeight()/1.6f;
    static int optionSize = Math.round(Gdx.graphics.getWidth()/6);
    static int optionSpaced = Math.round(optionSize*1.2f);
    static Random rand = new Random();
    static int spritesAmount = MainController.spriteLis.length;
    static boolean newButtonTouch = false;
    static boolean dataLoaded = false;
    static List<String> spriteList = new ArrayList<String>();
    static TextInput listener = new TextInput();
    static TextInput2 listener2 = new TextInput2();
    static String spriteCode = "";

    //Dialog booleans
    static boolean sizeDialog = false;
    static boolean deletePermission = false;
    static boolean emptyDialog = false;
    static boolean resolutionNew = false;
    static boolean resolutionCopy = false;
    static boolean invalidSprite = false;
    static boolean colourDialog = false;

    //Button vars
    static Float buttonY = Gdx.graphics.getHeight()/5f;
    static Float buttonAlpha = 0f;
    static Float buttonScale = 0.5f;
    static Float openAlpha = 0f;
    static Float openScale = 0.5f;
    static Float buttonWidth = Gdx.graphics.getWidth()/6f;
    static Float buttonHeight = buttonWidth * 0.56f;
    static Float smallButtonWidth = buttonHeight * 0.88105f;
    static Float editButtonX = centreX - (buttonWidth/2)*1.2f;
    static Float copyButtonX = centreX + (buttonWidth/2)*1.2f;
    static Float saveButtonX = centreX - (buttonWidth)*1.55f;
    static Float deleteButtonX = centreX + (buttonWidth)*1.55f;
    static Float zoomSize = Gdx.graphics.getHeight()/9f;
    static Float zoomScale = 1f;
    static boolean zoomTouch = false;
    static Float gearSize = Gdx.graphics.getHeight()/9f;
    static Float gearScale = 1f;
    static boolean gearTouch = false;

    //Scroll variables
    static boolean touched = false;
    static boolean spriteMinis = false;
    static Float scrollWidth = Gdx.graphics.getWidth()*1f;
    static Float scrollHeight = Gdx.graphics.getHeight()/3f;
    static Float scrollBitWidth = (scrollWidth/25);
    static Float scrollBitHeight = scrollBitWidth * 0.416f;
    static Float scrollBitAlpha = 0f;
    static int optionSelected = 0;
    static int touchX = 0;
    static int scrollAmount = 0;
    static int scrolled = 0;
    static int goingTo = -1;
    static boolean scrollLeftTouch = false;
    static boolean scrollRightTouch = false;
    static Float scrollButtonSize = Gdx.graphics.getHeight()/6f;
    static Float scrollLeftAlpha = 0f;
    static Float scrollRightAlpha = 0f;
    static Float scrollLeftScale = 1f;
    static Float scrollRightScale = 1f;

    //Textures
    static Texture scrollBit = new Texture("barscroll2.png");
    static Texture newButtonTex = new Texture("newbutton.png");
    static Texture newButtonTex2 = new Texture("newbutton2.png");
    static Texture newButtonState = new Texture("newbutton.png");
    static Texture editButton = new Texture("editbutton.png");
    static Texture editButton2 = new Texture("editbutton2.png");
    static Texture editButtonState = new Texture("editbutton.png");
    static Texture copyButton = new Texture("copybutton.png");
    static Texture copyButton2 = new Texture("copybutton2.png");
    static Texture copyButtonState = new Texture("copybutton.png");
    static Texture saveButton = new Texture("savebutton.png");
    static Texture saveButton2 = new Texture("savebutton2.png");
    static Texture saveButtonState = new Texture("savebutton.png");
    static Texture deleteButton = new Texture("deletebutton.png");
    static Texture deleteButton2 = new Texture("deletebutton2.png");
    static Texture deleteButtonState = new Texture("deletebutton.png");
    static Texture openButton = new Texture("openbutton.png");
    static Texture openButton2 = new Texture("openbutton2.png");
    static Texture openButtonState = new Texture("openbutton.png");
    static Texture zoomInButton = new Texture("zoominbutton.png");
    static Texture zoomOutButton = new Texture("zoomoutbutton.png");
    static Texture zoomState = new Texture("zoomoutbutton.png");
    static Texture gearIcon = new Texture("gearicon.png");
    static Texture scrollLeft = new Texture("scrollleft.png");
    static Texture scrollRight = new Texture("scrollright.png");

    //Draw the main menu
    public static void Main() {

        //Auto scroll to closest option when untouched
        if (goingTo == -1) {
            if (!touched && scrolled != optionSelected) {
                scrolled = lerp(scrolled,optionSelected,0.1f);
            }
        }
        //Lerp to position when asked
        else {
            if (scrolled != goingTo)
                scrolled = lerp(scrolled,goingTo,0.1f);
            else
                goingTo = -1;
        }

        //Clamp the scroll
        scrolled = Math.round(MainController.clamp(scrolled*1f,0,scrollAmount));
        optionSelected = Math.round((scrolled/optionSpaced)*optionSpaced);

        //Load the data
        if (!dataLoaded) {
            SaveData.LoadData();
            dataLoaded = true;
        }

        //Draw options
        for (int i = 0; i < spritesAmount+1; i++) {
            //Draw only what can be seen
            if (scrolled > i*optionSpaced - scrollWidth && scrolled < i*optionSpaced + scrollWidth) {
                if (i == 0) {
                    if (i*optionSpaced == optionSelected)
                        DrawSprite.Draw(newButtonState, centreX - scrolled, scrollY, optionSize*1f, optionSize*1f, 1.4f, 1f, Color.TEAL, true);
                    else
                        DrawSprite.Draw(newButtonState, centreX - scrolled, scrollY, optionSize*1f, optionSize*1f, 1f, 0.3f, Color.TEAL, true);
                }
                else {
                    Float spriteSize = Float.valueOf(spriteList.get(spritesAmount-i).substring(0,2));
                    if (i*optionSpaced == optionSelected) {
                        //Mini sprites
                        if (spriteMinis) {
                            //shadows
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize, centreX + (optionSpaced*i) - scrolled + 2, scrollY + (optionSize/4) + spriteSize - 2, Color.BLACK, 0.15f);
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize*2, centreX + (optionSpaced*i) - scrolled + 3, scrollY - 3 + Math.round(spriteSize*1.25f), Color.BLACK, 0.15f);
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize*4, centreX + (optionSpaced*i) - scrolled + 6, scrollY - (optionSize/4) - 6, Color.BLACK, 0.15f);
                            //sprites
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize, centreX + (optionSpaced*i) - scrolled , scrollY + (optionSize/4) + spriteSize, Color.TEAL, 1f);
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize*2, centreX + (optionSpaced*i) - scrolled, scrollY + Math.round(spriteSize*1.25f), Color.TEAL, 1f);
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), spriteSize*4, centreX + (optionSpaced*i) - scrolled, scrollY - (optionSize/4), Color.TEAL, 1f);
                        }
                        //Large sprite
                        else {
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), optionSize*1.2f, centreX + (optionSpaced*i) - scrolled + (optionSize*0.07f), scrollY - (optionSize*0.07f), Color.BLACK, 0.15f);
                            SpriteDrawer.Draw(spriteList.get(spritesAmount-i), optionSize*1.2f, centreX + (optionSpaced*i) - scrolled, scrollY, Color.TEAL, 1f);
                        }

                        //Draw text
                        batch.begin();
                        font.setColor(0,0,0,0.2f);
                        font.draw(batch, Integer.toString(Math.round(spriteSize)) + " x " + Integer.toString(Math.round(spriteSize)), centreX + (optionSpaced*i) - scrolled + 3, scrollY - optionSize/1.4f - 3,0, Align.center,false);
                        font.setColor(1,1,1,1);
                        font.draw(batch, Integer.toString(Math.round(spriteSize)) + " x " + Integer.toString(Math.round(spriteSize)), centreX + (optionSpaced*i) - scrolled, scrollY - optionSize/1.4f,0, Align.center,false);
                        batch.end();

                    } else {
                        SpriteDrawer.Draw(spriteList.get(spritesAmount-i), optionSize*0.9f, centreX + (optionSpaced*i) - scrolled, scrollY, Color.TEAL, 0.3f);
                        //Draw text
                        batch.begin();
                        font.setColor(1,1,1,0.4f);
                        font.draw(batch, Integer.toString(Math.round(spriteSize)) + " x " + Integer.toString(Math.round(spriteSize)), centreX + (optionSpaced*i) - scrolled, scrollY - optionSize/1.7f,0, Align.center,false);
                        batch.end();
                    }

                }
            }
        }

        //Set the scrollbit alpha
        if (touched || goingTo != -1) {
            if (scrollBitAlpha < 1)
                scrollBitAlpha += MainController.dt/15f;
        }
        else if (!touched || goingTo != -1) {
            if (scrollBitAlpha > 0)
                scrollBitAlpha -= MainController.dt/15f;
        }
        scrollBitAlpha = MainController.clamp(scrollBitAlpha,0,1);

        //Draw the scrollbit
        if (spritesAmount > 0)
            DrawSprite.Draw(scrollBit, (scrolled*1f/scrollAmount)*(scrollWidth-scrollBitHeight-scrollBitWidth) + (scrollWidth/35), scrollBitHeight, scrollBitWidth, scrollBitHeight, 1f, scrollBitAlpha, Color.TEAL, true);

        //Button fade in/out
        if (optionSelected == 0) {
            if (buttonAlpha > 0)
                buttonAlpha -= MainController.dt/10;
            if (buttonScale > 0.5)
                buttonScale -= MainController.dt/20;
            if (openAlpha < 1)
                openAlpha += MainController.dt/10;
            if (openScale < 1)
                openScale += MainController.dt/20;
        }
        else {
            if (buttonAlpha < 1)
                buttonAlpha += MainController.dt/10;
            if (buttonScale < 1)
                buttonScale += MainController.dt/20;
            if (openAlpha > 0)
                openAlpha -= MainController.dt/10;
            if (openScale > 0.5)
                openScale -= MainController.dt/20;
        }
        buttonAlpha = MainController.clamp(buttonAlpha,0,1);
        buttonScale = MainController.clamp(buttonScale,0.5f,1);
        openAlpha = MainController.clamp(openAlpha,0,1);
        openScale = MainController.clamp(openScale,0.5f,1);

        //Draw the buttons
        DrawSprite.Draw(openButtonState,centreX, buttonY, buttonWidth, buttonHeight, openScale, openAlpha, Color.TEAL, true);
        DrawSprite.Draw(saveButtonState,saveButtonX, buttonY, smallButtonWidth, buttonHeight, buttonScale, buttonAlpha, Color.TEAL, true);
        DrawSprite.Draw(editButtonState,editButtonX, buttonY, buttonWidth, buttonHeight, buttonScale, buttonAlpha, Color.TEAL, true);
        DrawSprite.Draw(copyButtonState,copyButtonX, buttonY, buttonWidth, buttonHeight, buttonScale, buttonAlpha, Color.TEAL, true);
        DrawSprite.Draw(deleteButtonState,deleteButtonX, buttonY, smallButtonWidth, buttonHeight, buttonScale, buttonAlpha, Color.TEAL, true);

        //Zoom/Gear icons
        DrawSprite.Draw(zoomState,0 + zoomSize*0.6f, Gdx.graphics.getHeight() - zoomSize*0.6f, zoomSize, zoomSize, zoomScale, buttonAlpha, Color.TEAL, true);
        DrawSprite.Draw(gearIcon,Gdx.graphics.getWidth() - gearSize*0.6f, Gdx.graphics.getHeight() - gearSize*0.6f, gearSize, gearSize, gearScale, 1f, Color.TEAL, true);

        //Zoom button scaling
        if (zoomTouch) {
            if (zoomScale < 1.2f)
                zoomScale += MainController.dt/30;
        }
        else {
            if (zoomScale > 1)
                zoomScale -= MainController.dt/30;
        }
        zoomScale = MainController.clamp(zoomScale,1,1.2f);

        //Gear icon scaling
        if (gearTouch) {
            if (gearScale < 1.2f)
                gearScale += MainController.dt/30;
        }
        else {
            if (gearScale > 1)
                gearScale -= MainController.dt/30;
        }
        gearScale = MainController.clamp(gearScale,1,1.2f);

        //Left scroll button fade in/outs
        if (optionSelected > optionSpaced) {

            if (scrollLeftAlpha < 0.3f)
                scrollLeftAlpha += MainController.dt / 40;
            else {
                if (scrollLeftTouch) {
                    if (scrollLeftScale < 1.5)
                        scrollLeftScale += MainController.dt / 10;
                    if (scrollLeftAlpha < 1)
                        scrollLeftAlpha += MainController.dt / 10;
                } else {
                    if (scrollLeftScale > 1)
                        scrollLeftScale -= MainController.dt / 10;
                    if (scrollLeftAlpha > 0.3)
                        scrollLeftAlpha -= MainController.dt / 10;
                }
                scrollLeftAlpha = MainController.clamp(scrollLeftAlpha, 0.3f, 1);
            }

        }
        else{
            if (scrollLeftAlpha >= 0)
                scrollLeftAlpha -= MainController.dt / 40;
            if (scrollLeftScale > 1)
                scrollLeftScale -= MainController.dt / 10;
        }
        scrollLeftAlpha = MainController.clamp(scrollLeftAlpha, 0, 1);
        scrollLeftScale = MainController.clamp(scrollLeftScale,1,1.5f);

        //Right scroll button fade in/outs
        if (scrolled != scrollAmount) {

            if (scrollRightAlpha < 0.3f)
                scrollRightAlpha += MainController.dt / 40;
            else {
                if (scrollRightTouch) {
                    if (scrollRightScale < 1.5)
                        scrollRightScale += MainController.dt / 10;
                    if (scrollRightAlpha < 1)
                        scrollRightAlpha += MainController.dt / 10;
                } else {
                    if (scrollRightScale > 1)
                        scrollRightScale -= MainController.dt / 10;
                    if (scrollRightAlpha > 0.3)
                        scrollRightAlpha -= MainController.dt / 10;
                }
                scrollRightAlpha = MainController.clamp(scrollRightAlpha, 0.3f, 1);
            }
        }
        else {
            if (scrollRightAlpha >= 0)
                scrollRightAlpha -= MainController.dt / 40;
            if (scrollRightScale > 1)
                scrollRightScale -= MainController.dt / 10;
        }
        scrollRightAlpha = MainController.clamp(scrollRightAlpha, 0, 1);
        scrollRightScale = MainController.clamp(scrollRightScale,1,1.5f);

        //Draw left/right buttons
        if (spritesAmount > 0) {
            DrawSprite.Draw(scrollLeft,(scrollButtonSize/1.8f) * scrollLeftScale,(scrollButtonSize/1.8f) * scrollLeftScale,scrollButtonSize * scrollLeftScale,scrollButtonSize * scrollLeftScale,1f,scrollLeftAlpha,Color.TEAL,true);
            SpriteDrawer.Draw(spriteList.get(spriteList.size()-1),(scrollButtonSize*0.5f) * scrollLeftScale,(scrollButtonSize/1.8f) * scrollLeftScale,(scrollButtonSize/1.8f) * scrollLeftScale,Color.TEAL,scrollLeftAlpha);

            DrawSprite.Draw(scrollRight,Gdx.graphics.getWidth() - (scrollButtonSize/1.8f) * scrollRightScale,(scrollButtonSize/1.8f) * scrollRightScale,scrollButtonSize * scrollRightScale,scrollButtonSize * scrollRightScale,1f,scrollRightAlpha,Color.TEAL,true);
            SpriteDrawer.Draw(spriteList.get(0),(scrollButtonSize*0.5f) * scrollRightScale,Gdx.graphics.getWidth() - (scrollButtonSize/1.8f) * scrollRightScale,(scrollButtonSize/1.8f) * scrollRightScale,Color.TEAL,scrollRightAlpha);
        }

        //Dialogs ---------------------------------------------------------------------------------------------------------->

        //If size limit reached
        if (sizeDialog) {
            DialogBox.ConfirmDialog("You have reached 1000 sprites!\nNo more room!", 1);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                sizeDialog = false;
            }
        }

        //If empty sprite
        if (emptyDialog) {
            DialogBox.ConfirmDialog("Sorry, no empty sprites allowed.", 1);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                emptyDialog = false;
            }
        }

        //If invalid sprite code
        if (invalidSprite) {
            DialogBox.ConfirmDialog("Invalid sprite code.", 1);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                invalidSprite = false;
            }
        }

        //Check if delete
        if (deletePermission) {
            DialogBox.ConfirmDialog("Are you sure you wish to delete?",0);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                deletePermission = false;
                SaveData.DeleteData(spritesAmount-(optionSelected/optionSpaced));
            }
            else if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                deletePermission = false;
            }
        }

        //Ask for resolution of new sprite
        if (resolutionNew) {
            DialogBox.ConfirmDialog("Resolution of new sprite?",2);
            if (DialogBox.dialogResult == 1) {
                //Reset draw screen
                DrawScreen.resetDrawScreen(-1,CanvasDrawer.emptySprite(DialogBox.canvasSize),DialogBox.canvasSize);
                resolutionNew = false;
            }
            else if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                resolutionNew = false;
            }
        }

        //Ask for resolution of copied sprite
        if (resolutionCopy) {
            DialogBox.ConfirmDialog("Resolution of copied sprite?",2);
            if (DialogBox.dialogResult == 1) {
                //If new sprite will not be empty
                if (!CanvasDrawer.spriteEmpty(CanvasDrawer.spriteConverter(spriteList.get(spritesAmount-(optionSelected/optionSpaced)),DialogBox.canvasSize))) {
                    //Save selected sprite as new
                    SaveData.SaveNew(CanvasDrawer.spriteConverter(spriteList.get(spritesAmount-(optionSelected/optionSpaced)),DialogBox.canvasSize));
                    //Reset draw screen
                    DrawScreen.resetDrawScreen(spriteList.size()-1,spriteList.get(spriteList.size()-1),Integer.valueOf(spriteList.get(spriteList.size()-1).substring(0,2)));
                    resolutionCopy = false;
                }
                //If it will be empty, cancel the dialog
                else {
                    DialogBox.dialogResult = 0;
                    resolutionCopy = false;
                    emptyDialog = true;
                }
            }
            else if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                resolutionCopy = false;
            }
        }

        //Get main colour
        if (colourDialog) {
            DialogBox.ConfirmDialog("Select theme colour:", 3);
            if (DialogBox.dialogResult == 1) {
                DialogBox.dialogResult = 0;
                colourDialog = false;
            }
        }

        //Ask for spriteCode
        if (spriteCode != "") {
            if (!spriteCode.equals("random")) {
                try {
                    Gdx.app.log("MyTag", "trying");

                    //Validate code before decompressing
                    if (MainController.validateCompressed(spriteCode)) {

                        //Decompress
                        spriteCode = MainController.decompressString(spriteCode);

                        //Validate decompressed string
                        if (MainController.validateDecompressed(spriteCode)) {

                            //If sprite will not be empty
                            if (!CanvasDrawer.spriteEmpty(spriteCode)) {
                                Gdx.app.log("MyTag", "sprite saving");
                                SaveData.SaveNew(spriteCode);
                                Gdx.app.log("MyTag", "sprite saved " + spriteCode);
                            }
                            else
                                emptyDialog = true;
                            Gdx.app.log("MyTag", "code valid");
                        } else
                            invalidSprite = true;
                    }
                    else {
                        invalidSprite = true;
                    }

                } catch (Exception e) {
                    invalidSprite = true;
                    Gdx.app.log("MyTag", "code invalid");
                } finally {
                    spriteCode = "";
                }

            }
            //Sprite code is 'random'
            else {
                SaveData.SaveNew(CanvasDrawer.randomSprite());
                spriteCode = "";
            }
        }
    }

    //MainMenu controls - touch down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If not going anywhere
        if (goingTo == -1 && MainController.screenChange == 0) {

            //If new button is not selected
            if (optionSelected != 0) {

                //Set the new button to untouched
                newButtonState = newButtonTex;
                newButtonTouch = false;

                //If touching within save button bounds
                if (screenX > saveButtonX - (smallButtonWidth/2) && screenX < saveButtonX + (smallButtonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    saveButtonState = saveButton2;
                else
                    saveButtonState = saveButton;

                //If touching within edit button bounds
                if (screenX > editButtonX - (buttonWidth/2) && screenX < editButtonX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    editButtonState = editButton2;
                else
                    editButtonState = editButton;

                //If touching within copy button bounds
                if (screenX > copyButtonX - (buttonWidth/2) && screenX < copyButtonX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    copyButtonState = copyButton2;
                else
                    copyButtonState = copyButton;

                //If touching within delete button bounds
                if (screenX > deleteButtonX - (smallButtonWidth/2) && screenX < deleteButtonX + (smallButtonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    deleteButtonState = deleteButton2;
                else
                    deleteButtonState = deleteButton;

            }
            //New button is selected
            else {
                //If touching within open button bounds
                if (screenX > centreX - (buttonWidth/2) && screenX < centreX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    openButtonState = openButton2;
                else
                    openButtonState = openButton;
            }

            //Zoom button touched
            if (screenX > 0 + zoomSize*0.6f - zoomSize/2 && screenX < 0 + zoomSize*0.6f + zoomSize/2 && screenY > Gdx.graphics.getHeight() - zoomSize*0.6f - zoomSize/2 && screenY < Gdx.graphics.getHeight() - zoomSize*0.6f + zoomSize/2)
                zoomTouch = true;

            //Gear button touched
            if (screenX > Gdx.graphics.getWidth() - gearSize*0.6f - gearSize/2 && screenX < Gdx.graphics.getWidth() - gearSize*0.6f + gearSize/2 && screenY > Gdx.graphics.getHeight() - gearSize*0.6f - gearSize/2 && screenY < Gdx.graphics.getHeight() - gearSize*0.6f + gearSize/2)
                gearTouch = true;

                //If new button is touched
            if (screenX > centreX-(optionSize/2) && screenX < centreX+(optionSize/2) && screenY > scrollY - (optionSize/2) && screenY < scrollY+(optionSize/2) && optionSelected == 0  && MainController.screenChange == 0) {
                newButtonTouch = true;
                newButtonState = newButtonTex2;
            }

            //if touching within scroll bounds
            if (screenX > 0 && screenX < scrollWidth && screenY > scrollHeight && screenY < Gdx.graphics.getHeight()) {
                //Set the touch
                if (!gearTouch && !zoomTouch) {
                    touchX = scrolled + screenX;
                    touched = true;
                }
            }
            else
                touched = false;

            //if touching left scroll button
            if (screenX > 0 && screenX < scrollButtonSize && screenY > 0 && screenY < scrollButtonSize)
                scrollLeftTouch = true;

            //if touching right scroll button
            if (screenX > Gdx.graphics.getWidth() - scrollButtonSize && screenX < Gdx.graphics.getWidth() && screenY > 0 && screenY < scrollButtonSize)
                scrollRightTouch = true;
        }
    }

    //MainMenu controls - drag
    public static void ControllerDrag(int screenX, int screenY, int pointer) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If not going anywhere
        if (goingTo == -1 && MainController.screenChange == 0) {
            //If touching within scrollbar bounds
            if (screenX > 0 && screenX < scrollWidth && screenY > scrollHeight && screenY < Gdx.graphics.getHeight()) {

                //Set scroll amount
                if (touched)
                    scrolled = touchX - screenX;

            } else {
                newButtonTouch = false;
                newButtonState = newButtonTex;
                touched = false;
            }

            //if touching left scroll button
            if (screenX > 0 && screenX < scrollButtonSize && screenY > 0 && screenY < scrollButtonSize)
                scrollLeftTouch = true;
            else
                scrollLeftTouch = false;

            //if touching right scroll button
            if (screenX > Gdx.graphics.getWidth() - scrollButtonSize && screenX < Gdx.graphics.getWidth() && screenY > 0 && screenY < scrollButtonSize)
                scrollRightTouch = true;
            else
                scrollRightTouch = false;
        }
    }

    //MainMenu controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If not going anywhere
        if (goingTo == -1 && MainController.screenChange == 0) {

            //If new button is touched
            if (screenX > centreX-(optionSize/2) && screenX < centreX+(optionSize/2) && screenY > scrollY - (optionSize/2) && screenY < scrollY+(optionSize/2)) {
                //If new button touched
                if (optionSelected == 0 && newButtonTouch) {
                    //If there is space in the file to save
                    if (SaveData.GetSize() < 1000) {
                        DialogBox.selection = 10;
                        resolutionNew = true;
                    }
                    //If limit reached, display error
                    else
                        sizeDialog = true;
                }
            }

            //New button is selected
            if (optionSelected == 0) {

                //If touching within open button bounds
                if (openButtonState == openButton2 && screenX > centreX - (buttonWidth/2) && screenX < centreX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2)) {

                    //If there is space in the file to open
                    if (SaveData.GetSize() < 1000)
                        Gdx.input.getTextInput(listener, "Enter sprite code:", "", "Enter a sprite string");
                    //If limit reached, display error
                    else
                        sizeDialog = true;
                }
            }
            else
            //If new button is not selected
            if (optionSelected != 0) {

                //If zoom button is touched
                if (screenX > 0 + zoomSize*0.6f - zoomSize/2 && screenX < 0 + zoomSize*0.6f + zoomSize/2 && screenY > Gdx.graphics.getHeight() - zoomSize*0.6f - zoomSize/2 && screenY < Gdx.graphics.getHeight() - zoomSize*0.6f + zoomSize/2 && zoomTouch) {
                    if (spriteMinis) {
                        zoomState = zoomOutButton;
                        spriteMinis = false;
                    }
                    else {
                        zoomState = zoomInButton;
                        spriteMinis = true;
                    }
                }

                //If touching within edit button bounds
                if (editButtonState == editButton2 && screenX > editButtonX - (buttonWidth/2) && screenX < editButtonX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2)) {
                    //Reset draw screen
                    DrawScreen.resetDrawScreen(spritesAmount-(optionSelected/optionSpaced),spriteList.get(spritesAmount-(optionSelected/optionSpaced)),Integer.valueOf(spriteList.get(spritesAmount-(optionSelected/optionSpaced)).substring(0,2)));
                }

                //If touching within copy button bounds
                if (copyButtonState == copyButton2 && screenX > copyButtonX - (buttonWidth/2) && screenX < copyButtonX + (buttonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2)) {

                    //If there is space in the file to save
                    if (SaveData.GetSize() < 1000) {
                        DialogBox.selection = Integer.valueOf(spriteList.get(spritesAmount-(optionSelected/optionSpaced)).substring(0,2));
                        resolutionCopy = true;
                    }
                    //If limit reached, display error
                    else
                        sizeDialog = true;
                }

                //If touching within share button bounds
                if (saveButtonState == saveButton2 && screenX > saveButtonX - (smallButtonWidth/2) && screenX < saveButtonX + (smallButtonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    Gdx.input.getTextInput(listener2, "Your sprite code:", MainController.compressString(spriteList.get(spritesAmount-(optionSelected/optionSpaced))), "Give this code to a friend!");

                //If touching within delete button bounds
                if (deleteButtonState == deleteButton2 && screenX > deleteButtonX - (smallButtonWidth/2) && screenX < deleteButtonX + (smallButtonWidth/2) && screenY > buttonY - (buttonHeight/2) && screenY < buttonY + (buttonHeight/2))
                    deletePermission = true;
            }

            //Gear button touched
            if (screenX > Gdx.graphics.getWidth() - gearSize*0.6f - gearSize/2 && screenX < Gdx.graphics.getWidth() - gearSize*0.6f + gearSize/2 && screenY > Gdx.graphics.getHeight() - gearSize*0.6f - gearSize/2 && screenY < Gdx.graphics.getHeight() - gearSize*0.6f + gearSize/2 && gearTouch) {
                DialogBox.selection = MainController.mainColour;
                colourDialog = true;
            }

            //if touching left scroll button
            if (screenX > 0 && screenX < scrollButtonSize && screenY > 0 && screenY < scrollButtonSize && scrolled > optionSpaced)
                goingTo = optionSpaced;

            //if touching right scroll button
            if (screenX > Gdx.graphics.getWidth() - scrollButtonSize && screenX < Gdx.graphics.getWidth() && screenY > 0 && screenY < scrollButtonSize)
                goingTo = scrollAmount;
        }

        //reset the buttons states and stuff
        newButtonTouch = false;
        newButtonState = newButtonTex;
        openButtonState = openButton;
        editButtonState = editButton;
        copyButtonState = copyButton;
        saveButtonState = saveButton;
        deleteButtonState = deleteButton;
        scrollLeftTouch = false;
        scrollRightTouch = false;
        zoomTouch = false;
        gearTouch = false;
        touched = false;

    }

    //Lerp
    public static Integer lerp (int a, int b, Float f) {
        int l = Math.round(a + f * (b - a));
        if (b > a)
            return l + ((b-a)/(b-a));
        else
            return l - 1;
    }

    //Text input
    public static class TextInput implements Input.TextInputListener {
        @Override
        public void input (String text) {
            spriteCode = text;
        }

        @Override
        public void canceled () {
        }
    }

    //Text input 2 (doesn't do anything)
    public static class TextInput2 implements Input.TextInputListener {
        @Override
        public void input (String text) {
        }

        @Override
        public void canceled () {
        }
    }

    //Dispose
    @Override
    public void dispose() {
        scrollBit.dispose();
        newButtonTex.dispose();
        newButtonTex2.dispose();
        newButtonState.dispose();
        editButton.dispose();
        editButton2.dispose();
        editButtonState.dispose();
        copyButton.dispose();
        copyButton2.dispose();
        copyButtonState.dispose();
        saveButton.dispose();
        saveButton2.dispose();
        saveButtonState.dispose();
        deleteButton.dispose();
        deleteButton2.dispose();
        deleteButtonState.dispose();
        openButton.dispose();
        openButton2.dispose();
        openButtonState.dispose();
        zoomInButton.dispose();
        zoomOutButton.dispose();
        gearIcon.dispose();
        zoomState.dispose();
        scrollLeft.dispose();
        scrollRight.dispose();
        font.dispose();
        batch.dispose();
    }
}