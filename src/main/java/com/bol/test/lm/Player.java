package com.bol.test.lm;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:24
 */
public class Player {
    private final String name;
    private final String id;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && (obj instanceof Player)
                && (((Player) obj).getId() == getId());
    }
}
