package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import sun.applet.Main;

public class DialogBox extends ApplicationAdapter {

    //Stuff
    static ShapeRenderer renderer = new ShapeRenderer();
    static SpriteBatch batch = new SpriteBatch();
    static BitmapFont font = MainController.cyrillicFont;

    //Textures
    static Texture dialogBox = new Texture("dialog.png");
    static Texture dialogBox2 = new Texture("dialog2.png");
    static Texture confirmButton = new Texture("confirmbutton.png");
    static Texture confirmButton2 = new Texture("confirmbutton2.png");
    static Texture confirmButtonState = new Texture("confirmbutton.png");
    static Texture denyButton = new Texture("denybutton.png");
    static Texture denyButton2 = new Texture("denybutton2.png");
    static Texture denyButtonState = new Texture("denybutton.png");
    static Texture okButton = new Texture("okbutton.png");
    static Texture okButton2 = new Texture("okbutton2.png");
    static Texture okButtonState = new Texture("okbutton.png");
    static Texture overwrite = new Texture("overwrite.png");
    static Texture sizeNubHover = new Texture("sizenubhover.png");
    static Texture sizeNubHover2 = new Texture("sizenubhover2.png");
    static Texture setSizeBar = new Texture("setsizebar.png");
    static Texture sizeBarNub = new Texture("sizebarnub.png");
    static Texture invalidSize = new Texture("invalidsize.png");
    static Texture gearIcon = new Texture("gearicon.png");

    //Variables
    static Float dialogX = Gdx.graphics.getWidth() / 2f;
    static Float dialogY = Gdx.graphics.getHeight() / 2f;
    static Float dialogWidth = Gdx.graphics.getWidth() / 1.4f;
    static Float dialogHeight = dialogWidth * 0.457f;
    static Float dialogLeft = dialogX - dialogWidth / 8.7f;
    static Float dialogRight = dialogX + dialogWidth / 8.7f;
    static Float buttonY = dialogY - dialogHeight / 3.8f;
    static Float buttonWidth = dialogWidth / 5;
    static Float buttonHeight = buttonWidth * 0.567f;
    static Float overwriteWidth = dialogWidth/2.5f;
    static Float overwriteHeight = overwriteWidth * 0.285f;
    static Float dialogAlpha = 0f;
    static Float darknessAlpha = 0f;
    static boolean dialogShown = false;
    static int dialogState = 0;
    static int setDialogResult = 0;
    static int dialogResult = 0;
    static Float spriteSize = dialogHeight/4.2f;
    static int dialogType = 0;

    //Grid size stuff
    static int maxSize = 20;
    static int maxColour = 11;
    static int maxSelect = 0;
    static int selection = 0;
    static int canvasSize = 16;
    static int sizeBarWidth = Math.round(MainMenu.scrollWidth/2.5f);
    static Float nubSize = sizeBarWidth * 0.09f;
    static Float sizeBarAlpha = 0f;
    static Float hoverSize = Gdx.graphics.getHeight()/3f;
    static Float hoverAlpha = 0f;
    static boolean sizeTouch = false;
    static boolean spriteListInit = false;
    static String[] spriteList = new String[maxSize+1];

    //Draw dialog: 0 - nothing, 1 - confirm, 2 - deny | type: 0 - confirmation, 1 - message, 2 - resolution, 3 - screen colour
    public static void ConfirmDialog(String message, int type) {

        //Set the size previews
        if (!spriteListInit) {
            spriteList[0] = "10_____Lxx________Lrccx______LrxxLcx______x__xcx________Lcx________Lrx_________xcx__________x_______________________L__________Lcx__________x_____";
            spriteList[1] = "01x";
            spriteList[2] = "02xbbx";
            spriteList[3] = "03xbWbxbWbx";
            spriteList[4] = "04xbxbxWxWxxxxbWxW";
            spriteList[5] = "05WxxxWbxbWbWxxbWbWbxbWxxbW";
            spriteList[6] = "06WbWbWbbWxxbWWxWbWbbxxxbWWxWbxbbWxxbW";
            spriteList[7] = "07xxxbxbxbWxWbxbWbxbxbxbWbWbWbWbxxxbWbWbWxWbWbWbxbW";
            spriteList[8] = "08WxxbWxWxxWbxbWxWWxxbWxWxxWbxbWbWWxxbWxxbbWbWxWbxWbWbWxxbbWbWxWbx";
            spriteList[9] = "09WbWbWbWbWbWxxbxbxbWxWxWbxbWbWxxbxbxbWbWxWbWbWbWbWbxxWbWbWbxbxbWbWbWbxxWbWbWbWbxbW";
            spriteList[10] = "10WbWbWbWbWbxWbxbWbWbWxbxbxbxbxbxWxWxWbxbWxbWxWbxbxbbWbWbWbWbWWbxbWxWbWbbWxWxWxWbWWbxbxbxbWbbWxWbxbWbW";
            spriteList[11] = "11WbWbWbWbWbWbWxWxWbWbWbWbxbxbxbxbWbWxWxWbxbWbWbxbxbxbxbWbWbWbWbWbWbWbWbxbxbWbWbWbWxWxWbWbWbWbxbxbWbWbWbWxWxWbWbWbWbWbWbWbW";
            spriteList[12] = "12WbWbWbWbWbWbbxbxxWbWbWbWWxWbWxWbxbxbbxbWxWbWbxbWWxWxxxWbxbxbbWbWbWbWbWbWWbWbxbxxWbWbbWbWxWbWxWbWWbWbxbWxWbWbbWbWxWxxxWbWWbWbWbWbWbWbbWbWbWbWbWbW";
            spriteList[13] = "13WbWbWbWbWbWbWbWbxbxbWbWbWbWbWxWbxbxbxbWbWbxbxbWbxbWbWbWxWbxbxbxbWbWbxbxbWbWbWbWbWbWbWbWbWbWbWbWbxbxbWbWbWbWbWxWbxbWbWbWbWbxbxbWbWbWbWbWxWbxbWbWbWbWbxbxbWbWbWbWbWbWbWbWbW";
            spriteList[14] = "14WbWbWbWbWbWbWbbWbWxWxWbWbWbWWbWbxbxbxbWbWbbWbWxWxxxxbWbWWbWbxbWbxbWbWbbWbWbWbWbWbWbWWbWbWxWxWbWbWbbWbWbWxWbWbWbWWbWbWxWxWbWbWbbWbWbWbWbWbWbWWbWbxbxbWbWbWbbWbWxWxWxWbWbWWbWbxbxxxxWbWbbWbWxWbWxWbWbW";
            spriteList[15] = "15WbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWxWxxxWbWbWbWbWbxbxbWbxbxbWbWbWxWxxbWbxbWbWbWbxbWbxbxbxbWbWbWxWxxbWbWbWbWbWbWbWbWbWbWbWbWbWbWxWxxxWbWbWbWbWbxbxbWbWbWbWbWbWxWxxbWbWbWbWbWbxbWbxbWbWbWbWbWxWxxbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbW";
            spriteList[16] = "16WbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWWxWbxbWbWbWxWbxbbxbxbWbWbWbxbxbWWxWxxbWxWxWxWxxbbxbxbxbWxWbxbxbxWxWbxbWxWxWxWbxbbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbW";
            spriteList[17] = "17WbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWxWxxxWbWbWxWxxxWbxbWbxbWbWbxbWbxbWxWbxbWxWxWxWbxbWbxbWxWbWxWbxbWxWbWxWbxbWxWxWxWbxbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbW";
            spriteList[18] = "18WbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWWxWbxxWbWbWbxbWxxbbxbxbWxWbWbWxWxWbxWxWbxxWbxbxbxbWxxbbxbxbWxWbxbWxWxWbxWxWbxxWbxbxbxbWxxbbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbW";
            spriteList[19] = "19WbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbxbxxxbWbWbWbxbxxxbWxWxWxWbWbWbWxWxWxWbxbxxxbWxWxWbxbxxxbWxWbWxWbWxWbWxWbWxWbxbWbxbWxWxWbxbWbxbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbWbW";
            spriteList[20] = "20WbWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWbWWbxxxbWbxxxbWbWbWbWbbxbWbxbxbWbxbxbWbxbWWbWbWxWxWbWxWbxbxbWbbWbWxWbxbWbxbWbxbWbWWbWxWbWxWbWxWbxbxbWbbWxWbWbxbWbxbxbWbxbWWxxxxxWbxxxbWbWbWbWbbWbWbWbWbWbWbWbWbWbWWbWbWbxxxbWbxxxbWbWbbWbWbxbWbxbxbWbxbWbWWbWbWbWbWxWxWbWxWbWbbWbWbWbWxWbxbWbxbWbWWbWbWbWxWbWxWbWxWbWbbWbWbWxWbWbxbWbxbWbWWbWbWxxxxxWbxxxbWbWbbWbWbWbWbWbWbWbWbWbWWbWbWbWbWbWbWbWbWbWbbWbWbWbWbWbWbWbWbWbW";
            spriteListInit = true;
        }

        //Show the dialog
        dialogShown = true;
        dialogType = type;

        //set the max sizes
        if (dialogType == 2)
            maxSelect = maxSize;
        else if (dialogType == 3) {
            maxSelect = maxColour;
            //Set theme colour
            ColourPalette.themeColour = MainController.mainColourList[MainController.mainColour];
            ColourPalette.colourArray[17] = new Color(ColourPalette.fCol(Math.round(ColourPalette.themeColour.r*255+55),Math.round(ColourPalette.themeColour.g*255+55),Math.round(ColourPalette.themeColour.b*255+55))); //theme light
            ColourPalette.colourArray[18] = new Color(ColourPalette.fCol(Math.round(ColourPalette.themeColour.r*255+20),Math.round(ColourPalette.themeColour.g*255+20),Math.round(ColourPalette.themeColour.b*255+20)));  //theme dark
            //Set random colour
            while (ColourPalette.randColour == MainController.mainColourList[MainController.mainColour]) {
                ColourPalette.randColour = MainController.randomColour();
                ColourPalette.colourArray[19] = new Color(ColourPalette.fCol(Math.round(ColourPalette.randColour.r*255+55),Math.round(ColourPalette.randColour.g*255+55),Math.round(ColourPalette.randColour.b*255+55))); //random light
                ColourPalette.colourArray[20] = new Color(ColourPalette.fCol(Math.round(ColourPalette.randColour.r*255+20),Math.round(ColourPalette.randColour.g*255+20),Math.round(ColourPalette.randColour.b*255+20))); //random dark
            }
        }

        //set the values the slider is controlling
        if (dialogType == 2)
            canvasSize = selection;
        else if (dialogType == 3) {
            MainController.mainColour = selection;
            //save background preference
            MainController.prefs.putInteger("Colour",MainController.mainColour);
            MainController.prefs.flush();
        }

        //Set dialog fade in/out
        //Dialog showing
        if (dialogState == 0 && dialogShown) {
            if (darknessAlpha < 0.6)
                darknessAlpha += MainController.dt / 30;
            if (dialogAlpha < 1)
                dialogAlpha += MainController.dt / 15;
        }
        //Not showing
        else if (dialogState == 1 && dialogShown) {
            if (darknessAlpha > 0)
                darknessAlpha -= MainController.dt / 30;
            if (dialogAlpha > 0)
                dialogAlpha -= MainController.dt / 15;
            else if (dialogAlpha == 0) {
                dialogShown = false;
                dialogState = 0;
                dialogResult = setDialogResult;
                darknessAlpha = 0f;
                setDialogResult = 0;
            }
        }

        //Set clamps
        darknessAlpha = MainController.clamp(darknessAlpha, 0, 0.6f);
        dialogAlpha = MainController.clamp(dialogAlpha, 0, 1);

        //Darken the background
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draw darkness
        renderer.setColor(0, 0, 0, darknessAlpha);
        renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //End renderer
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //Draw box and shadow
        DrawSprite.Draw(dialogBox2, dialogX + dialogWidth / 30, dialogY - dialogWidth / 30, dialogWidth, dialogHeight, 1f, (0.1f)*dialogAlpha, Color.BLACK, true);
        DrawSprite.Draw(dialogBox2, dialogX, dialogY, dialogWidth, dialogHeight, 1f, dialogAlpha, MainController.mainColourList[MainController.mainColour], true);
        DrawSprite.Draw(dialogBox, dialogX, dialogY, dialogWidth, dialogHeight, 1f, dialogAlpha, Color.TEAL, true);

        //Draw overwrite box if necessary, lift text up
        if (message == "Save over the existing sprite?") {
            //Draw text
            batch.begin();
            font.setColor(0,0,0,(0.2f)*dialogAlpha);
            font.draw(batch, message, dialogX + 3,dialogY*1.385f - 3,0,Align.center,false);
            font.setColor(1,1,1,dialogAlpha);
            font.draw(batch, message, dialogX,dialogY*1.385f,0,Align.center,false);
            batch.end();
            DrawSprite.Draw(overwrite, dialogX, dialogY * 1.05f, overwriteWidth, overwriteHeight, 1f, dialogAlpha, Color.TEAL, true);
            //Old sprite
            SpriteDrawer.Draw(MainMenu.spriteList.get(CanvasDrawer.oldSprite), spriteSize, dialogLeft - spriteSize*0.249f, dialogY + spriteSize*0.175f, Color.TEAL, dialogAlpha);
            //New sprite
            SpriteDrawer.Draw(CanvasDrawer.canvasSprite, spriteSize, dialogRight + spriteSize*0.249f, dialogY + spriteSize*0.175f, Color.TEAL, dialogAlpha);
        }
        else if (dialogType == 2 || dialogType == 3) {
            //Draw text
            batch.begin();
            font.setColor(0,0,0,(0.2f)*dialogAlpha);
            font.draw(batch, message, dialogX + 3,dialogY*1.31f - 3,0,Align.center,false);
            font.setColor(1,1,1,dialogAlpha);
            font.draw(batch, message, dialogX,dialogY*1.31f,0,Align.center,false);
            batch.end();
        }
        //Move text down if not overwriting sprite
        else {
            //Draw text
            batch.begin();
            font.setColor(0,0,0,(0.2f)*dialogAlpha);
            font.draw(batch, message, dialogX + 3,dialogY*1.17f - 3,0,Align.center,false);
            font.setColor(1,1,1,dialogAlpha);
            font.draw(batch, message, dialogX,dialogY*1.17f,0,Align.center,false);
            batch.end();
        }

        //Draw buttons
        if (type == 0 || type == 2) {
            DrawSprite.Draw(confirmButtonState, dialogLeft, buttonY, buttonWidth, buttonHeight, 1f, dialogAlpha, Color.TEAL, true);
            DrawSprite.Draw(denyButtonState, dialogRight, buttonY, buttonWidth, buttonHeight, 1f, dialogAlpha, Color.TEAL, true);
        }
        else if (type == 1 || type == 3)
            DrawSprite.Draw(okButtonState, dialogX, buttonY, buttonWidth, buttonHeight, 1f, dialogAlpha, Color.TEAL, true);

        //Draw resolution slider
        if (type == 2 || type == 3) {
            sizeBarAlpha = MainController.clamp(sizeBarAlpha,0,1);

            //Size bar hoverer fade in/out
            if (sizeTouch) {
                if (hoverAlpha < 1)
                    hoverAlpha += MainController.dt/10;
            }
            else {
                if (hoverAlpha > 0)
                    hoverAlpha -= MainController.dt/10;

            }
            hoverAlpha = MainController.clamp(hoverAlpha,0,1);

            //Draw the grid size bar
            DrawSprite.Draw(setSizeBar, dialogX, dialogY, sizeBarWidth*1f, nubSize*0.25f, 0.9f, dialogAlpha, Color.TEAL, true);
            //Draw the pointer
            DrawSprite.Draw(sizeBarNub,dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY, nubSize, nubSize, 0.8f+(hoverAlpha*0.2f), dialogAlpha, Color.TEAL, true);
            //Draw the size nub hoverer
            DrawSprite.Draw(sizeNubHover2,dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize/1.9f + hoverAlpha*(hoverSize/10), hoverSize, hoverSize, 1f, hoverAlpha, MainController.mainColourList[MainController.mainColour], true);
            DrawSprite.Draw(sizeNubHover,dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY  + hoverSize/1.9f + hoverAlpha*(hoverSize/10), hoverSize, hoverSize, 1f, hoverAlpha, Color.TEAL, true);

            //Draw hover sprite
            if (message == "Resolution of copied sprite?") {
                //Draw if cropped sprite will not be empty
                if (!CanvasDrawer.spriteEmpty(CanvasDrawer.spriteConverter(MainMenu.spriteList.get(MainMenu.spritesAmount-(MainMenu.optionSelected/MainMenu.optionSpaced)),DialogBox.selection)))
                    SpriteDrawer.Draw(CanvasDrawer.spriteConverter(MainMenu.spriteList.get(MainMenu.spritesAmount-(MainMenu.optionSelected/MainMenu.optionSpaced)),DialogBox.selection), spriteSize, dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSize) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize/1.65f + hoverAlpha*(hoverSize/10), Color.TEAL, hoverAlpha);
                else
                    DrawSprite.Draw(invalidSize, dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize/1.65f + hoverAlpha*(hoverSize/10), hoverSize/4, hoverSize/4,1f, hoverAlpha, Color.TEAL, true);
            }
            else if (message == "Resolution of new sprite?") {
                if (selection > 0 && selection <= maxSelect)
                    SpriteDrawer.Draw(spriteList[selection], spriteSize, dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize/1.65f + hoverAlpha*(hoverSize/10), Color.TEAL, hoverAlpha);
                else
                    SpriteDrawer.Draw(spriteList[0], spriteSize, dialogX-(sizeBarWidth/2) + selection*(sizeBarWidth/maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize/1.65f + hoverAlpha*(hoverSize/10), Color.TEAL, hoverAlpha);
            }
            else if (type == 3)
                DrawSprite.Draw(gearIcon, dialogX - (sizeBarWidth / 2) + selection * (sizeBarWidth / maxSelect) - ((sizeBarWidth/maxSelect)/2), dialogY + hoverSize / 1.65f + hoverAlpha * (hoverSize / 10), hoverSize / 3, hoverSize / 3, 1f, hoverAlpha, Color.WHITE, true);
        }

        //Close dialog when result returned
        if (setDialogResult != 0)
            dialogState = 1;
    }

    //DialogBox controls - touch down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If dialog box fully visible
        if (dialogAlpha == 1) {
            //Confirm dialog
            if (dialogType == 0 || dialogType == 2) {
                //Touching confirm
                if (screenX > dialogLeft - buttonWidth/2 && screenX < dialogLeft + buttonWidth/2 && screenY > buttonY - buttonHeight/2 && screenY < buttonY + buttonHeight/2)
                    confirmButtonState = confirmButton2;

                //Touching deny
                if (screenX > dialogRight - buttonWidth/2 && screenX < dialogRight + buttonWidth/2 && screenY > buttonY - buttonHeight/2 && screenY < buttonY + buttonHeight/2)
                    denyButtonState = denyButton2;
            }
            //Message dialog
            else if (dialogType == 1 || dialogType == 3) {
                //Touching ok
                if (screenX > dialogX - buttonWidth/2 && screenX < dialogX + buttonWidth/2 && screenY > buttonY - buttonHeight/2 && screenY < buttonY + buttonHeight/2)
                    okButtonState = okButton2;
            }
        }
    }

    //DialogBox controls - touch drag
    public static void ControllerDrag(int screenX, int screenY, int pointer) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //Touching slider
        if (dialogType == 2 || dialogType == 3) {
            //If touching within sizebar bounds and new button is selected
            if (screenX > dialogX-(sizeBarWidth/2) && screenX < dialogX+(sizeBarWidth/2) && screenY > dialogY - nubSize/2 && screenY < dialogY + nubSize*2) {
                selection = Math.round((screenX - (Gdx.graphics.getWidth()-(dialogX+(sizeBarWidth/2)))) / (sizeBarWidth/maxSelect));
                sizeTouch = true;
            }
            else
                sizeTouch = false;

            //clamp the values
            selection = Math.round(MainController.clamp(selection,1f,maxSelect*1f));
            MainController.mainColour = Math.round(MainController.clamp(MainController.mainColour, 1f, maxSelect * 1f));
        }
    }

    //DialogBox controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If dialog box fully visible
        if (dialogAlpha == 1) {
            //Confirm dialog
            if (dialogType == 0 || dialogType == 2) {
                //Touching confirm
                if (confirmButtonState == confirmButton2 && screenX > dialogLeft - buttonWidth / 2 && screenX < dialogLeft + buttonWidth / 2 && screenY > buttonY - buttonHeight / 2 && screenY < buttonY + buttonHeight / 2)
                    setDialogResult = 1;

                //Touching deny
                if (denyButtonState == denyButton2 && screenX > dialogRight - buttonWidth / 2 && screenX < dialogRight + buttonWidth / 2 && screenY > buttonY - buttonHeight / 2 && screenY < buttonY + buttonHeight / 2)
                    setDialogResult = 2;
            }
            //Message dialog
            else if (dialogType == 1 || dialogType == 3) {
                //Touching ok
                if (okButtonState == okButton2 && screenX > dialogX - buttonWidth/2 && screenX < dialogX + buttonWidth/2 && screenY > buttonY - buttonHeight/2 && screenY < buttonY + buttonHeight/2)
                    setDialogResult = 1;
            }
        }

        //Set both buttons untouched
        confirmButtonState = confirmButton;
        denyButtonState = denyButton;
        okButtonState = okButton;
        sizeTouch = false;
    }

    //Dispose
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
        dialogBox.dispose();
        dialogBox2.dispose();
        confirmButton.dispose();
        confirmButton2.dispose();
        confirmButtonState.dispose();
        denyButton.dispose();
        denyButton2.dispose();
        denyButtonState.dispose();
        okButton.dispose();
        okButton2.dispose();
        okButtonState.dispose();
        overwrite.dispose();
        setSizeBar.dispose();
        sizeNubHover.dispose();
        sizeNubHover2.dispose();
        sizeBarNub.dispose();
        invalidSize.dispose();
    }
}
