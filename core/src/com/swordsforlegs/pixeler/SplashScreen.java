package com.swordsforlegs.pixeler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class SplashScreen {

    public static Float centreX = Gdx.graphics.getWidth()/2f;
    public static Float centreY = Gdx.graphics.getHeight()/2f;
    public static int waitTime = 3000;

    static SpriteBatch batch = new SpriteBatch();
    static BitmapFont font = MainController.cyrillicFont;

    public static void Main() {

        //Clear the screen
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draw logo
        SpriteDrawer.Draw(MainController.logo, Gdx.graphics.getHeight()/4f, centreX + 3, centreY + (Gdx.graphics.getHeight()/14f) - 3, Color.BLACK, 0.2f);
        SpriteDrawer.Draw(MainController.logo, Gdx.graphics.getHeight()/4f, centreX, centreY + (Gdx.graphics.getHeight()/14f), Color.TEAL, 1f);

        //Draw text
        batch.begin();
        font.setColor(0,0,0,0.2f);
        font.draw(batch, "Pixeler", centreX + 3,centreY - (Gdx.graphics.getHeight()/13f) - 3,0, Align.center,false);
        font.setColor(0,0,0,1);
        font.draw(batch, "Pixeler", centreX,centreY - (Gdx.graphics.getHeight()/13f),0,Align.center,false);
        batch.end();

        //Wait a bit, then go to main menu
        if (waitTime > 0)
            waitTime -= MainController.dt*15;
        else {
            SaveData.LoadData();
            //load recovered sprite
            if (MainController.prefs.contains("Sprite"))
                DrawScreen.resetDrawScreen(MainController.prefs.getInteger("OldSprite"), MainController.prefs.getString("Sprite"), Integer.valueOf(MainController.prefs.getString("Sprite").substring(0,2)));
            else
                MainController.screenChange = 2;
        }
    }
}
