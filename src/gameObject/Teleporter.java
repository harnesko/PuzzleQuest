package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Teleporter extends GameObject {
    public Teleporter() {
        name = "Teleporter";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/rune.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
