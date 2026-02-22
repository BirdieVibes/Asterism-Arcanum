package com.birdie.asterismarcanum.entity.mobs.astromancer;

import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AstromancerRenderer extends AbstractSpellCastingMobRenderer {
    public AstromancerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AstromancerModel());
    }
}
