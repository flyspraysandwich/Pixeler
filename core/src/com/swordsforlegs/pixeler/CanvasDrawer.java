package com.swordsforlegs.pixeler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.ApplicationAdapter;

import java.util.Random;

public class CanvasDrawer extends ApplicationAdapter {

    //Other
    static boolean canDraw = true;
    static Random rand = new Random();
    static int r = rand.nextInt(MainController.spriteLis.length);
    static Float centreX = Gdx.graphics.getWidth()/2f;
    static Float centreY = Gdx.graphics.getHeight()/2f;
    static boolean canvasChange = false;

    //Set the canvas dimensions
    static int spriteSize = 16;
    static int oldSprite = -1;
    static String canvasSprite = emptySprite(spriteSize);
    final static ShapeRenderer renderer = new ShapeRenderer();
    final static Float canvasSize = Gdx.graphics.getWidth()/2f;
    static Float pixelSize = canvasSize/spriteSize;
    final static Float canvasX = (Gdx.graphics.getWidth() - canvasSize) / 2;
    final static Float canvasY = (Gdx.graphics.getHeight() - canvasSize) / 2;
    static Pixmap canvasGrid;
    static Texture gridTex;
    static boolean gridDrawn = false;

    //Text
    static SpriteBatch batch = new SpriteBatch();
    static BitmapFont font = new BitmapFont();
    static String message = "canvas message";

    //Main
    public static void Canvas () {

        //Set the pixel size
        pixelSize = canvasSize/spriteSize;

        //Draw the grid pixmap once
        if (!gridDrawn) {

            //Grid pixmap initialize
            canvasGrid = new Pixmap(Math.round(canvasSize),Math.round(canvasSize), Pixmap.Format.RGBA8888);
            canvasGrid.setColor(1,1,1,0.3f);
            canvasGrid.fill();

            //Draw grid in pixmap
            for (int y = 0; y < spriteSize; y++) {
                for (int x = 0; x < spriteSize; x++) {
                    canvasGrid.setColor(1,1,1,0.5f);
                    canvasGrid.drawRectangle(Math.round(x*pixelSize),Math.round(y*pixelSize), Math.round(pixelSize), Math.round(pixelSize));
                }
            }
            gridTex = new Texture(canvasGrid);
            canvasGrid.dispose();
            gridDrawn = true;
        }

        //Draw the gridmap
        DrawSprite.Draw(gridTex, canvasX, canvasY, canvasSize, canvasSize,1f, 1f,Color.TEAL, false);

         //Draw the canvassprite
        SpriteDrawer.Draw(canvasSprite, canvasSize, centreX + 5, centreY - 5, Color.BLACK, 0.2f);
        SpriteDrawer.Draw(canvasSprite, canvasSize, centreX, centreY, Color.TEAL, 1f);
    }

    //Check if canvas is empty
    public static boolean spriteEmpty(String spriteString) {
        Float spriteSize = Float.valueOf(spriteString.substring(0,2));
        for (int i = 2; i < spriteSize*spriteSize+2; i++) {
            char theChar = spriteString.charAt(i);
            if (theChar != '_') {
                return false;
            }
        }
        return true;
    }

    //Flip sprite horizontally
    public static String flipSpriteH(String spriteString) {

        String newString = "";
        String line;
        String reverse;
        String size = spriteString.substring(0,2);
        Integer spriteSize = Integer.valueOf(size);
        spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

        for (int y = 0; y < spriteSize; y++) {
            reverse = "";
            //Take a line from the sprite
            line = spriteString.substring((y * spriteSize), (y * spriteSize)+spriteSize);
            //Flip the line
            for(int i = line.length() - 1; i >= 0; i--)
                reverse = reverse + line.charAt(i);
            //Add the line to the new string
            newString += reverse;
        }

        if (spriteSize >= 10)
            return(spriteSize + newString);
        else
            return("0" + spriteSize + newString);

    }

    //Flip sprite vertically
    public static String flipSpriteV(String spriteString) {
        String newString = "";
        String line;
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

        for (int y = spriteSize-1; y >= 0; y--) {
            //Take bottom line minus y from the sprite
            line = spriteString.substring((y * spriteSize), (y * spriteSize)+spriteSize);
            //Add line to new string
            newString += line;
        }

        if (spriteSize >= 10)
            return(spriteSize + newString);
        else
            return("0" + spriteSize + newString);
    }

    //Shift sprite Right
    public static String shiftSpriteRight(String spriteString) {
        String newString = "";
        String line;
        String shifted;
        char c;
        int spriteSize = Integer.valueOf(spriteString.substring(0, 2));
        spriteString = spriteString.substring(2, (spriteSize * spriteSize) + 2);

        for (int y = 0; y < spriteSize; y++) {
            //Take a line from the sprite
            line = spriteString.substring((y * spriteSize), (y * spriteSize) + spriteSize);

            //Shift the line
            c = line.charAt(line.length() - 1);
            StringBuilder sBuild = new StringBuilder(line);
            sBuild.insert(0, c);
            sBuild.deleteCharAt(line.length());
            shifted = sBuild.toString();

            //Add the line to the new string
            newString += shifted;
        }

        if (spriteSize >= 10)
            return(spriteSize + newString);
        else
            return("0" + spriteSize + newString);
    }

        //Shift sprite left
        public static String shiftSpriteLeft(String spriteString) {
            String newString = "";
            String line;
            String shifted;
            char c;
            int spriteSize = Integer.valueOf(spriteString.substring(0,2));
            spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

            for (int y = 0; y < spriteSize; y++) {
                //Take a line from the sprite
                line = spriteString.substring((y * spriteSize), (y * spriteSize)+spriteSize);

                //Shift the line
                c = line.charAt(0);
                StringBuilder sBuild = new StringBuilder(line);
                sBuild.insert(line.length(),c);
                sBuild.deleteCharAt(0);
                shifted = sBuild.toString();

                //Add the line to the new string
                newString += shifted;
            }

            if (spriteSize >= 10)
                return(spriteSize + newString);
            else
                return("0" + spriteSize + newString);
    }

    //Shift sprite Up
    public static String shiftSpriteUp(String spriteString) {
        String line;
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

        //Take first line from the sprite
        line = spriteString.substring(0, spriteSize);
        //Move it to the end
        spriteString += line;
        //Delete first line
        spriteString = spriteString.substring(spriteSize, spriteString.length());

        if (spriteSize >= 10)
            return(spriteSize + spriteString);
        else
            return("0" + spriteSize + spriteString);
    }

    //Shift sprite down
    public static String shiftSpriteDown(String spriteString) {
        String line;
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

        //Take all the lines before the last line
        line = spriteString.substring(0, spriteSize*spriteSize-spriteSize);
        //Move it to the end
        spriteString += line;
        //Delete first lines
        spriteString = spriteString.substring(spriteSize*spriteSize-spriteSize, spriteString.length());

        if (spriteSize >= 10)
            return(spriteSize + spriteString);
        else
            return("0" + spriteSize + spriteString);
    }

    //Convert a sprite int a sprite of a new size
    public static String spriteConverter(String spriteString, int newSize) {

        String newSprite;
        String line;
        String emptyChunk = "";
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        spriteString = spriteString.substring(2, (spriteSize*spriteSize)+2);

        //Set the prefix
        if (newSize >= 10)
            newSprite = newSize+"";
        else
            newSprite = "0"+newSize+"";

        //Resize the sprite, less than original
        if (newSize <= spriteSize) {
            for (int y = 0; y < newSize; y++) {
                line = spriteString.substring(y*spriteSize,(y*spriteSize)+newSize);
                newSprite += line;
            }
        }
        //larger than original
        else {
            for (int y = 0; y < newSize; y++) {
                if (y < spriteSize) {
                    line = spriteString.substring(y*spriteSize,(y*spriteSize)+spriteSize);
                    for (int i = 0; i < newSize - spriteSize; i++)
                        emptyChunk += "_";
                    line += emptyChunk;
                    newSprite += line;
                }
                else {
                    for (int i = 0; i < newSize; i++)
                        emptyChunk += "_";
                    newSprite += emptyChunk;
                }
                emptyChunk = "";
            }
        }
        return newSprite;
    }

    //Return empty sprite string
    public static String emptySprite(int size) {
        String string = "";

        for (int i = 0; i < size*size; i++)
            string += "_";

        if (size >= 10)
            return(size + string);
        else
            return("0" + size + string);
    }

    //Generate random sprite
    public static String randomSprite() {
        String newSprite = "";
        int size = rand.nextInt(DialogBox.maxSize)+1;

        //Generate the random code
        for (int i = 0; i < size * size; i++) {
            r = rand.nextInt(ColourPalette.colourCodes.length);
            newSprite += ColourPalette.colourCodes[r];
        }

        //Add the prefix
        if (size >= 10)
            newSprite = size + newSprite;
        else
            newSprite = "0" + size + newSprite;

        //Rerandomize if sprite was empty
        while (spriteEmpty(newSprite))
            newSprite = randomSprite();

        return newSprite;
    }

    //CONTROLS ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->

    //Canvas controls - touch down
    public static void ControllerDown(int screenX, int screenY, int pointer, int button) {
        addPixel(screenX, screenY);
    }

    //Canvas controls - touch up
    public static void ControllerUp(int screenX, int screenY, int pointer, int button) {
        if (canvasChange) {
            UndoRedo.makeChange();
            canvasChange = false;
        }
    }

    //Canvas controls - drag
    public static void ControllerDrag(int screenX, int screenY, int pointer) {
        addPixel(screenX, screenY);
    }

    //Add pixel
    public static void addPixel(int screenX, int screenY) {
        if (canDraw) {
            //Canvas touched, add pixel
            if (screenX > canvasX && screenX < canvasX+canvasSize && screenY > canvasY && screenY < canvasY+canvasSize) {

                canvasChange = true;

                //Add the pixel
                int cX = Math.round((screenX-canvasX+(pixelSize/2))/pixelSize);
                int cY = Math.round((screenY-canvasY-(pixelSize/2))/pixelSize);
                StringBuilder sBuild = new StringBuilder(canvasSprite);
                canvasSprite = sBuild.deleteCharAt(cX+(cY*Math.round(spriteSize))+1).toString();
                canvasSprite = sBuild.insert(cX+(cY*Math.round(spriteSize)+1), ColourPalette.currentColour).toString();

                //save sprite for recovery
                MainController.prefs.putString("Sprite",canvasSprite);
                MainController.prefs.putInteger("OldSprite",oldSprite);
                MainController.prefs.flush();
            }
        }
    }

    //Disposer
    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
        renderer.dispose();
        canvasGrid.dispose();
        gridTex.dispose();
    }
}
