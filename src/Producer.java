/**
 * Created by Elliot Åberg Fält
 * Date: 2021-02-22
 * Time: 10:40
 * Project: Skobutik
 * Copyright: MIT
 */
public class Producer {
    private String name;

    public Producer(String name) {
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
