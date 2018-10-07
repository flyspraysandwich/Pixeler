package com.swordsforlegs.pixeler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.ApplicationAdapter;

public class SpriteDrawer extends ApplicationAdapter{

    //Set the renderer
    final static ShapeRenderer renderer = new ShapeRenderer();
    static Texture tex;
    static Pixmap map;

    //Draw the sprite
    public static void Draw(String spriteString, Float drawSize, Float spriteX, Float spriteY, Color colour, Float alpha) {

        //Initialize pixmap
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        map = new Pixmap(spriteSize,spriteSize, Pixmap.Format.RGBA8888);
        map.setColor(0,0,0,0);
        map.fill();

        //Draw sprite on pixmap
        for (int y = 0; y < spriteSize; y++) {
            for (int x = 0; x < spriteSize; x++) {

                //Get pixel color from string
                char theChar = spriteString.charAt((y * spriteSize) + x + 2);
                int pixelColour = ColourCode(ColourPalette.colourCodes,theChar);

                //Draw pixel
                if (theChar != '_') {
                    //Set the pixel colour to original
                    if (colour == Color.TEAL)
                        map.setColor(ColourPalette.colourArray[pixelColour].r,ColourPalette.colourArray[pixelColour].g,ColourPalette.colourArray[pixelColour].b,1);
                    else //or override
                        map.setColor(colour.r,colour.g,colour.b,1);
                    map.drawPixel(x,y);
                }
            }
        }
        tex = new Texture(map);
        DrawSprite.Draw(tex, spriteX, spriteY, drawSize, drawSize,1f, alpha,Color.TEAL, true);
        tex.dispose();
        map.dispose();
    }

    //Get pixmap
    public static Pixmap getPixmap(String spriteString) {

        //Initialize pixmap
        int spriteSize = Integer.valueOf(spriteString.substring(0,2));
        map = new Pixmap(spriteSize,spriteSize, Pixmap.Format.RGBA8888);
        map.setColor(0,0,0,0);
        map.fill();

        //Draw sprite on pixmap
        for (int y = 0; y < spriteSize; y++) {
            for (int x = 0; x < spriteSize; x++) {

                //Get pixel color from string
                char theChar = spriteString.charAt((y * spriteSize) + x + 2);
                int pixelColour = ColourCode(ColourPalette.colourCodes,theChar);

                //Draw pixel
                if (theChar != '_') {
                    map.setColor(ColourPalette.colourArray[pixelColour].r,ColourPalette.colourArray[pixelColour].g,ColourPalette.colourArray[pixelColour].b,1);
                    map.drawPixel(x,y);
                }
            }
        }
        return(map);
    }

    //Find array index
    public static int ColourCode (char[] colours, char target)
    {
        for (int i = 0; i < colours.length; i++)
            if (colours[i] == target)
                return i;
        return -1;
    }

    @Override
    public void dispose () {
        renderer.dispose();
        tex.dispose();
        map.dispose();
    }
}
