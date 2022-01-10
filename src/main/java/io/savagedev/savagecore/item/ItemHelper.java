package io.savagedev.savagecore.item;

/*
 * ItemHelper.java
 * Copyright (C) 2020 Savage - github.com/devsavage
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemHelper
{
    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, Level world, Player player, BlockPos origin, int width, int height, int depth) {
        return calcAOEBlocks(stack, world, player, origin, width, height, depth, -1);
    }

    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, Level world, Player player, BlockPos origin, int width, int height, int depth, int distance) {
        if (stack == null)
            return ImmutableList.of();

        BlockState state = world.getBlockState(origin);

        if (state.getMaterial() == Material.AIR) {
            return ImmutableList.of();
        }

        HitResult mop = rayTrace(world, player, ClipContext.Fluid.ANY);
        if (mop == null) {
            return ImmutableList.of();
        }

        int x, y, z;
        BlockPos start = origin;

        if(mop.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockRayTraceResult = (BlockHitResult) mop;

            if(!origin.equals(blockRayTraceResult.getBlockPos())) {
                return ImmutableList.of();
            }

            switch (blockRayTraceResult.getDirection()) {
                case DOWN:
                case UP:
                    Vec3i vec = player.getDirection().getNormal();
                    x = vec.getX() * height + vec.getZ() * width;
                    y = blockRayTraceResult.getDirection().getAxisDirection().getStep() * -depth;
                    z = vec.getX() * width + vec.getZ() * height;
                    start = start.offset(-x / 2, 0, -z / 2);
                    if (x % 2 == 0) {
                        if (x > 0 && blockRayTraceResult.getLocation().x() - blockRayTraceResult.getBlockPos().getX() > 0.5d)
                            start = start.offset(1, 0, 0);
                        else if (x < 0 && blockRayTraceResult.getLocation().x() - blockRayTraceResult.getBlockPos().getX() < 0.5d)
                            start = start.offset(-1, 0, 0);
                    }
                    if (z % 2 == 0) {
                        if (z > 0 && blockRayTraceResult.getLocation().z() - blockRayTraceResult.getBlockPos().getZ() > 0.5d)
                            start = start.offset(0, 0, 1);
                        else if (z < 0 && blockRayTraceResult.getLocation().z() - blockRayTraceResult.getBlockPos().getZ() < 0.5d)
                            start = start.offset(0, 0, -1);
                    }
                    break;
                case NORTH:
                case SOUTH:
                    x = width;
                    y = height;
                    z = blockRayTraceResult.getDirection().getAxisDirection().getStep() * -depth;
                    start = start.offset(-x / 2, -y / 2, 0);
                    if (x % 2 == 0 && blockRayTraceResult.getLocation().x() - blockRayTraceResult.getBlockPos().getX() > 0.5d)
                        start = start.offset(1, 0, 0);
                    if (y % 2 == 0 && blockRayTraceResult.getLocation().y() - blockRayTraceResult.getBlockPos().getY() > 0.5d)
                        start = start.offset(0, 1, 0);
                    break;
                case WEST:
                case EAST:
                    x = blockRayTraceResult.getDirection().getAxisDirection().getStep() * -depth;
                    y = height;
                    z = width;
                    start = start.offset(-0, -y / 2, -z / 2);
                    if (y % 2 == 0 && blockRayTraceResult.getLocation().y() - blockRayTraceResult.getBlockPos().getY() > 0.5d)
                        start = start.offset(0, 1, 0);
                    if (z % 2 == 0 && blockRayTraceResult.getLocation().z() - blockRayTraceResult.getBlockPos().getZ() > 0.5d)
                        start = start.offset(0, 0, 1);
                    break;
                default:
                    x = y = z = 0;
            }

            ImmutableList.Builder<BlockPos> builder = ImmutableList.builder();
            for (int xp = start.getX(); xp != start.getX() + x; xp += x / Mth.abs(x)) {
                for (int yp = start.getY(); yp != start.getY() + y; yp += y / Mth.abs(y)) {
                    for (int zp = start.getZ(); zp != start.getZ() + z; zp += z / Mth.abs(z)) {
                        if (xp == origin.getX() && yp == origin.getY() && zp == origin.getZ()) {
                            continue;
                        }

                        if (distance > 0 && Mth.abs(xp - origin.getX()) + Mth.abs(yp - origin.getY()) + Mth.abs(
                                zp - origin.getZ()) > distance) {
                            continue;
                        }

                        BlockPos pos = new BlockPos(xp, yp, zp);
                        if (stack.getItem().isCorrectToolForDrops(world.getBlockState(pos))) {
                            builder.add(pos);
                        }
                    }
                }
            }

            return builder.build();
        }

        return ImmutableList.of();
    }

    public static void destroyExtraAOEBlocks(ItemStack stack, Level world, Player player, BlockPos pos, BlockPos refPos) {
        if (world.getBlockState(pos).isAir())
            return;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!stack.getItem().isCorrectToolForDrops(state)) {
            return;
        }

        BlockState refState = world.getBlockState(refPos);
        float refStrength = blockStrength(refState, player, world, refPos);
        float strength = blockStrength(state, player, world, pos);

        if (!ForgeHooks.isCorrectToolForDrops(state, player) || refStrength / strength > 10f)
            return;

        if (player.isCreative()) {
            block.playerWillDestroy(world, pos, state, player);
            if(block.removedByPlayer(state, world, pos, player, false, state.getFluidState()))
                block.destroy(world, pos, state);

            if (!world.isClientSide()) {
                ((ServerPlayer) player).connection.send(new ClientboundBlockUpdatePacket(world, pos));
            }

            return;
        }

        stack.mineBlock(world, state, pos, player);

        if (!world.isClientSide()) {
            int xp = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayer) player).gameMode.getGameModeForPlayer(), (ServerPlayer) player, pos);

            if (xp == -1) {
                return;
            }

            block.playerWillDestroy(world, pos, state, player);

            if (block.removedByPlayer(state, world, pos, player, true, state.getFluidState())) {
                block.destroy(world, pos, state);
                block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), stack);
                block.popExperience((ServerLevel) world, pos, xp);
            }

            ServerPlayer mpPlayer = (ServerPlayer) player;
            mpPlayer.connection.send(new ClientboundBlockUpdatePacket(world, pos));
        } else {
            world.globalLevelEvent(2001, pos, Block.getId(state));

            if (block.removedByPlayer(state, world, pos, player, true, state.getFluidState())) {
                block.destroy(world, pos, state);
            }

            stack.mineBlock(world, state, pos, player);

            if (stack.getMaxStackSize() == 0 && stack == player.getMainHandItem()) {
                ForgeEventFactory.onPlayerDestroyItem(player, stack, InteractionHand.MAIN_HAND);
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }

            Minecraft.getInstance().getConnection().send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.DOWN));
        }
    }

    public static HitResult rayTrace(Level worldIn, Player player, ClipContext.Fluid fluidMode) {
        float f = player.getYRot();
        float f1 = player.getXRot();
        Vec3 vec3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    public static float blockStrength(BlockState state, Player player, Level world, BlockPos pos) {
        float hardness = state.getDestroySpeed(world, pos);
        if (hardness < 0.0F) {
            return 0.0F;
        }

        if (!ForgeHooks.isCorrectToolForDrops(state, player)) {
            return player.getDigSpeed(state, pos) / hardness / 100F;
        } else {
            return player.getDigSpeed(state, pos) / hardness / 30F;
        }
    }

    public static boolean equalsIgnoreStackSize(ItemStack stack1, ItemStack stack2) {
        if(stack1 != null && stack2 != null) {
            if(Item.getId(stack1.getItem()) - Item.getId(stack2.getItem()) == 0) {
                if(stack1.getItem() == stack2.getItem()) {
                    if(stack1.getDamageValue() == stack2.getDamageValue()) {
                        if(stack1.hasTag() && stack2.hasTag()) {
                            return ItemStack.isSame(stack1, stack2);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return false;
    }
}
