package tv.savageboy74.savagecore.common.util;

/*
 * InventoryHelper.java
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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper
{
    public static void dropInventory(World worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if(!(tileEntity instanceof IInventory))
        {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if(itemstack != null) {
                float f = worldIn.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = worldIn.rand.nextFloat() * 0.8F + 0.1F;

                while(itemstack.stackSize > 0)
                {
                    int j = worldIn.rand.nextInt(21) + 10;

                    if(j > itemstack.stackSize) {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;

                    EntityItem item = new EntityItem(worldIn, (double)((float)pos.getX() + f), (double)((float)pos.getY() + f1), (double)((float)pos.getZ() + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                    if(itemstack.hasTagCompound())
                    {
                        item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    worldIn.spawnEntityInWorld(item);
                }
            }
        }
    }
}
