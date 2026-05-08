package com.zg.natural_transmute.common.blocks.state.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;

@AllArgsConstructor
@Getter
@MethodsReturnNonnullByDefault
public enum HCStovePart implements StringRepresentable {

    /*
    X→
    Z↑

    Layer1 (y=0)
    BB
    BM
    Facing↓

    Layer2 (y=1)
    BB
    BB
    Facing↓

    B: normal part
    M: main part
     */
    X0Y0Z0(1, 0, 0, 0, "x0y0z0"),
    X0Y0Z1(2, 0, 0, 1, "x0y0z1"),
    X1Y0Z0(3, 1, 0, 0, "x1y0z0", true),
    X1Y0Z1(4, 1, 0, 1, "x1y0z1"),
    X0Y1Z0(5, 0, 1, 0, "x0y1z0"),
    X0Y1Z1(6, 0, 1, 1, "x0y1z1"),
    X1Y1Z0(7, 1, 1, 0, "x1y1z0"),
    X1Y1Z1(8, 1, 1, 1, "x1y1z1"),
    ;

    public static final HCStovePart MAIN_BLOCK = X1Y0Z0;

    private final int id;
    private final int x;
    private final int y;
    private final int z;
    private final String name;
    private final boolean isMainBlock;

    HCStovePart(int id, int x, int y, int z, String name) {
        this(id, x, y, z, name, false);
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Vec3i getRelativePos(Direction facing) {
        return toGlobalOffset(facing, new Vec3i(1 - getX(), getY(), -getZ()));
    }

    public Vec3i getRelativeMainPos(Direction facing) {
        return toGlobalOffset(facing, new Vec3i(1 - getX(), getY(), -getZ()).multiply(-1));
    }

    private Vec3i toGlobalOffset(Direction facing, Vec3i localOffset) {
        var a = facing.getAxis() == Direction.Axis.X ? -facing.getAxisDirection().getStep() : 0;
        var b = facing.getAxis() == Direction.Axis.Z ? -facing.getAxisDirection().getStep() : 0;
        var x = a * localOffset.getX() + b * localOffset.getX();
        var y = localOffset.getY();
        var z = -b * localOffset.getZ() + a * localOffset.getZ();
        return new Vec3i(x, y, z);
    }
}