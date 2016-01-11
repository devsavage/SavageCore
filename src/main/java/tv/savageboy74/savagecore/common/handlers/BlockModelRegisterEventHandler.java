package tv.savageboy74.savagecore.common.handlers;

/*
 * BlockModelRegisterEventHandler.java
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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tv.savageboy74.savagecore.common.events.BlockModelRegisterEvent;

import java.util.Map;
import java.util.Set;

public class BlockModelRegisterEventHandler
{
    private static Map<Block, Set<IProperty>> hiddenProperties = Maps.newHashMap();

    @SubscribeEvent
    public void onBlockModelRegister(BlockModelRegisterEvent event) {
        BlockModelShapes modelShapes = event.modelShapes;

        for (Map.Entry<Block, Set<IProperty>> entry : hiddenProperties.entrySet()) {
            modelShapes.registerBlockWithStateMapper(entry.getKey(), (new StateMap.Builder()).ignore(entry.getValue().toArray(new IProperty[]{})).build());
        }
    }

    public static void addHiddenProperties(Block block, IProperty... properties) {
        hiddenProperties.put(block, Sets.newHashSet(properties));
    }
}
