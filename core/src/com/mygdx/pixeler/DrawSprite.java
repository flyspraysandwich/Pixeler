package com.mygdx.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawSprite extends ApplicationAdapter{

    static SpriteBatch batch = new SpriteBatch();
    static Sprite sprite;

    //Draw
    public static void Draw(Texture texture, Float x, Float y, Float sizeW, Float sizeH, Float scale, Float alpha, Color color, boolean centreOrigin) {

        //Set properties
        sprite = new Sprite(texture);
        sprite.setSize(sizeW, sizeH);
        sprite.setScale(scale);
        if (color != Color.TEAL)
            sprite.setColor(color);
        sprite.setOrigin(sizeW/2,sizeH/2);
        sprite.setAlpha(alpha) ;
        if (centreOrigin)
            sprite.setPosition(x - sizeW/2,y - sizeH/2);
        else
            sprite.setPosition(x,y);

        //Draw sprite
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
