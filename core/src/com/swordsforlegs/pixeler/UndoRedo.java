package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class UndoRedo extends ApplicationAdapter{

    //Textures
    static Texture undoButton = new Texture("undobutton.png");
    static Texture undoButton2 = new Texture("undobutton2.png");
    static Texture undoButton3 = new Texture("undobutton3.png");
    static Texture undoButtonState = new Texture("undobutton.png");
    static Texture redoButton = new Texture("redobutton.png");
    static Texture redoButton2 = new Texture("redobutton2.png");
    static Texture redoButton3 = new Texture("redobutton3.png");
    static Texture redoButtonState = new Texture("redobutton.png");

    static Texture timelinestart = new Texture("timelinestart.png");
    static Texture timelinestart2 = new Texture("timelinestart2.png");
    static Texture timelinemid = new Texture("timelinemiddle.png");
    static Texture timelinemid2 = new Texture("timelinemiddle2.png");
    static Texture timelineend = new Texture("timelineend.png");
    static Texture timelineend2 = new Texture("timelineend2.png");

    static Texture timelineSelect = new Texture("timelineselect.png");
    static Texture timelineBox = new Texture("timelinebox.png");
    static Texture emptySprite = new Texture("invalidsize.png");
    static Texture scrollBit = new Texture("barscroll2.png");

    //Button variables
    static Float centreY = Gdx.graphics.getHeight() / 2f;
    static Float unredoW = CanvasDrawer.canvasSize/7f;
    static Float unredoH = unredoW * 1.146f;
    static Float rightCentreX = RightButtons.rightCentreX;
    static Float unredoButtonY = centreY / 3.9f;
    static Float undoButtonX = rightCentreX - unredoW/1.9f;
    static Float redoButtonX = rightCentreX + unredoW/1.9f;

    //Timeline variables
    static List<String> spriteChanges = new ArrayList<String>();
    static int changePosition = 0;
    static int timelineScrollable = 0;
    static int timelineScrolled = 0;
    static Float timelineShown = 0f;
    static Float showTime = 1000f;
    static Float timelineAlpha = 0f;
    static Float timelineH = Gdx.graphics.getHeight()/4.7f;
    static Float timelineShift = 0f;
    static Float timelineShiftMax = timelineH/4;
    static Float timelineY = unredoButtonY - timelineShiftMax;
    static Float startX = Gdx.graphics.getWidth()/ 23f;
    static Float startW = timelineH * 0.47f;
    static Float endX = undoButtonX - unredoW*1.3f;
    static Float endW = timelineH * 0.673f;
    static Float midX = ((endX - endW/2) - (startX + startW/2))/2 + startX + startW/2;
    static Float midW = ((endX - endW/2) - (startX + startW/2));
    static int changesNum = 8;
    static Float spriteSize = midW/10;
    static Float spriteSpace = 1.3f;
    static Float scrollBitW = timelineH/5;
    static Float scrollBitH = scrollBitW * 0.416f;
    static int scrollPos = 0;
    static int scrollGoingTo = 0;

    //Undo/redo buttons and timeline
    public static void Main() {

        //lower the time the timeline is shown
        if (timelineShown > 0)
            timelineShown -= MainController.dt*15;
        timelineShown = MainController.clamp(timelineShown,0,showTime);

        //Draw the timeline
        if (timelineShown > 0) {
            //Fade in the timeline
            if (timelineAlpha < 1)
                timelineAlpha += MainController.dt/10;
            if (timelineShift < timelineShiftMax)
                timelineShift += MainController.dt*4;
        } else {
            //Fade out the timeline
            if (timelineAlpha > 0)
                timelineAlpha -= MainController.dt/10;
            if (timelineShift > 0)
                timelineShift -= MainController.dt*4;
        }
        timelineShift = MainController.clamp(timelineShift,0,timelineShiftMax);
        timelineAlpha = MainController.clamp(timelineAlpha,0,1);

        //Start piece
        DrawSprite.Draw(timelinestart2, startX + timelineH/13, timelineY + timelineShift - timelineH/13, startW, timelineH, 1f,0.15f*timelineAlpha, Color.BLACK,true);
        DrawSprite.Draw(timelinestart2, startX, timelineY + timelineShift, startW, timelineH, 1f,timelineAlpha, MainController.mainColourList[MainController.mainColour],true);
        DrawSprite.Draw(timelinestart, startX, timelineY + timelineShift, startW, timelineH, 1f,timelineAlpha, Color.TEAL,true);
        //Middle piece
        DrawSprite.Draw(timelinemid2, midX + timelineH/13, timelineY + timelineShift - timelineH/13, midW, timelineH,1f,0.15f * timelineAlpha, Color.BLACK,true);
        DrawSprite.Draw(timelinemid2, midX, timelineY + timelineShift, midW, timelineH,1f,timelineAlpha, MainController.mainColourList[MainController.mainColour],true);
        DrawSprite.Draw(timelinemid, midX, timelineY + timelineShift, midW, timelineH,1f,timelineAlpha, Color.TEAL,true);
        //End piece
        DrawSprite.Draw(timelineend2, endX + timelineH/13, timelineY + timelineShift - timelineH/13, endW, timelineH, 1f,0.15f * timelineAlpha, Color.BLACK,true);
        DrawSprite.Draw(timelineend2, endX, timelineY + timelineShift, endW, timelineH, 1f,timelineAlpha, MainController.mainColourList[MainController.mainColour],true);
        DrawSprite.Draw(timelineend, endX, timelineY + timelineShift, endW, timelineH, 1f,timelineAlpha, Color.TEAL,true);

        //Set the timeline scroll
        timelineScrollable = spriteChanges.size() - changesNum;

        //Draw past changes
        for (int i = 0; i < spriteChanges.size(); i++) {

            //Only draw as many as changesNum
            if (i + timelineScrolled < changesNum + timelineScrolled) {
                //Draw the container
                DrawSprite.Draw(timelineBox, startX*2.2f + ((spriteSize)*spriteSpace) * i,timelineY + timelineShift, spriteSize,spriteSize, 1f, timelineAlpha,Color.TEAL,true);

                //Draw the sprite
                if (!CanvasDrawer.spriteEmpty(spriteChanges.get(i + timelineScrolled)))
                    SpriteDrawer.Draw(spriteChanges.get(i + timelineScrolled), spriteSize, startX*2.2f + ((spriteSize)*spriteSpace) * i,timelineY + timelineShift,Color.TEAL, timelineAlpha);
                else
                    DrawSprite.Draw(emptySprite, startX*2.2f + ((spriteSize)*spriteSpace) * i,timelineY + timelineShift, timelineH/3f,timelineH/3f, 1f, timelineAlpha,Color.TEAL,true);

                //Draw the selector
                if (changePosition == i + timelineScrolled)
                    DrawSprite.Draw(timelineSelect,startX*2.2f + ((spriteSize)*spriteSpace) * i,timelineY + timelineShift,spriteSize,spriteSize,1f,timelineAlpha,Color.TEAL,true);
            }
        }

        //Lerp the scroller
        if (scrollPos != scrollGoingTo)
            scrollPos = Math.round(MainMenu.lerp(Math.round(scrollPos),Math.round(scrollGoingTo),0.1f));

        //Draw the scrollbit
        if (timelineScrollable > 0)
            DrawSprite.Draw(scrollBit, scrollPos + startX*1.935f, timelineShift, scrollBitW, scrollBitH, 1f, timelineAlpha, Color.TEAL, true);

        //Draw undo/redo buttons
        if (changePosition > 0)
            DrawSprite.Draw(undoButtonState, undoButtonX,  unredoButtonY, unredoW, unredoH, RightButtons.buttonScale, RightButtons.buttonAlpha, Color.TEAL, true);
        else
            DrawSprite.Draw(undoButton3, undoButtonX,  unredoButtonY, unredoW, unredoH, RightButtons.buttonScale, RightButtons.buttonAlpha, Color.TEAL, true);

        if (changePosition < spriteChanges.size()-1)
            DrawSprite.Draw(redoButtonState, redoButtonX,  unredoButtonY, unredoW, unredoH, RightButtons.buttonScale, RightButtons.buttonAlpha, Color.TEAL, true);
        else
            DrawSprite.Draw(redoButton3, redoButtonX,  unredoButtonY, unredoW, unredoH, RightButtons.buttonScale, RightButtons.buttonAlpha, Color.TEAL, true);

        //Clamp the scroll
        scrollGoingTo = Math.round(MainController.clamp(scrollGoingTo,0,midW*0.96f));
        scrollPos = Math.round(MainController.clamp(scrollPos,0,midW*0.96f));

    }

    //When user makes a change to the sprite, add pixel, flip, shift, etc.
    public static void makeChange() {

        //If new sprite, adding start state
        if (spriteChanges.size() == 0) {
            spriteChanges.add(CanvasDrawer.canvasSprite);
            changePosition = spriteChanges.size()-1;
        }
        //only add change when change was made
        else if (!spriteChanges.get(changePosition).equals(CanvasDrawer.canvasSprite)) {
            if (changePosition < spriteChanges.size()-1) {
                spriteChanges = spriteChanges.subList(0,changePosition + 1);
            }
            spriteChanges.add(CanvasDrawer.canvasSprite);
            changePosition = spriteChanges.size()-1;

            //Set the scroll position
            if (changePosition > (changesNum-1))
                timelineScrolled = changePosition - (changesNum-1);
            else
                timelineScrolled = 0;

            scrollGoingTo = Math.round(midW*0.96f);

        }
    }

    // --------------------------------------------------------------------------- CONTROLS ------------------------------------------------------------------------------------------->

    //Drawscreen controls - touch down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If buttons are touchable
        if (!RightButtons.buttonUntouchable) {

            //Undo touched
            if (screenX > undoButtonX - (unredoW / 2)&& screenX < undoButtonX + (unredoW / 2) && screenY > unredoButtonY - (unredoH / 2) && screenY < unredoButtonY + (unredoH / 2) && changePosition > 0)
                undoButtonState = undoButton2;

            //Redo touched
            if (screenX > redoButtonX - (unredoW / 2)&& screenX < redoButtonX + (unredoW / 2) && screenY > unredoButtonY - (unredoH / 2) && screenY < unredoButtonY + (unredoH / 2) && changePosition < spriteChanges.size()-1)
                redoButtonState = redoButton2;
        }
    }

    //Drawscreen controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If buttons are touchable
        if (!RightButtons.buttonUntouchable) {

            //Undo touched
            if (screenX > undoButtonX - (unredoW / 2)&& screenX < undoButtonX + (unredoW / 2) && screenY > unredoButtonY - (unredoH / 2) && screenY < unredoButtonY + (unredoH / 2) && undoButtonState == undoButton2 && changePosition > 0) {
                timelineShown = showTime;
                changePosition -= 1;
                if (changePosition < timelineScrolled)
                    timelineScrolled -= 1;
                CanvasDrawer.canvasSprite = spriteChanges.get(changePosition);
                scrollGoingTo = Math.round((timelineScrolled*1f/timelineScrollable)*(midW*0.96f));
            }

            //Redo touched
            if (screenX > redoButtonX - (unredoW / 2)&& screenX < redoButtonX + (unredoW / 2) && screenY > unredoButtonY - (unredoH / 2) && screenY < unredoButtonY + (unredoH / 2) && redoButtonState == redoButton2 && changePosition < spriteChanges.size()-1) {
                timelineShown = showTime;
                changePosition += 1;
                if (changePosition > timelineScrolled + (changesNum-1))
                    timelineScrolled += 1;
                CanvasDrawer.canvasSprite = spriteChanges.get(changePosition);
                scrollGoingTo = Math.round((timelineScrolled*1f/timelineScrollable)*(midW*0.96f));
            }

        }
        undoButtonState = undoButton;
        redoButtonState = redoButton;
    }

    //Dispose
    public void dispose() {
        undoButton.dispose();
        undoButton2.dispose();
        undoButtonState.dispose();
        redoButton.dispose();
        redoButton2.dispose();
        redoButtonState.dispose();
    }
}
