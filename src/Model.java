/**
 * Created by Elliot Åberg Fält
 * Date: 2021-02-22
 * Time: 11:15
 * Project: Skobutik
 * Copyright: MIT
 */
public class Model {
    private String name;

    public Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
