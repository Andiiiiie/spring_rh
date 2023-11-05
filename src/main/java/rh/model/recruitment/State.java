package rh.model.recruitment;

import lombok.Data;

@Data
public class State {
    private String name;
    private String color;
    private String icon;

    public State() {
    }

    public State(String name, String color, String icon) {
        setName(name);
        setColor(color);
        setIcon(icon);
    }
}
