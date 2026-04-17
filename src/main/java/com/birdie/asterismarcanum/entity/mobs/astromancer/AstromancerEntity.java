package com.birdie.asterismarcanum.entity.mobs.astromancer;

import com.birdie.asterismarcanum.entity.mobs.dragonfly.DragonflyEntity;
import com.birdie.asterismarcanum.registries.ASARItemsRegistry;
import com.birdie.asterismarcanum.registries.ASARSchoolRegistry;
import com.birdie.asterismarcanum.registries.SpellRegistries;
import com.google.common.collect.Sets;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.NeutralWizard;
import io.redspace.ironsspellbooks.entity.mobs.goals.PatrolNearLocationGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.SpellBarrageGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardRecoverGoal;
import io.redspace.ironsspellbooks.entity.mobs.wizards.IMerchantWizard;
import io.redspace.ironsspellbooks.item.InkItem;
import io.redspace.ironsspellbooks.loot.SpellFilter;
import io.redspace.ironsspellbooks.player.AdditionalWanderingTrades;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.aces_spell_utils.entity.mobs.goals.WizardSpellComboGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.*;

// basiocally a complete clone of electromancer from DTE because its SO GOOD
public class AstromancerEntity extends NeutralWizard implements IMerchantWizard {
    public AstromancerEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        xpReward = 25;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
//        this.goalSelector.addGoal(2, new SpellBarrageGoal(this, SpellRegistry.VOLT_STRIKE_SPELL.get(), 1, 3, 100, 250, 3));

        this.goalSelector.addGoal(3, new WizardAttackGoal(this, 1.5f, 25, 40)
                .setSpells(// Attack
                        List.of(SpellRegistries.LUMINOUS_BEAM.get(), SpellRegistries.STARFIRE.get(), SpellRegistries.STAR_SWARM.get()),
                        // Defense
                        List.of(SpellRegistries.CELESTIAL_TETHER.get(), SpellRegistries.BRIGHTBURST.get(), SpellRegistries.PIERCING_LIGHT.get()),
                        // Movement
                        List.of(SpellRegistries.WISHING_STAR.get()),
                        // Support
                        List.of(SpellRegistry.EVASION_SPELL.get())
                )
                .setSpellQuality(0.25F, 0.25F)
                .setDrinksPotions()
                .setAllowFleeing(true)
        );
        this.goalSelector.addGoal(2, new WizardSpellComboGoal(this,
                List.of(
                        SpellRegistries.STARCUTTER.get(),
                        SpellRegistries.BRIGHTBURST.get(),
                        SpellRegistries.STAR_SWARM.get()
                ), 1.3f, 1.3f, 100, 250));

        this.goalSelector.addGoal(3, new PatrolNearLocationGoal(this, 30, .75f));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new WizardRecoverGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
        // No likey bugs
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DragonflyEntity.class, true));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ASARItemsRegistry.ASTRAL_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ASARItemsRegistry.ASTRAL_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ASARItemsRegistry.ASTRAL_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(ASARItemsRegistry.ASTRAL_BOOTS.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ASARItemsRegistry.CELESTIAL_STAFF));
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = Utils.random;
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 2.5)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2)
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 2.5)
                .add(Attributes.MOVEMENT_SPEED, .35)
                .add(AttributeRegistry.MAX_MANA, 100);
    }

    @Override
    public boolean isAlliedTo(Entity entityIn) {
        if (entityIn instanceof IMagicSummon summon && summon.getSummoner() == this)
        {
            return true;
        }
        else if (entityIn instanceof AstromancerEntity)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // So he doesn't die when combo-ing - ace
    //furthermore, the astromancer literally lives on a precarious bridge on a mountain
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    /***
     * Merchant
     */
    @Nullable
    private Player tradingPartner;
    @Nullable
    protected MerchantOffers offer;

    // Serialized
    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    // Not Serialized
    private long lastRestockCheckDayTime;

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean preventTrade = (!this.level().isClientSide && this.getOffers().isEmpty() || this.getTarget() != null || isAngryAt(player));

        if (hand == InteractionHand.MAIN_HAND)
        {
            if (preventTrade && !this.level().isClientSide)
            {
                // Iron doesn't have anything in here, so I don't think I should either
            }
        }
        if (!preventTrade)
        {
            if (!this.level().isClientSide && !this.getOffers().isEmpty())
            {
                if (shouldRestock())
                {
                    restock();
                }
                this.startTrading(player);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    private void startTrading(Player player)
    {
        this.setTradingPlayer(player);
        this.lookControl.setLookAt(player);
        this.openTradingScreen(player, this.getDisplayName(), 0);
    }

    @Override
    public int getRestocksToday() {
        return numberOfRestocksToday;
    }

    @Override
    public void setRestocksToday(int restocks) {
        this.numberOfRestocksToday = restocks;
    }

    @Override
    public long getLastRestockGameTime() {
        return lastRestockGameTime;
    }

    @Override
    public void setLastRestockGameTime(long time) {
        this.lastRestockGameTime = time;
    }

    @Override
    public long getLastRestockCheckDayTime() {
        return lastRestockCheckDayTime;
    }

    @Override
    public void setLastRestockCheckDayTime(long time) {
        this.lastRestockCheckDayTime = time;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPartner = player;
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return tradingPartner;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offer == null)
        {
            this.offer = new MerchantOffers();

            this.offer.addAll(createRandomOffers(2, 3));

            // Ink
            if (this.random.nextFloat() < 0.25f) {
                this.offer.add(new AdditionalWanderingTrades.InkBuyTrade((InkItem) ItemRegistry.INK_UNCOMMON.get()).getOffer(this, this.random));
            }
            if (this.random.nextFloat() < 0.25f) {
                this.offer.add(new AdditionalWanderingTrades.InkBuyTrade((InkItem) ItemRegistry.INK_RARE.get()).getOffer(this, this.random));
            }
            if (this.random.nextFloat() < 0.25f) {
                this.offer.add(new AdditionalWanderingTrades.InkBuyTrade((InkItem) ItemRegistry.INK_EPIC.get()).getOffer(this, this.random));
            }

            this.offer.add(new AdditionalWanderingTrades.RandomScrollTrade(new SpellFilter(ASARSchoolRegistry.ASTRAL.get()), 0F, 0.25F).getOffer(this, this.random));
            if (this.random.nextFloat() < .8f) {
                this.offer.add(new AdditionalWanderingTrades.RandomScrollTrade(new SpellFilter(ASARSchoolRegistry.ASTRAL.get()), .3f, .7f).getOffer(this, this.random));
            }
            if (this.random.nextFloat() < .8f) {
                this.offer.add(new AdditionalWanderingTrades.RandomScrollTrade(new SpellFilter(ASARSchoolRegistry.ASTRAL.get()), .8f, 1f).getOffer(this, this.random));
            }

            this.offer.add(new MerchantOffer(
                    new ItemCost(ItemRegistry.MITHRIL_SCRAP.get(), 6),
                    new ItemStack(ASARItemsRegistry.ASTRAL_RUNE.get(), 1),
                    2,
                    0,
                    1.2F
            ));

            this.offer.removeIf(Objects::isNull);

            numberOfRestocksToday++;
        }

        return this.offer;
    }

    private static final List<VillagerTrades.ItemListing> filler = List.of(
            new AdditionalWanderingTrades.SimpleBuy(1, new ItemCost(ASARItemsRegistry.DRAGONFLY_WINGS.get(), 1), 6, 12),
            new AdditionalWanderingTrades.SimpleSell(1, new ItemStack(ASARItemsRegistry.ASTRAL_RUNE.get(), 2), 25, 55),
            new AdditionalWanderingTrades.SimpleSell(1, new ItemStack(ASARItemsRegistry.DRAGONFLY_WINGS.get(), 1), 6, 12)
    );

    private Collection<MerchantOffer> createRandomOffers(int min, int max)
    {
        Set<Integer> set = Sets.newHashSet();
        int fillerTrades = random.nextIntBetweenInclusive(min, max);
        for (int i = 0; i < 10 && set.size() < fillerTrades; i++)
        {
            set.add(random.nextInt(filler.size()));
        }
        Collection<MerchantOffer> offers = new ArrayList<>();
        for (Integer integer : set)
        {
            offers.add(filler.get(integer).getOffer(this, this.random));
        }

        return offers;
    }

    @Override
    public void overrideOffers(MerchantOffers merchantOffers) {
        // Nothing in here
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || isTrading();
    }

    @Override
    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    @Override
    public void notifyTradeUpdated(ItemStack itemStack) {
        if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20)
        {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!itemStack.isEmpty()), this.getSoundVolume(), this.getSoundVolume());
        }
    }

    protected SoundEvent getTradeUpdatedSound(boolean affirm)
    {
        return affirm ? SoundRegistry.TRADER_YES.get() : SoundRegistry.TRADER_NO.get();
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundRegistry.TRADER_YES.get();
    }

    /***
     * NBT
     */
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        serializeMerchant(pCompound, this.offer, this.lastRestockGameTime, this.numberOfRestocksToday);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        deserializeMerchant(pCompound, c -> this.offer = c);
    }
}
