package com.sziov.smart.park.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {
    icon_ali_pay('\ue652'),
    icon_scan('\ue62f');

    private char character;
    EcIcons(char character){
        this.character = character;
    }
    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
