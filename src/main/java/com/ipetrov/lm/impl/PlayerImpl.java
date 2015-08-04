package com.ipetrov.lm.impl;

import com.ipetrov.lm.Player;

/**
 * User: Igor
 * Date: 15.08.14
 * Time: 23:24
 */
public class PlayerImpl implements Player {
    private final String name;
    private final String id;

    public PlayerImpl(String id, String name) {
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
                && (obj instanceof PlayerImpl)
                && ((Player) obj).getId().equals(getId());
    }
}
