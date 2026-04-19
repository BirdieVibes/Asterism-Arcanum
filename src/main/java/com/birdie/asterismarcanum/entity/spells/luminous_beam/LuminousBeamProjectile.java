package com.birdie.asterismarcanum.entity.spells.luminous_beam;

import com.birdie.asterismarcanum.entity.spells.AbstractBeamProjectile;
import com.birdie.asterismarcanum.registries.ASAREntityRegistry;
import com.birdie.asterismarcanum.registries.ASARParticleRegistry;
import com.birdie.asterismarcanum.registries.ASARSpellRegistry;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Random;

import java.util.ArrayList;
import java.util.List;

// THIS IS HEAVILY BASED ON ELECTROCUTE
// Creates a single 'lightning' particle, kind of like a long cone spell
public class LuminousBeamProjectile extends AbstractBeamProjectile {
    private List<Vec3> beamVectors;

    public LuminousBeamProjectile(EntityType<? extends AbstractBeamProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LuminousBeamProjectile(Level level, LivingEntity entity) {
        super(ASAREntityRegistry.LUMINOUS_BEAM_PROJECTILE.get(), level, entity);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return super.shouldRenderAtSqrDistance(pDistance);
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return super.shouldRender(pX, pY, pZ);
    }

    public double getBeamBlockCollision(Entity entity) {
        if (this.getOwner() != null) {
            Entity owner = this.getOwner();
            var start = owner.position();
            var blockCollision = RaycastBuilder.begin(level(), owner)
                    .range(18)
                    .checkForBlocks(true)
                    .bbInflation(.15f)
                    .build();
            if (blockCollision instanceof BlockHitResult) {
                double distance = start.distanceTo(blockCollision.getLocation());
                return distance;
            }
        }
        return 17;
    }

    public void generateLightningBeams() {

        Vec3 coreStart = new Vec3(0, 0, 0);
        int coreLength = (int) getBeamBlockCollision(this) + 1;

        beamVectors = new ArrayList<>();


        for (int core = 0; core < coreLength; core++) {
            float width = Mth.lerp(core / (float) coreLength, 4f, 4f);
            Vec3 coreEnd = coreStart.add(0, 0, 1).add(randomVector(0f).multiply(width, 1, width));
            beamVectors.add(coreStart);
            beamVectors.add(coreEnd);
            coreStart = coreEnd;

            int branchSegments = 1;
            beamVectors.addAll(generateBranch(coreEnd, branchSegments));
        }
    }

    public static List<Vec3> generateBranch(Vec3 origin, int maxLength) {
        List<Vec3> branchSegements = new ArrayList<>();
        Random random = new Random();
        int branches = random.nextInt(maxLength + 1);

        for (int i = 0; i < branches; i++) {
        }
        return branchSegements;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            generateLightningBeams();
        }
    }

    public static Vec3 randomVector(float radius) {
        double x = Math.random() * 2 * radius - radius;
        double y = Math.random() * 2 * radius - radius;
        double z = Math.random() * 2 * radius - radius;
        return new Vec3(x, y, z);
    }

    public List<Vec3> getBeamCache() {
        if (beamVectors == null)
            generateLightningBeams();
        return beamVectors;
    }

    @Override
    public void spawnParticles() {

    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        DamageSources.applyDamage(entity, damage, ASARSpellRegistry.LUMINOUS_BEAM.get().getDamageSource(this, getOwner()));

        MagicManager.spawnParticles(level(), ASARParticleRegistry.STARS_PARTICLE.get(), entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 10, entity.getBbWidth() / 3, entity.getBbHeight() / 3, entity.getBbWidth() / 3, 0.1, false);
    }
}
