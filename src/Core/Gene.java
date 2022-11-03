package Core;

import Core.Cube;

public class Gene {
    protected String name = "";
    protected int index = 0;

    public Gene(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public void doAction(Cube actor) {}

    public String toString() {
        return this.name;
    }
}
