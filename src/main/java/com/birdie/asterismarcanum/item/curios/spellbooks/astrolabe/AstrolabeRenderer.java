package com.birdie.asterismarcanum.item.curios.spellbooks.astrolabe;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AstrolabeRenderer extends GeoItemRenderer<AstrolabeSpellBook> {
    public AstrolabeRenderer() {
        super(new AstrolabeModel());
    }
}
