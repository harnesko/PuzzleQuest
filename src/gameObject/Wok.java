package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Wok extends GameObject {

    public Wok() {
        name = "Wok";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/wok.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
