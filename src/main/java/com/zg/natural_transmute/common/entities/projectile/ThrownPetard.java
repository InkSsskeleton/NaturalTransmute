package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ThrownPetard extends ThrowableItemProjectile {

    @Nullable
    private BlockState lastState;
    private boolean inGround;
    private int fuse = 100;

    public ThrownPetard(EntityType<? extends ThrownPetard> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownPetard(Level level, double x, double y, double z) {
        super(NTEntityTypes.PETARD.get(), x, y, z, level);
    }

    public ThrownPetard(Level level, LivingEntity shooter) {
        super(NTEntityTypes.PETARD.get(), shooter, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.level().addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        } else {
            BlockPos blockPos = this.blockPosition();
            BlockState blockState = this.level().getBlockState(blockPos);
            if (!blockState.isAir() && !this.noPhysics) {
                VoxelShape voxelShape = blockState.getCollisionShape(this.level(), blockPos);
                if (!voxelShape.isEmpty()) {
                    Vec3 position = this.position();
                    for (AABB aabb : voxelShape.toAabbs()) {
                        if (aabb.move(blockPos).contains(position)) {
                            this.inGround = true;
                            break;
                        }
                    }
                }
            }

            if (this.inGround && !this.noPhysics && this.lastState != blockState && this.shouldFall()) {
                this.startFalling();
            }

            if (--this.fuse <= 0) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.TNT);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.lastState = this.level().getBlockState(result.getBlockPos());
        Vec3 vec3 = result.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        this.inGround = true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("fuse", this.fuse);
        compound.putBoolean("inGround", this.inGround);
        if (this.lastState != null) {
            compound.put("inBlockState", NbtUtils.writeBlockState(this.lastState));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.fuse = compound.getInt("fuse");
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("inBlockState", 10)) {
            this.lastState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("inBlockState"));
        }
    }

    private boolean shouldFall() {
        return this.inGround && this.level().noCollision(new AABB(this.position(), this.position()).inflate(0.06));
    }

    private void startFalling() {
        this.inGround = false;
        Vec3 vec3 = this.getDeltaMovement();
        double factorX = this.random.nextFloat() * 0.2F;
        double factorY = this.random.nextFloat() * 0.2F;
        double factorZ = this.random.nextFloat() * 0.2F;
        this.setDeltaMovement(vec3.multiply(factorX, factorY, factorZ));
    }

    public void setFuse(int fuse) {
        this.fuse = fuse;
    }

    @Override
    protected Item getDefaultItem() {
        return NTItems.PETARD.get();
    }

}