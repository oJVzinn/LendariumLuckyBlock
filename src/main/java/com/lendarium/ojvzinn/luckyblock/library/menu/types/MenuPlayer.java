package com.lendarium.ojvzinn.luckyblock.library.menu.types;

import com.lendarium.ojvzinn.luckyblock.library.menu.MenuAbstract;

public abstract class MenuPlayer extends MenuAbstract {

    public MenuPlayer(Integer rows, String title) {
        super(rows, title);
    }

    public MenuPlayer(String title) {
        super(title);
    }
}
