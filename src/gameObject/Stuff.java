package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Stuff extends GameObject {

    public Stuff() {
        name = "Stuff";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Stuff.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
