package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ColourPalette extends ApplicationAdapter{

    //Set colour variables
    static Color themeColour = MainController.mainColourList[MainController.mainColour];
    final static Color[] colourArray = {
        new Color(fCol(25,25,25)),     //black
        new Color(fCol(236,112,99)),   //red
        new Color(fCol(245,160,150)),  //lighter red
        new Color(fCol(240,178,122)),  //orange
        new Color(fCol(255,255,100)),  //yellow
        new Color(fCol(139,226,176)),  //green
        new Color(fCol(46,204,113)),   //darker green
        new Color(fCol(102,232,255)),  //light blue
        new Color(fCol(40,183,251)),   //darker blue
        new Color(fCol(195,153,255)),  //purple
        new Color(fCol(254,186,235)),  //pink
        new Color(fCol(245,203,169)),  //light brown
        new Color(fCol(198,163,119)),  //brown
        new Color(fCol(100,100,100)),  //dark grey
        new Color(fCol(160,160,160)),  //grey
        new Color(fCol(200,200,200)),  //light grey
        new Color(fCol(255,255,255)),  //white
        new Color(fCol(Math.round(themeColour.r*255+55),Math.round(themeColour.g*255+55),Math.round(themeColour.b*255+55))), //theme light
        new Color(fCol(Math.round(themeColour.r*255+20),Math.round(themeColour.g*255+20),Math.round(themeColour.b*255+20)))  //theme dark
    };

    final static char[] colourCodes = {'x','c', 'r', 'o', 'y', 'g', 'd', 'b', 'i', 'u', 'p', 's', 'w', 'D', 'G', 'L', 'W', 't', 'T', '_'};

    //Variables
    static ShapeRenderer renderer = new ShapeRenderer();
    static Integer buttonSelected = 1;
    static char currentColour = 'x';
    static Float barWidth = CanvasDrawer.canvasSize/2.42f;
    static Float barHeight = Gdx.graphics.getHeight()*1f;
    static Float barCentre = barWidth/2;
    static Float buttonWidth = barWidth * 0.6f;
    static Float buttonHeight = buttonWidth * 0.56f;
    static Float touchY = 0f;
    static boolean touched = false;
    static Float touchedX = 0f;
    static Float touchedY = 0f;

    static Float scrollable = -((colourArray.length-5)*(buttonHeight*1.2f)+(buttonHeight/2));
    static float scrolled = 0f;
    static float scrollBitAlpha = 1f;

    //Textures
    static Texture scrollBit = new Texture("barscroll.png");
    static Texture colourButton = new Texture("colourbutton.png");
    static Texture colourButton2 = new Texture("colourbutton2.png");
    static Texture colourOverlay = new Texture("colouroverlay.png");
    static Texture colourOverlay2 = new Texture("colouroverlay2.png");
    static Texture eraseButton = new Texture("erasebutton.png");
    static Texture eraseButton2 = new Texture("erasebutton2.png");

    //Draw the colour palette
    public static void Palette() {

        //Set the theme colour
        themeColour = MainController.mainColourList[MainController.mainColour];

        //Start renderer
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draw the bar
        renderer.setColor(1,1,1,0.3f);
        renderer.rect(0,0,barWidth, barHeight);

        //End renderer
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //Clamp the scroller
        scrolled = MainController.clamp(scrolled,scrollable,0);

        //Set the scrollbit alpha
        if (touched) {
            if (scrollBitAlpha < 1)
                scrollBitAlpha += MainController.dt/15f;
        }
        else {
            if (scrollBitAlpha > 0)
                scrollBitAlpha -= MainController.dt/15f;
        }
        scrollBitAlpha = MainController.clamp(scrollBitAlpha,0,1);

        //Draw the scroller
        DrawSprite.Draw(scrollBit, barWidth * 0.91f, barHeight * 0.95f - (scrolled/scrollable)*(barHeight*0.9f), barHeight/35, (barHeight/35)*2.4f, 1f, scrollBitAlpha, Color.TEAL, true);

        //Draw the colours
        for (int i = 0; i < colourArray.length+2; i++) {
            //Eraser
            if (i == 0 || i == colourArray.length+1) {
                if (buttonSelected != i)
                    DrawSprite.Draw(eraseButton, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, Color.TEAL, true);
                else
                    DrawSprite.Draw(eraseButton2, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, Color.TEAL, true);
            }
            //Colour
            else if (i < colourArray.length+1 && i > 0) {
                //button up
                if (buttonSelected != i) {
                    DrawSprite.Draw(colourButton, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, Color.TEAL, true);
                    DrawSprite.Draw(colourOverlay, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, colourArray[i-1], true);
                }
                //button down
                else {
                    DrawSprite.Draw(colourButton2, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, Color.TEAL, true);
                    DrawSprite.Draw(colourOverlay2, barCentre,barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled, buttonWidth, buttonHeight, 1f, 1f, colourArray[i-1], true);
                }
            }
            //Select a colour
            if (MainController.screenChange == 0 && touchedX > barCentre - (buttonWidth/2) && touchedX < barCentre + (buttonWidth/2) &&
                    touchedY > barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled - (buttonHeight/2) && touchedY <  barHeight - (buttonHeight/2)*1.4f - (buttonHeight*1.2f)*i - scrolled + (buttonHeight/2)) {
                if (i == 0 || i == colourArray.length+1)
                    currentColour = '_';
                else
                    currentColour = colourCodes[i-1];
                buttonSelected = i;
            }
        }
    }

    //ColourPalette controls - touch down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {

    //Flip the y
    screenY = Gdx.graphics.getHeight() - screenY;

        //Return the positions of screenX and screenY for use in selecting the colour
        touchedX = screenX * 1f;
        touchedY = screenY * 1f;
        touchY = scrolled + screenY * 1f;

        //Set touch to true if not true while touching inside scroll bounds
        if (screenX > 0 && screenX < barWidth && screenY > 0 && screenY < barHeight) {
            if (!touched)
                touched = true;
        }
        else
            touched = false;

    }

    //ColourPalette controls - drag
    public static void ControllerDrag(int screenX, int screenY, int pointer) {

        //Flip the y
        screenY = Gdx.graphics.getHeight() - screenY;

        //If touching within scrollbar bounds
        if (screenX > 0 && screenX < barWidth && screenY > 0 && screenY < barHeight) {

            //Set scroll amount
            if (touched) {
                scrolled = touchY - screenY;
                //Return the positions of screenX and screenY for use in selecting the colour
                touchedX = screenX * 1f;
                touchedY = screenY * 1f;
            }
            else
                touched = false;
        }
    }

    //ColourPalette controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {

        //Make scrollbar no longer touched
        touched = false;

    }

    //RGB to float converter
    public static Color fCol(int r, int g, int b) { return(new Color(r/255f, g/255f, b/255f, 1f)); }

    //Dispose
    @Override
    public void dispose() {
        renderer.dispose();
        scrollBit.dispose();
        colourButton.dispose();
        colourButton2.dispose();
        colourOverlay.dispose();
        colourOverlay2.dispose();
        eraseButton.dispose();
        eraseButton2.dispose();
    }
}
