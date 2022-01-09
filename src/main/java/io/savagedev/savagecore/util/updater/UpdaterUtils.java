package io.savagedev.savagecore.util.updater;

/*
 * UpdaterUtils.java
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

import io.savagedev.savagecore.init.ModConfigs;
import io.savagedev.savagecore.util.reference.CoreReference;
import io.savagedev.savagecore.util.logger.LogHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.io.IOException;

public class UpdaterUtils
{
    public static void sendUpdateMessageIfOutdated(String modName, PlayerEvent.PlayerLoggedInEvent event, Updater updater) {
        if(updater.getStatus() == UpdateStatus.OUTDATED) {
            String version = updater.getVersionForOutput();
            String outdatedText = ChatFormatting.AQUA + "[" + modName + "] " +
                    ChatFormatting.RESET +
                    "is " +
                    ChatFormatting.RED +
                    "outdated! " +
                    ChatFormatting.RESET +
                    "Newest Version: " +
                    ChatFormatting.GOLD + version +
                    ChatFormatting.RESET +
                    " Current Version: " +
                    ChatFormatting.RED +
                    updater.getCurrentVersion();
            String downloadText = CoreReference.Strings.DOWNLOAD;
            String update_url = updater.getDownloadUrl();

            event.getPlayer().sendMessage(Component.Serializer.fromJson("[{\"text\":\"" + outdatedText + "\"}," + "{\"text\":\" " + ChatFormatting.WHITE + "[" + ChatFormatting.GREEN + downloadText + ChatFormatting.WHITE + "]\"," + "\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":" + "{\"text\":\"Click to download the latest version\",\"color\":\"yellow\"}}," + "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + update_url + "\"}}]"), Util.NIL_UUID);
        }
    }

    public static void initializeUpdateCheck(Updater updater) {
        if(ModConfigs.ENABLE_UPDATE_CHECK.get()) {
            LogHelper.info(CoreReference.Updater.BEGIN_UPDATE_CHECK, updater.getModId());
            try {
                LogHelper.info(CoreReference.Updater.UPDATE_CHECK);

                updater.checkForUpdate();

                if(updater.getStatus() == UpdateStatus.UNSUPPORTED) {
                    LogHelper.info(CoreReference.Updater.UNSUPPORTED);
                } else if(updater.getStatus() == UpdateStatus.UP_TO_DATE) {
                    LogHelper.info(CoreReference.Updater.UP_TO_DATE);
                } else if(updater.getStatus() == UpdateStatus.OUTDATED) {
                    LogHelper.info(CoreReference.Updater.OUTDATED, updater.getVersionForOutput());
                } else {
                    LogHelper.info(CoreReference.Updater.UPDATE_STATUS, updater.getStatus());
                }
            } catch (IOException e) {
                LogHelper.error(CoreReference.Updater.UPDATE_FAILED);

                e.printStackTrace();
            }
        } else {
            LogHelper.info(CoreReference.Updater.UPDATE_DISABLED);
        }
    }
}
