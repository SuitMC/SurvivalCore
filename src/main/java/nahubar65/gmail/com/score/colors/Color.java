package nahubar65.gmail.com.score.colors;

import java.util.Map;

public class Color {

    private float r;

    private float g;

    private float b;

    private float opacity;

    private Map<String, Color> colors;

    public Color(float r, float g, float b, float opacity){
        this.r = r;
        this.g = g;
        this.b = b;
        this.opacity = opacity;
    }

    public Color(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }


}