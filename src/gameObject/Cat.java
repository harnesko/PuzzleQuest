package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
/**
 * Game Object Class
 * This is an item for a Quest
 * @author Kristoffer
 */
public class Cat extends GameObject {

    public Cat() {
        name = "cat";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/cat.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}