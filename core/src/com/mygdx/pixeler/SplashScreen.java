package com.mygdx.pixeler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class SplashScreen {

    public static Float centreX = Gdx.graphics.getWidth()/2f;
    public static Float centreY = Gdx.graphics.getHeight()/2f;
    public static int waitTime = 3000;

    public static void Main() {

        //Clear the screen
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draw logo
        SpriteDrawer.Draw(MainController.logo, Gdx.graphics.getHeight()/4f, centreX, centreY, Color.TEAL, 1f);

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
