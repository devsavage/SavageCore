package tv.savageboy74.savagecore.common.util;

/*
 * Utils.java
 * Copyright (C) 2015 Savage - github.com/savageboy74
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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Utils
{
    public static String localize(String s)
    {
        return StatCollector.translateToLocal(s);
    }

    public static EntityItem dropItemStackInWorld(World worldObj, double x, double y, double z, ItemStack stack) {
        float f = 0.7F;
        float d0 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d1 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d2 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        EntityItem entityitem = new EntityItem(worldObj, x + d0, y + d1, z + d2, stack);
        entityitem.setPickupDelay(10);
        if (stack.hasTagCompound())
        {
            entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
        }
        worldObj.spawnEntityInWorld(entityitem);
        return entityitem;
    }

    public static void dropAsEntity(World world, int i, int j, int k, ItemStack stack) {
        if(stack != null) {
            double d = 0.7D;
            double e = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double f = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double g = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            EntityItem entityItem = new EntityItem(world, (double)i + e, (double)j + f, (double)k + g, stack.copy());
            entityItem.setPickupDelay(10);
            world.spawnEntityInWorld(entityItem);
        }
    }

    public static boolean isEnabled(ItemStack itemStack)
    {
        return NBTHelper.getBoolean(itemStack, "enabled");
    }

    public static Object getIdent(Item item) {
        return item == null ? null : Item.itemRegistry.getNameForObject(item);
    }

    public static Object getIdent(Block block) {
        return block == null ? null : Block.blockRegistry.getNameForObject(block);
    }

    public static float getDefualtFlySpeed()
    {
        return 0.05F;
    }

    public static void addOreBlockRecipe(Block block, Item item)
    {
        GameRegistry.addRecipe(new ItemStack(block), "XXX", "XXX", "XXX", 'X', item);
    }

    public static void addBlockToIngotRecipe(Item item, Block block)
    {
        GameRegistry.addShapelessRecipe(new ItemStack(item, 9), block);
    }

    public static void addIngotSmelt(Block block, ItemStack itemStack, float f)
    {
        GameRegistry.addSmelting(block, itemStack, f);
    }

    public static void addDustToIngotSmelt(ItemStack dust, ItemStack ingot, float exp)
    {
        GameRegistry.addSmelting(dust, ingot, exp);
    }

    public static boolean consumeFirstInventoryItem(IInventory inventory, ItemStack stack) {
        int slotWithStack = getFirstSlotWithStack(inventory, stack);
        if (slotWithStack > -1) {
            ItemStack stackInSlot = inventory.getStackInSlot(slotWithStack);
            stackInSlot.stackSize--;
            if (stackInSlot.stackSize == 0) {
                inventory.setInventorySlotContents(slotWithStack, null);
            }
            return true;
        }
        return false;
    }

    public static int getFirstSlotWithStack(IInventory inventory, ItemStack stack) {
        inventory = getInventory(inventory);
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (stackInSlot != null && stackInSlot.isItemEqual(stack)) { return i; }
        }
        return -1;
    }

    public static IInventory getInventory(IInventory inventory)
    {
        return inventory;
    }

    public static ItemStack returnItem(ItemStack stack) {
        return (stack == null || stack.stackSize <= 0)? null : stack.copy();
    }

    public static final double lengthSq(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static final double lengthSq(double x, double z) {
        return x * x + z * z;
    }

    public static boolean isClient()
    {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    public static double gaussian(Random rand)
    {
        return rand.nextGaussian() / 6;
    }

    public static boolean isEqual(Block blockA, Block blockB)
    {
        if (blockA == blockB)
        {
            return true;
        }

        if (blockA == null | blockB == null)
        {
            return false;
        }

        return blockA.equals(blockB) || blockA.isAssociatedBlock(blockB);
    }

    public static List<ItemStack> breakBlock(EntityPlayer player, World worldObj, BlockPos pos, Block block, int fortune, boolean doBreak, boolean silkTouch, IBlockState state) {

        if (block.getBlockHardness(worldObj, pos) == -1) {
            return new LinkedList<ItemStack>();
        }
        int meta = worldObj.getBlockState(pos).getBlock().getMetaFromState(state);
        List<ItemStack> stacks = null;
        if (silkTouch && block.canSilkHarvest(worldObj, pos, block.getDefaultState(), player)) {
            stacks = new LinkedList<ItemStack>();
            stacks.add(createStackedBlock(block, meta));
        } else {
            stacks = block.getDrops(worldObj, pos, state, fortune);
        }
        if (!doBreak) {
            return stacks;
        }
        worldObj.playAuxSFXAtEntity(null, 2001, pos, Block.getIdFromBlock(block) + (meta << 12));
        worldObj.setBlockToAir(pos);

        List<EntityItem> result = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
        for (int i = 0; i < result.size(); i++)
        {
            EntityItem entity = result.get(i);
            if (entity.isDead || entity.getEntityItem().stackSize <= 0) {
                continue;
            }
            stacks.add(entity.getEntityItem());
            entity.worldObj.removeEntity(entity);
        }
        return stacks;
    }

    public static ItemStack createStackedBlock(Block block, int bMeta)
    {

        Item item = Item.getItemFromBlock(block);
        if (item.getHasSubtypes()) {
            return new ItemStack(item, 1, bMeta);
        }
        return new ItemStack(item, 1, 0);
    }

    public static NBTTagCompound setItemStackTagName(NBTTagCompound tag, String name) {

        if (name == "") {
            return null;
        }
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        if (!tag.hasKey("display")) {
            tag.setTag("display", new NBTTagCompound());
        }
        tag.getCompoundTag("display").setString("Name", name);

        return tag;
    }

    public static ItemStack readItemStackFromNBT(NBTTagCompound nbt) {

        ItemStack stack = new ItemStack(Item.getItemById(nbt.getShort("id")));
        stack.stackSize = nbt.getInteger("Count");
        stack.setItemDamage(Math.max(0, nbt.getShort("Damage")));

        if (nbt.hasKey("tag", 10))
        {
            nbt.getCompoundTag("tag");
        }

        return stack;
    }

    public static NBTTagCompound writeItemStackToNBT(ItemStack stack, NBTTagCompound nbt) {

        nbt.setShort("id", (short) Item.getIdFromItem(stack.getItem()));
        nbt.setInteger("Count", stack.stackSize);
        nbt.setShort("Damage", (short) getItemDamage(stack));

        if (stack.getTagCompound() != null)
        {
            nbt.setTag("tag", stack.getTagCompound());
        }
        return nbt;
    }

    public static NBTTagCompound writeItemStackToNBT(ItemStack stack, int amount, NBTTagCompound nbt) {

        nbt.setShort("id", (short) Item.getIdFromItem(stack.getItem()));
        nbt.setInteger("Count", amount);
        nbt.setShort("Damage", (short) getItemDamage(stack));

        if (stack.getTagCompound() != null) {
            nbt.setTag("tag", stack.getTagCompound());
        }
        return nbt;
    }

    public static int getItemDamage(ItemStack stack) {

        return Items.diamond.getDamage(stack);
    }
}
