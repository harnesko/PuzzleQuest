package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Fixat i denna klassen
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, escPressed, ePressed, enterPressed, fPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState) {
            mainMenuKeys(code);
        }
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_ESCAPE){
                escPressed = !escPressed;
            }
            gamePlayKeys(code);
        }
        if(gp.gameState == gp.optionsState) {
            if (code == KeyEvent.VK_ESCAPE){
                escPressed = !escPressed;
            }
            optionKey(code);
        }
        if(gp.gameState == gp.noneState){
            optionsBackButton(code);
        }
        if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_E || code == KeyEvent.VK_ENTER  ){      //E or Enter key progresses dialogue
                //gp.ui.displayNextDialogue("Input proper string here");
                gp.progressDialogue();
                gp.ui.displayNextDialogue(gp.npcList[gp.currentSpeaker].getCurrDialogue());
            } else if (code == KeyEvent.VK_ESCAPE){                     //Esc exits dialogue state
                gp.gameState = gp.playState;
            }
        }
    }

    /**
     * Method to organize the keys for the mainMenu.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    public void mainMenuKeys(int code){
        if (gp.ui.titleScreenState == 0) {
            startKeys(code);
        }
        if (gp.ui.titleScreenState == 1) {
            savesKeys(code);
        }
        if (gp.ui.titleScreenState == 2) {
            settingsKeys(code);
        }

    }

    /**
     * The keys used in the MainMenu start screen.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    public void startKeys(int code) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNumber--;
                gp.playSoundEffect(3);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 3;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                gp.playSoundEffect(3);
                if (gp.ui.commandNumber > 3) {
                    gp.ui.commandNumber = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 0) {
                    //Play
                    gp.gameState = gp.playState;
                    gp.playSoundEffect(2);
                }
                if (gp.ui.commandNumber == 1) {
                    //Saves
                    gp.ui.titleScreenState = 1;
                    gp.ui.commandNumber = 0;
                    gp.playSoundEffect(2);
                }
                if (gp.ui.commandNumber == 2) {
                    //Settings menu
                    gp.ui.titleScreenState = 2;
                    gp.ui.commandNumber = 0;
                    gp.playSoundEffect(2);
                }
                if (gp.ui.commandNumber == 3) {
                    //Quit
                    System.exit(0);
                }
            }
    }

    /**
     * The keys used in the saves' menu.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    public void savesKeys(int code) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNumber--;
                gp.playSoundEffect(3);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 4;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                gp.playSoundEffect(3);
                if (gp.ui.commandNumber > 4) {
                    gp.ui.commandNumber = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 0) {
                    if (!gp.ui.save1Active) {
                        gp.playSoundEffect(1);
                    } else
                        gp.playSoundEffect(2);
                    //enter save 1
                }
                if (gp.ui.commandNumber == 1) {
                    if (!gp.ui.save2Active) {
                        gp.playSoundEffect(1);
                    } else
                        gp.playSoundEffect(2);
                    //enter save 2
                }
                if (gp.ui.commandNumber == 2) {
                    if (!gp.ui.save3Active) {
                        gp.playSoundEffect(1);
                    } else
                        gp.playSoundEffect(2);
                    //enter save 3
                }
                if (gp.ui.commandNumber == 3) {
                    if (!gp.ui.save4Active) {
                        gp.playSoundEffect(1);
                    } else
                        gp.playSoundEffect(2);
                    //enter save 4
                }
                if (gp.ui.commandNumber == 4) {
                    //Return
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNumber = 0;
                }
            }
    }

    /**
     * The keys used in the settings menu.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    public void settingsKeys(int code) {
        if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) && !enterPressed) {
            gp.ui.commandNumber--;
            gp.playSoundEffect(3);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 3;
            }
        }

        if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) && !enterPressed) {
            gp.ui.commandNumber++;
            gp.playSoundEffect(3);
            if (gp.ui.commandNumber > 3) {
                gp.ui.commandNumber = 0;
            }
        }

        if ((code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) && !enterPressed) {
            //Lower Music
            if (gp.ui.commandNumber == 0 && gp.music.volumeScale > 0) {
                gp.music.volumeScale --;
                gp.music.volumeChanger();
                gp.playSoundEffect(2);
            }
            //Lower Volume
            if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale > 0) {
                gp.soundEffects.volumeScale --;
                gp.playSoundEffect(2);
            }
        }

        if ((code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) && !enterPressed) {
            //Increase Music
            if (gp.ui.commandNumber == 0 && gp.music.volumeScale < 10) {
                gp.music.volumeScale++;
                gp.music.volumeChanger();
                gp.playSoundEffect(2);
            }//Increase sound effect
            if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale < 10) {
                gp.soundEffects.volumeScale++;
                gp.playSoundEffect(2);
            }
        }

        if ((code == KeyEvent.VK_ENTER) && !enterPressed) {
            if (gp.ui.commandNumber == 2) {
                if(gp.ui.fullscreen) {
                    gp.ui.fullscreen = false;
                    gp.playSoundEffect(2);
                }else{
                    gp.ui.fullscreen = true;
                }
                enterPressed = true;
                gp.ui.commandNumber = 0;
                gp.playSoundEffect(2);

            }
            if (gp.ui.commandNumber == 3) {
                gp.ui.titleScreenState = 0;
                gp.ui.commandNumber = 0;
                gp.playSoundEffect(2);
            }
        }
        if (code == KeyEvent.VK_BACK_SPACE){ //Denna Fungerar inte med ESC!!!!!!!
            enterPressed = false;
        }
    }

    /**
     * The keys used in the options menu.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    private void optionKey(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNumber--;
            gp.playSoundEffect(3);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 3;
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNumber++;
            gp.playSoundEffect(3);
            if (gp.ui.commandNumber > 3) {
                gp.ui.commandNumber = 0;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            //Lower Music
            if (gp.ui.commandNumber == 0 && gp.music.volumeScale > 0) {
                gp.music.volumeScale --;
                gp.music.volumeChanger();
                gp.playSoundEffect(2);
            }
            //Lower Volume
            if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale > 0) {
                gp.soundEffects.volumeScale --;
                gp.playSoundEffect(2);
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            //Increase Music
            if (gp.ui.commandNumber == 0 && gp.music.volumeScale < 10) {
                gp.music.volumeScale++;
                gp.music.volumeChanger();
                gp.playSoundEffect(2);
            }//Increase sound effect
            if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale < 10) {
                gp.soundEffects.volumeScale++;
                gp.playSoundEffect(2);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 2) {

                // TODO: fixa så man frågas innan programmet stängs

                if(gp.ui.fullscreen) {
                    gp.ui.fullscreen = false;
                    gp.playSoundEffect(2);
                }else{
                    gp.ui.fullscreen = true;
                }
                gp.ui.settingsState = 1;
                gp.gameState = gp.noneState;
                gp.ui.commandNumber = 0;
                gp.playSoundEffect(2);;

            }
            if (gp.ui.commandNumber == 3) {
                gp.gameState= gp.titleState;
                gp.ui.commandNumber = 0;
                gp.playSoundEffect(2);
            }
        }

        if(gp.ui.settingsState == 1){
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.commandNumber == 0) {
                    if (gp.ui.settingsState == 1) {
                        gp.gameState = gp.optionsState;
                        gp.ui.settingsState = 0;
                        gp.playSoundEffect(2);
                    }
                }
            }
        }
    }

    /**
     * This method is used to go back from the fullscreen notifications screen.
     * @param code used to determine what key we use.
     * @author Kristoffer
     */
    public void optionsBackButton(int code) {
        if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_BACK_SPACE) {
            if (gp.ui.commandNumber == 0) {
                if (gp.ui.settingsState == 1) {
                    gp.gameState = gp.optionsState;
                    gp.ui.settingsState = 0;
                    gp.playSoundEffect(2);
                }
            }
        }

    }

    public void gamePlayKeys(int code){
        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }
        if (code == KeyEvent.VK_F2){
            fPressed = !fPressed;
        }
        if (code == KeyEvent.VK_P){
            escPressed = true;
            gp.gameState =gp.optionsState;
        }
        if (code == KeyEvent.VK_E){
            ePressed = true;
            //gp.npcList[0].speak();
        }
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState =gp.optionsState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
    }
}
