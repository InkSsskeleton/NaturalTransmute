package com.zg.natural_transmute.common.blocks.state;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** @noinspection deprecation*/
public class NTBlockProperties extends BlockBehaviour.Properties {

    public boolean isSimpleModelBlock = false;
    public boolean useSimpleBlockItem = false;

    public static NTBlockProperties get() {
        return new NTBlockProperties();
    }

    public static NTBlockProperties ofFullCopy(BlockBehaviour blockBehaviour) {
        NTBlockProperties properties1 = ofLegacyCopy(blockBehaviour);
        BlockBehaviour.Properties properties2 = blockBehaviour.properties();
        properties1.jumpFactor = properties2.jumpFactor;
        properties1.isRedstoneConductor = properties2.isRedstoneConductor;
        properties1.isValidSpawn = properties2.isValidSpawn;
        properties1.hasPostProcess = properties2.hasPostProcess;
        properties1.isSuffocating = properties2.isSuffocating;
        properties1.isViewBlocking = properties2.isViewBlocking;
        properties1.drops = properties2.drops;
        return properties1;
    }

    public static NTBlockProperties ofLegacyCopy(BlockBehaviour blockBehaviour) {
        NTBlockProperties properties1 = new NTBlockProperties();
        BlockBehaviour.Properties properties2 = blockBehaviour.properties();
        properties1.destroyTime = properties2.destroyTime;
        properties1.explosionResistance = properties2.explosionResistance;
        properties1.hasCollision = properties2.hasCollision;
        properties1.isRandomlyTicking = properties2.isRandomlyTicking;
        properties1.lightEmission = properties2.lightEmission;
        properties1.mapColor = properties2.mapColor;
        properties1.soundType = properties2.soundType;
        properties1.friction = properties2.friction;
        properties1.speedFactor = properties2.speedFactor;
        properties1.dynamicShape = properties2.dynamicShape;
        properties1.canOcclude = properties2.canOcclude;
        properties1.isAir = properties2.isAir;
        properties1.ignitedByLava = properties2.ignitedByLava;
        properties1.liquid = properties2.liquid;
        properties1.forceSolidOff = properties2.forceSolidOff;
        properties1.forceSolidOn = properties2.forceSolidOn;
        properties1.pushReaction = properties2.pushReaction;
        properties1.requiresCorrectToolForDrops = properties2.requiresCorrectToolForDrops;
        properties1.offsetFunction = properties2.offsetFunction;
        properties1.spawnTerrainParticles = properties2.spawnTerrainParticles;
        properties1.requiredFeatures = properties2.requiredFeatures;
        properties1.emissiveRendering = properties2.emissiveRendering;
        properties1.instrument = properties2.instrument;
        properties1.replaceable = properties2.replaceable;
        return properties1;
    }

    public NTBlockProperties isSimpleModelBlock() {
        this.isSimpleModelBlock = true;
        return this;
    }

    public NTBlockProperties useSimpleBlockItem() {
        this.useSimpleBlockItem = true;
        return this;
    }

}