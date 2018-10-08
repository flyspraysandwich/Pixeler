package com.swordsforlegs.pixeler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainController extends ApplicationAdapter implements InputProcessor{

	//Initialize stuff
	static ShapeRenderer renderer;
	static SpriteBatch batch;
	static Float dt = 0f;
    static Color[] mainColourList = new Color[12];
    static String[] spriteLis = new String[25];
	static Integer whichScreen = 1; // 0 - nothing, 1 - splash, 2 - menu, 3 - draw
	static int fadeOutState = 0;
	static Float fadeOutAlpha = 0f;
	static int mainColour = 1;
	static int screenChange = 0;
	static String logo = "10_______x________xpx______xGxpx____xyxGx____xyyox____xyyox____xyyox___xxWxox___x_xWWx_____xxxxxxxxxx_";
    static FileHandle file;
	static BitmapFont cyrillicFont;
    static BitmapFont cyrillicFont2;
	static Preferences prefs;
	static BitmapFont font;
	static String message = "";

	@Override
	public void create () {

        // font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Folks-Normal.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(48 * (Gdx.graphics.getWidth()/1280f));
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        cyrillicFont = generator.generateFont(parameter);

        parameter.size = Math.round(24 * (Gdx.graphics.getWidth()/1280f));
        cyrillicFont2 = generator.generateFont(parameter);

        generator.dispose();

        //set the main colours
		mainColourList[0] = ColourPalette.fCol(160,160,170); //grey
		mainColourList[1] = ColourPalette.fCol(160,160,170); //grey
		mainColourList[2] = ColourPalette.fCol(120,120,130); //grey 2
		mainColourList[3] = ColourPalette.fCol(60,179,113); //green
		mainColourList[4] = ColourPalette.fCol(204,129,255); //purpl
        mainColourList[5] = ColourPalette.fCol(255,129,129); //red
		mainColourList[6] = ColourPalette.fCol(244,164,96); //orange
        mainColourList[7] = ColourPalette.fCol(0,188,255); //lt blue
		mainColourList[8] = ColourPalette.fCol(116,146,233); //royal blue
        mainColourList[9] = ColourPalette.fCol(255,153,204); //pink
        mainColourList[10] = ColourPalette.fCol(0,210,210); //aqua
		mainColourList[11] = ColourPalette.fCol(210,180,140); //tan

		//Set the file
		file = Gdx.files.local("spritedata.txt");

		//Get the preference colour
		prefs = Gdx.app.getPreferences("SpriteSave");
		if (prefs.contains("Colour"))
			mainColour = prefs.getInteger("Colour");

		//Initialize stuff
		renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {

		//Set delta time
		dt = Gdx.graphics.getDeltaTime() * 60;

		//Clear the screen
		Gdx.gl.glClearColor(mainColourList[mainColour].r,mainColourList[mainColour].g,mainColourList[mainColour].b,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw the splash screen
		if (whichScreen == 1)
			SplashScreen.Main();
		//Draw the main menu
		else if (whichScreen == 2)
			MainMenu.Main();
		//Draw the draw screen
		else if (whichScreen == 3)
			DrawScreen.Main();

		//if screen changed
		if (screenChange != 0) {
			GoToScreen(screenChange);
		}

		batch.begin();
		font.draw(batch, message,Gdx.graphics.getWidth()/2, 40);
		batch.end();
	}

	//Transition to another screen
	public static void GoToScreen(int screen) {

		//start renderer
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		renderer.begin(ShapeRenderer.ShapeType.Filled);

		//Draw fade
		renderer.setColor(1,1,1, fadeOutAlpha);
		renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//End renderer
		renderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		//transition values
		if (fadeOutState == 0) {
			if (fadeOutAlpha < 1)
				fadeOutAlpha += dt/20;
			else {
				whichScreen = screen;
				fadeOutState = 1;
			}
		}
		else {
			if (fadeOutAlpha > 0) {
				fadeOutAlpha -= dt/20;
			}
			else
				fadeOutState = screenChange = 0;
		}
		clamp(fadeOutAlpha,0,1);
	}

	//Clamp float
	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

	//Compress string
	public static String compressString(String spriteString){
		String newString = "";
		int size = Integer.valueOf(spriteString.substring(0,2));
		spriteString = spriteString.substring(2, (size*size)+2);
		int charCount = 0;
		char c;
		char cLast = '+';

		//Compress string
		for (int i = 0; i < size*size; i++) {
			c = spriteString.charAt(i);

			if (cLast == '+')
				cLast = c;

			//Add to string
			if (c != cLast) {
				if (charCount > 1)
					newString += cLast + Integer.toString(charCount);
				else
					newString += cLast;
				charCount = 0;
			}

			charCount += 1;
			cLast = c;

			//Last character
			if (i == size*size-1) {
				if (charCount > 1)
					newString += cLast + Integer.toString(charCount);
				else
					newString += cLast;
			}
		}

		//Return compressed string
		if (size >= 10)
			return(size + newString);
		else
			return("0" + size + newString);
	}

	//Decompress string
	public static String decompressString(String compressedString) {
		String newString = "";
		int size = Integer.valueOf(compressedString.substring(0,2));
		compressedString = compressedString.substring(2, compressedString.length());
		String charString = "";
		int charCount = 0;
		char c = '+';
		char cLast = '+';

		//Decompress string
		for (int i = 0; i < compressedString.length(); i++) {
			//Check the character
			if (!Character.isDigit(compressedString.charAt(i))) {
				//Get the colour code
				if (charCount == 0 && c != cLast && cLast != '+') {
					c = compressedString.charAt(i);
					cLast = c;
				}
				//count collected, add chars to string
				else {
					if (charCount != 0 && c != '+') {
						for (int j = 0; j < charCount; j++)
							newString = newString + Character.toString(c);
					}
					else if (c != '+')
						newString = newString + Character.toString(c);
					c = compressedString.charAt(i);
					charString = "";
					charCount = 0;
				}
			}
			//Add to the count
			else {
				charString += compressedString.charAt(i);
				charCount = Integer.parseInt(String.valueOf(charString));
			}

			//Last character, add to string
			if (i == compressedString.length()-1) {
				if (charCount != 0) {
					for (int j = 0; j < charCount; j++)
						newString = newString + Character.toString(c);
				}
				else
					newString = newString + Character.toString(c);
			}
		}

		//Return decompressed string
		if (size >= 10)
			return(size + newString);
		else
			return("0" + size + newString);
	}

	//Validate code
	public static boolean validateCompressed(String code) {
			int spriteSize;
			boolean charGood = false;

			//Check size
			if (code.length() < 2)
				return false;

			//Check if first 2 characters are an integer
			String firstChars = code.substring(0, 2);
			code = code.substring(2, code.length());
			try {
				spriteSize = Integer.valueOf(firstChars);
			} catch (Exception e) {
				return false;
			}

			//Check if size is less than the max size
			if (spriteSize > DialogBox.maxSize)
				return false;

			//Validate the code
			for (int i = 0; i < code.length(); i++) {
				char c = code.charAt(i);

				//If not a digit
				if  (!Character.isDigit(code.charAt(i))) {
					//check if it's a valid colour code
					for (int j = 0; j < ColourPalette.colourCodes.length; j++) {
						if (c == ColourPalette.colourCodes[j]) {
							charGood = true;
							break;
						} else
							charGood = false;
					}
					if (!charGood)
						return false;
				}
			}
		return true;
	}

	//Validate decompressed code
	public static boolean validateDecompressed(String code) {

		int spriteSize = Integer.valueOf(code.substring(0,2));
		code = code.substring(2, (spriteSize*spriteSize)+2);

		if (code.length() != spriteSize*spriteSize)
			return false;
		else
			return true;
	}

	//Touch events --------------------------------------------------------------->

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		//If dialog not shown
		if (!DialogBox.dialogShown) {

            if (whichScreen == 2) {
                //MainMenu controls
                MainMenu.ControllerDown(screenX, screenY, pointer, button);
            }
            else if (whichScreen == 3) {
				//Canvas controls
				CanvasDrawer.ControllerDown(screenX, screenY, pointer, button);

                //ColourPalette controls
                ColourPalette.ControllerDown(screenX, screenY, pointer, button);

				//RightButtons controls
				RightButtons.ControllerDown(screenX, screenY, pointer, button);

				//UndoRedo controls
				UndoRedo.ControllerDown(screenX, screenY, pointer, button);

                //DrawScreen controls
                DrawScreen.ControllerDown(screenX, screenY, pointer, button);
            }
		}
		//Dialog controls
		else {
			//DialogBox controls
			DialogBox.ControllerDown(screenX, screenY, pointer, button);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		//If dialog not shown
		if (!DialogBox.dialogShown && screenChange == 0) {

            if (whichScreen == 2) {
                //MainMenu controls
                MainMenu.ControllerUp(screenX, screenY, pointer, button);
            }
            else if (whichScreen == 3) {
				//Canvas controls
				CanvasDrawer.ControllerUp(screenX, screenY, pointer, button);

                //DrawScreen controls
                DrawScreen.ControllerUp(screenX, screenY, pointer, button);

                //RightButtons controls
                RightButtons.ControllerUp(screenX, screenY, pointer, button);

                //ColourPalette controls
                ColourPalette.ControllerUp(screenX, screenY, pointer, button);

				//UndoRedo controls
				UndoRedo.ControllerUp(screenX, screenY, pointer, button);
            }
        }
		//Dialog controls
		else {
			//DialogBox controls
			DialogBox.ControllerUp(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		//If dialog not shown
		if (!DialogBox.dialogShown && screenChange == 0) {

            if (whichScreen == 2) {
                //MainMenu controls
                MainMenu.ControllerDrag(screenX, screenY, pointer);
            }
            else if (whichScreen == 3) {
                //DrawScreen controls
                DrawScreen.ControllerDrag(screenX, screenY, pointer);

                //Canvas controls
                CanvasDrawer.ControllerDrag(screenX, screenY, pointer);

                //ColourPalette controls
                ColourPalette.ControllerDrag(screenX, screenY, pointer);
            }
		}
		//Dialog controls
		else {
			//DialogBox controls
			DialogBox.ControllerDrag(screenX, screenY, pointer);
		}
        return true;
	}
	//End of touch events --------------------------------------------------------------->

	//Disposer
	@Override
	public void dispose () {
		renderer.dispose();
		batch.dispose();
		font.dispose();
	}

	//Don't need these --------------------------------------------------------------->

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	//Didn't need those --------------------------------------------------------------->
}
