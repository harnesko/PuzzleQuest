package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

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