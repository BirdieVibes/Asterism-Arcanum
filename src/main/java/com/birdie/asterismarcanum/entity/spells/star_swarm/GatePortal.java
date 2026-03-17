package com.birdie.asterismarcanum.entity.spells.star_swarm;

import com.birdie.asterismarcanum.entity.spells.starfire.StarfireProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class GatePortal extends AbstractMagicProjectile implements GeoEntity {
    public float speed = 0;

    public GatePortal(Level level, LivingEntity shooter) {
        this(ASAREntityRegistry.GATE_PORTAL.get(), level);
        this.setOwner(shooter);
        this.setNoGravity(true);
    }

    public GatePortal(EntityType<GatePortal> gatePortalEntityType, Level level) {
        super(gatePortalEntityType,level);
    }

    public void shootStar(){
        Vec3 origin;
        origin = this.position();
        StarfireProjectile starfireProjectile = new StarfireProjectile(this.level(), (LivingEntity) this.getOwner());

        starfireProjectile.setPos(origin.add(0,starfireProjectile.getBbHeight()/2,0));
        starfireProjectile.setDamage(this.getDamage());
        starfireProjectile.shoot(this.getLookAngle());
        this.level().playSound((Player)null, origin.x, origin.y, origin.z, SoundRegistry.HOLY_CAST, SoundSource.PLAYERS, 0.1F, 1.0F);
        this.level().addFreshEntity(starfireProjectile);

        //particles
        if(this.level() instanceof ServerLevel level) {
            level.sendParticles(ASARParticleRegistry.SIGNS_PARTICLE.get(), this.getX(), this.getY(), this.getZ(), 5, 0, 0, 0, 0.1);
        }
    }

    @Override
    public void tick() {

        Vec3 lookAngle;
        lookAngle = this.getOwner().getLookAngle();
        float xRot = ((float)(Mth.atan2(lookAngle.horizontalDistance(), lookAngle.y) * (180F / Math.PI)) - 90.0F);
        float yRot = ((float)(Mth.atan2(lookAngle.z, lookAngle.x) * (180F / Math.PI)) - 90);
        this.setXRot(Mth.wrapDegrees(xRot));
        this.setYRot(Mth.wrapDegrees(yRot));

        move(MoverType.SELF, this.getOwner().getDeltaMovement());

        if(tickCount % 10 == 0){
            shootStar();
        }
        if(tickCount > 20){
            discard();
        }
        super.tick();
    }

    @Override
    public void trailParticles() {

    }

    @Override
    public void impactParticles(double v, double v1, double v2) {

    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.empty();
    }

    private final AnimationController<GatePortal> animationController = new AnimationController<>(this, "controller", 0, this::predicate);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(animationController);
    }

    private PlayState predicate(AnimationState<GatePortal> event){
        event.getController().setAnimation(DefaultAnimations.IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
