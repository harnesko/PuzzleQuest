package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
/**
 * Game Object Class
 * This is an item for a Quest
 * @author Kristoffer
 */
public class Ingredient extends GameObject {

    public Ingredient() {
        name = "Ingredient";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Stuff.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
