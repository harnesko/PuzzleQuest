package gameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Book extends GameObject {

    public Book() {
        name = "Book";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/bok.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

