package MainMenu;

import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class KeyHandler implements KeyListener {
    private myFrame frame;

    public KeyHandler(myFrame frame){
        this.frame=frame;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //=================================================================
                                    //MainMenu
        //=================================================================


        if (frame.panel.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                frame.panel.commandNumber--;
                frame.panel.repaint();
                if (frame.panel.commandNumber < 0) {
                    frame.panel.commandNumber = 3;
                }
            }

            if (code == KeyEvent.VK_S) {
                frame.panel.commandNumber++;
                frame.panel.repaint();
                if (frame.panel.commandNumber > 3) {
                    frame.panel.commandNumber = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (frame.panel.commandNumber == 0) {
                    //New Game
                    enterAudio();
                }
                if (frame.panel.commandNumber == 1) {
                    frame.panel.titleScreenState = 1;
                    frame.panel.repaint();
                    frame.panel.commandNumber = 0;
                    enterAudio();
                }
                if (frame.panel.commandNumber == 2) {
                    //Settings menu
                    frame.panel.titleScreenState = 2;
                    frame.panel.repaint();
                    frame.panel.commandNumber = 0;
                    enterAudio();
                }
                if (frame.panel.commandNumber == 3) {
                    //Quit
                    System.exit(0);
                }
            }
        }
        //=================================================================
                                //Saves menu
        //=================================================================
        if (frame.panel.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                frame.panel.commandNumber--;
                frame.panel.repaint();
                if (frame.panel.commandNumber < 0) {
                    frame.panel.commandNumber = 4;
                }
            }

            if (code == KeyEvent.VK_S) {
                frame.panel.commandNumber++;
                frame.panel.repaint();
                if (frame.panel.commandNumber > 4) {
                    frame.panel.commandNumber = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (frame.panel.commandNumber == 0) {
                    if(!frame.panel.save1Active){
                        ErrorAudio();
                    }else
                        enterAudio();
                    //enter save 1
                }
                if (frame.panel.commandNumber == 1) {
                    if(!frame.panel.save2Active){
                        ErrorAudio();
                    }else
                        enterAudio();
                    //enter save 2
                }
                if (frame.panel.commandNumber == 2) {
                    if(!frame.panel.save3Active){
                        ErrorAudio();
                    }else
                        enterAudio();
                    //enter save 3
                }
                if (frame.panel.commandNumber == 3) {
                    if(!frame.panel.save4Active){
                        ErrorAudio();
                    }else
                        enterAudio();
                    //enter save 4
                }
                if (frame.panel.commandNumber == 4) {
                    //Return
                    frame.panel.titleScreenState = 0;
                    frame.panel.commandNumber = 0;
                    frame.panel.repaint();
                }
            }
        }
        //=================================================================
                                //Settings menu
        //=================================================================
        if (frame.panel.titleScreenState == 2) {
            if (code == KeyEvent.VK_W) {
                frame.panel.commandNumber--;
                frame.panel.repaint();
                if (frame.panel.commandNumber < 0) {
                    frame.panel.commandNumber = 4;
                }
            }

            if (code == KeyEvent.VK_S) {
                frame.panel.commandNumber++;
                frame.panel.repaint();
                if (frame.panel.commandNumber > 4) {
                    frame.panel.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_A) {
                //Lower Volume
                if (frame.panel.commandNumber == 0) {
                    frame.panel.musicVolume -= 0.1;
                    if(frame.panel.musicVolume < 0){
                        frame.panel.musicVolume = 0;
                    }
                }
                if (frame.panel.commandNumber == 1) {
                    frame.panel.effectVolume -= 0.1;
                    if(frame.panel.effectVolume < 0){
                        frame.panel.effectVolume = 0;
                    }
                }
                frame.panel.repaint();
            }

            if (code == KeyEvent.VK_D) {
                //Increase volume
                if (frame.panel.commandNumber == 0) {
                    frame.panel.musicVolume += 0.1;
                    if(frame.panel.musicVolume > 1){
                        frame.panel.musicVolume = 1;
                    }
                }
                if (frame.panel.commandNumber == 1) {
                    frame.panel.effectVolume += 0.1;
                    if(frame.panel.effectVolume > 1){
                        frame.panel.effectVolume = 1;
                    }
                }
                frame.panel.repaint();
            }

            if (code == KeyEvent.VK_ENTER) {
                if (frame.panel.commandNumber == 2) {
                    //FullScreen
                    if(frame.panel.fullscreen) {
                        frame.panel.fullscreen = false;
                        enterAudio();
                    }else{
                        frame.panel.fullscreen = true;
                    }
                    enterAudio();
                    frame.panel.repaint();
                }
                if (frame.panel.commandNumber == 3) {
                    frame.panel.titleScreenState = 0;
                    frame.panel.commandNumber = 0;
                    frame.panel.repaint();
                        enterAudio();
                    //enter save 4
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
    }

    /**
     * Method to play the sound effect when selecting any option.
     * Volume is controlled by the @effectVolume variable from the MainMenu class.
     * The @effectVolume is changed in the keyHandler class.
     */
    public void enterAudio(){
        try {
            Clip clip;
            File file = new File("Audio/Select_Audio.wav");
            AudioInputStream audioStream = null;
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = 1;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to play the sound effect when save is locked in the SavesMenu.
     * Volume is controlled by the @effectVolume variable from the MainMenu class.
     * The @effectVolume is changed in the keyHandler class.
     */
    public void ErrorAudio(){
        try {
            Clip clip;
            File file = new File("Audio/Error_Audio.wav");
            AudioInputStream audioStream = null;
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = 1;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
