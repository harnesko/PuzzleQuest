package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
/**
 * Game Object Class
 * This is an item for a Quest
 * @author Kristoffer
 */
public class End extends GameObject {

    public End() {
        name = "End";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/pngwing.com.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
