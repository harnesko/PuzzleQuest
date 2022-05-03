package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Fixat i denna klassen
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, escPressed;;
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
            gamePlayKeys(code);
        }
        if(gp.gameState == gp.optionsState) {
            optionKey(code);
        }

    }

    /**
     *
     * @param code
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
     *
     * @param code
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
                gp.music.ceckVolume();
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
                gp.music.ceckVolume();
                gp.playSoundEffect(2);
            }//Increase sound effect
            if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale < 10) {
                gp.soundEffects.volumeScale++;
                gp.playSoundEffect(2);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 2) {
                //FullScreen
                if(gp.ui.fullscreen) {
                    gp.ui.fullscreen = false;
                    gp.playSoundEffect(2);
                }else{
                    gp.ui.fullscreen = true;
                }
                gp.playSoundEffect(2);;
            }
            if (gp.ui.commandNumber == 3) {
                gp.gameState= gp.titleState;
                gp.ui.commandNumber = 0;
                gp.playSoundEffect(2);

            }
        }
        if (code == KeyEvent.VK_ESCAPE){
            escPressed = false;
            gp.gameState = gp.playState;
        }
    }

    /**
     *
     * @param code
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
                    gp.gameState = gp.playState;
                    gp.playSoundEffect(2);
                    ;
                }
                if (gp.ui.commandNumber == 1) {
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
     *
     * @param code
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
     *
     * @param code
     * @author Kristoffer
     */
    public void settingsKeys(int code) {
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
                    gp.music.ceckVolume();
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
                    gp.music.ceckVolume();
                    gp.playSoundEffect(2);
                }//Increase sound effect
                if (gp.ui.commandNumber == 1 && gp.soundEffects.volumeScale < 10) {
                    gp.soundEffects.volumeScale++;
                    gp.playSoundEffect(2);
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 2) {
                    //FullScreen
                    if(gp.ui.fullscreen) {
                        gp.ui.fullscreen = false;
                        gp.playSoundEffect(2);
                    }else{
                        gp.ui.fullscreen = true;
                    }
                    gp.playSoundEffect(2);;
                }
                if (gp.ui.commandNumber == 3) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNumber = 0;
                    gp.playSoundEffect(2);
                    //enter save 4
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
        if (code == KeyEvent.VK_P){
            escPressed = true;
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
