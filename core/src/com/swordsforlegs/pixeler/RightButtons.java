package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class RightButtons extends ApplicationAdapter{

    //canvas variables
    static Float canvasX = CanvasDrawer.canvasX;
    static Float canvasY = CanvasDrawer.canvasY;
    static Float canvasSize = CanvasDrawer.canvasSize;

    //Variables
    static boolean buttonUntouchable = false;
    static Float rightCentreX = canvasX + canvasSize + (Gdx.graphics.getWidth() / 8f);
    static Float centreY = Gdx.graphics.getHeight() / 2f;
    static Float buttonScale = 1f;
    static Float buttonAlpha = 1f;
    static Float flipButtonY = centreY / 1.625f;
    static Float flipButtonSize = canvasSize/5.6f;
    static Float flipSpace = 1.8f;
    static Float shiftButtonY = centreY * 1.56f;
    static Float shiftButtonSize = canvasSize/7.5f;
    static Float shiftSpacing = (shiftButtonSize/1.2f);
    static Float backSizeW = canvasSize/2.9f;
    static Float backSizeH = backSizeW * 0.567f;
    static Float backbuttonY = centreY * 1.01f;
    static Float backScale = 1f;
    static Float backAlpha = 1f;
    static boolean backToMenu = false;

    //Textures
    static Texture flipButtonh = new Texture("flipbuttonh.png");
    static Texture flipButtonh2 = new Texture("flipbuttonh2.png");
    static Texture flipbuttonhState = new Texture("flipbuttonh.png");

    static Texture flipButtonv = new Texture("flipbuttonv.png");
    static Texture flipButtonv2 = new Texture("flipbuttonv2.png");
    static Texture flipbuttonvState = new Texture("flipbuttonv.png");

    static Texture shiftUp = new Texture("shiftup.png");
    static Texture shiftUp2 = new Texture("shiftup2.png");
    static Texture shiftUpState = new Texture("shiftup.png");

    static Texture shiftRight = new Texture("shiftright.png");
    static Texture shiftRight2 = new Texture("shiftright2.png");
    static Texture shiftRightState = new Texture("shiftright.png");

    static Texture shiftDown = new Texture("shiftdown.png");
    static Texture shiftDown2 = new Texture("shiftdown2.png");
    static Texture shiftDownState = new Texture("shiftdown.png");

    static Texture shiftLeft = new Texture("shiftleft.png");
    static Texture shiftLeft2 = new Texture("shiftleft2.png");
    static Texture shiftLeftState = new Texture("shiftleft.png");

    static Texture backButton = new Texture("backbutton.png");
    static Texture backButton2 = new Texture("backbutton2.png");
    static Texture backButtonState = new Texture("backbutton.png");

    static void Main() {

        //Initialize the undo/redo buttons
        UndoRedo.Main();

        //Make buttons untouchable if smallsprite is picked up
        if (DrawScreen.smallTouched && MainController.screenChange == 0)
            buttonUntouchable = true;

        //Hide the buttons if small sprite touched
        if (DrawScreen.smallTouched) {
            if (buttonScale > 0) {
                buttonScale -= MainController.dt/15;
            }
            if (buttonAlpha > 0) {
                buttonAlpha -= MainController.dt/15f;
            }
        }
        //Show the buttons when small sprite released
        else {
            if (buttonScale < 1) {
                buttonScale += MainController.dt/15;
            }
            if (buttonAlpha < 1) {
                buttonAlpha += MainController.dt/15f;
            }
        }
        buttonScale = MainController.clamp(buttonScale,0,1);
        buttonAlpha = MainController.clamp(buttonAlpha,0,1);

        //Show back button when canvas is empty
        if (CanvasDrawer.spriteEmpty(CanvasDrawer.canvasSprite) && DrawScreen.circleShrink == DrawScreen.smallSize/1.4f) {
            if (backAlpha < 1)
                backAlpha += MainController.dt/7.5f;
            if (backScale < 1)
                backScale += MainController.dt/15f;
        }
        //Hide the back button
        else {
            if (backAlpha > 0)
                backAlpha -= MainController.dt/7.5f;
            if (backScale > 0.5f)
                backScale -= MainController.dt/15f;
        }
        backAlpha = MainController.clamp(backAlpha,0,1);
        backScale = MainController.clamp(backScale,0.5f,1);

        //Draw back button
        DrawSprite.Draw(backButtonState, rightCentreX, backbuttonY, backSizeW, backSizeH, backScale, backAlpha, Color.TEAL, true);

        //Draw horizontal flip button
        DrawSprite.Draw(flipbuttonhState, rightCentreX - flipButtonSize/flipSpace,  flipButtonY, flipButtonSize, flipButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Draw vertical flip button
        DrawSprite.Draw(flipbuttonvState, rightCentreX + flipButtonSize/flipSpace, flipButtonY, flipButtonSize, flipButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Draw shift up
        DrawSprite.Draw(shiftUpState, rightCentreX, shiftButtonY + shiftSpacing, shiftButtonSize, shiftButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Draw shift down
        DrawSprite.Draw(shiftDownState, rightCentreX, shiftButtonY - shiftSpacing, shiftButtonSize, shiftButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Draw shift left
        DrawSprite.Draw(shiftLeftState, rightCentreX - shiftSpacing, shiftButtonY, shiftButtonSize, shiftButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Draw shift right
        DrawSprite.Draw(shiftRightState, rightCentreX + shiftSpacing, shiftButtonY, shiftButtonSize, shiftButtonSize, buttonScale, buttonAlpha, Color.TEAL, true);

        //Menu button dialog
        if (backToMenu != false) {
            DialogBox.ConfirmDialog("Go back to menu without saving?",0);
            if (DialogBox.dialogResult == 1) {

                //Delete the recovered sprite
                MainController.prefs.remove("Sprite");
                MainController.prefs.remove("OldSprite");
                MainController.prefs.flush();

                MainMenu.dataLoaded = false;
                if (CanvasDrawer.oldSprite != -1)
                    MainMenu.scrolled = MainMenu.optionSpaced * (MainMenu.spritesAmount - CanvasDrawer.oldSprite + 1);
                MainController.screenChange = 2;
                DialogBox.dialogResult = 0;
                backToMenu = false;
            }
            else
            if (DialogBox.dialogResult == 2) {
                DialogBox.dialogResult = 0;
                backToMenu = false;
            }
        }

    }

    //CONTROLS ------------------------------------------------------------------------------------------CONTROLS------------------------------------------------------------------------------------------------------------>

    //Drawscreen controls - down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If buttons are touchable
        if (!buttonUntouchable) {

            //Horizontal flip button touched
            if (screenX > rightCentreX - flipButtonSize/flipSpace - (flipButtonSize / 2) && screenX < rightCentreX - flipButtonSize/flipSpace + (flipButtonSize / 2) && screenY > flipButtonY - (flipButtonSize / 2) && screenY < flipButtonY + (flipButtonSize / 2))
                flipbuttonhState = flipButtonh2;

            //Vertical flip button touched
            if (screenX > rightCentreX + flipButtonSize/flipSpace - (flipButtonSize / 2) && screenX < rightCentreX + flipButtonSize/flipSpace + (flipButtonSize / 2) && screenY > flipButtonY - (flipButtonSize / 2) && screenY < flipButtonY + (flipButtonSize / 2))
                flipbuttonvState = flipButtonv2;

            //Shift up touched
            if (screenX > rightCentreX - (shiftButtonSize / 2) && screenX < rightCentreX + (shiftButtonSize / 2) && screenY > shiftButtonY - (shiftButtonSize / 2) + shiftSpacing && screenY < shiftButtonY + (shiftButtonSize / 2) + shiftSpacing)
                shiftUpState = shiftUp2;

            //Shift down touched
            if (screenX > rightCentreX - (shiftButtonSize / 2) && screenX < rightCentreX + (shiftButtonSize / 2) && screenY > shiftButtonY - (shiftButtonSize / 2) - shiftSpacing && screenY < shiftButtonY + (shiftButtonSize / 2) - shiftSpacing)
                shiftDownState = shiftDown2;

            //Shift left touched
            if (screenX > rightCentreX - (shiftButtonSize / 2) - shiftSpacing && screenX < rightCentreX + (shiftButtonSize / 2) - shiftSpacing && screenY > shiftButtonY - (shiftButtonSize / 2) && screenY < shiftButtonY + (shiftButtonSize / 2))
                shiftLeftState = shiftLeft2;

            //Shift right touched
            if (screenX > rightCentreX - (shiftButtonSize / 2) + shiftSpacing && screenX < rightCentreX + (shiftButtonSize / 2) + shiftSpacing && screenY > shiftButtonY - (shiftButtonSize / 2) && screenY < shiftButtonY + (shiftButtonSize / 2))
                shiftRightState = shiftRight2;

            //Back touched
            if (screenX > rightCentreX - (backSizeW / 2)&& screenX < rightCentreX + (backSizeW / 2) && screenY > backbuttonY - (backSizeH / 2) && screenY < backbuttonY + (backSizeH / 2))
                backButtonState = backButton2;
        }
    }

    //Drawscreen controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If buttons are touchable
        if (!buttonUntouchable) {

            //Horizontal flip button let go
            if (flipbuttonhState == flipButtonh2 && screenX > rightCentreX - flipButtonSize/flipSpace - flipButtonSize / 2 && screenX < rightCentreX - flipButtonSize/flipSpace + flipButtonSize / 2 && screenY > flipButtonY - flipButtonSize / 2 && screenY < flipButtonY + (flipButtonSize / 2)) {
                CanvasDrawer.canvasSprite = CanvasDrawer.flipSpriteH(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Vertical flip button let go
            if (flipbuttonvState == flipButtonv2 && screenX > rightCentreX + flipButtonSize/flipSpace - (flipButtonSize / 2) && screenX < rightCentreX + flipButtonSize/flipSpace + (flipButtonSize / 2) && screenY > flipButtonY - (flipButtonSize / 2) && screenY < flipButtonY + (flipButtonSize / 2)) {
                CanvasDrawer.canvasSprite = CanvasDrawer.flipSpriteV(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Shift up released
            if (shiftUpState == shiftUp2 && screenX > rightCentreX - (shiftButtonSize / 2) && screenX < rightCentreX + (shiftButtonSize / 2) && screenY > shiftButtonY - (shiftButtonSize / 2) + shiftSpacing && screenY < shiftButtonY + (shiftButtonSize / 2) + shiftSpacing) {
                CanvasDrawer.canvasSprite = CanvasDrawer.shiftSpriteUp(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Shift down released
            if (shiftDownState == shiftDown2 && screenX > rightCentreX - (shiftButtonSize / 2) && screenX < rightCentreX + (shiftButtonSize / 2) && screenY > shiftButtonY - (shiftButtonSize / 2) - shiftSpacing && screenY < shiftButtonY + (shiftButtonSize / 2) - shiftSpacing) {
                CanvasDrawer.canvasSprite = CanvasDrawer.shiftSpriteDown(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Shift left released
            if (shiftLeftState == shiftLeft2 && screenX > rightCentreX - (shiftButtonSize / 2) - shiftSpacing && screenX < rightCentreX + (shiftButtonSize / 2) - shiftSpacing && screenY > shiftButtonY - (shiftButtonSize / 2) && screenY < shiftButtonY + (shiftButtonSize / 2)) {
                CanvasDrawer.canvasSprite = CanvasDrawer.shiftSpriteLeft(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Shift right released
            if (shiftRightState == shiftRight2 && screenX > rightCentreX - (shiftButtonSize / 2) + shiftSpacing && screenX < rightCentreX + (shiftButtonSize / 2) + shiftSpacing && screenY > shiftButtonY - (shiftButtonSize / 2) && screenY < shiftButtonY + (shiftButtonSize / 2)) {
                CanvasDrawer.canvasSprite = CanvasDrawer.shiftSpriteRight(CanvasDrawer.canvasSprite);
                UndoRedo.makeChange();
            }
            //Back released
            if (screenX > rightCentreX - (backSizeW / 2) && screenX < rightCentreX + (backSizeW / 2)& screenY > backbuttonY - (backSizeH / 2) && screenY < backbuttonY + (backSizeH / 2) && CanvasDrawer.spriteEmpty(CanvasDrawer.canvasSprite))
                backToMenu = true;
        }

        //Reset the buttons
        flipbuttonhState = flipButtonh;
        flipbuttonvState = flipButtonv;
        shiftUpState = shiftUp;
        shiftDownState = shiftDown;
        shiftLeftState = shiftLeft;
        shiftRightState = shiftRight;
        backButtonState = backButton;
        buttonUntouchable = false;
    }

    //Disposer
    @Override
    public void dispose () {
        flipButtonh.dispose();
        flipButtonh2.dispose();
        flipbuttonhState.dispose();
        flipButtonv.dispose();
        flipButtonv2.dispose();
        flipbuttonvState.dispose();
        shiftUp.dispose();
        shiftUp2.dispose();
        shiftUpState.dispose();
        shiftDown.dispose();
        shiftDown2.dispose();
        shiftDownState.dispose();
        shiftLeft.dispose();
        shiftLeft2.dispose();
        shiftLeftState.dispose();
        shiftRight.dispose();
        shiftRight2.dispose();
        shiftRightState.dispose();
        backButton.dispose();
        backButton2.dispose();
        backButtonState.dispose();
    }
}
