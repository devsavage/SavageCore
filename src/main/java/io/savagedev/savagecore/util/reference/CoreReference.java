package io.savagedev.savagecore.util.reference;

/*
 * CoreReference.java
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

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

public class CoreReference
{
    public static final String MOD_ID = "savagecore";
    public static final String MOD_NAME = "SavageCore";

    public static class Updater
    {
        public static final String UPDATE_URL = "https://api.savagedev.io";
        public static final String BEGIN_UPDATE_CHECK = "Starting update check for {}...";
        public static final String UPDATE_CHECK = "Checking for update...";
        public static final String UP_TO_DATE = "Up to date.";
        public static final String OUTDATED = "This version is outdated! Newest version: {}";
        public static final String UPDATE_STATUS = "Updater status: {}";
        public static final String UPDATE_FAILED = "Update check failed!";
        public static final String UPDATE_DISABLED = "Update checking is disabled! Change this in the config file.";
        public static final String UNSUPPORTED = "This version of Minecraft may not be supported (yet).";
    }

    public static class Strings
    {
        public static final String DOWNLOAD = "Download";
        public static final String RECOMMENDED = "Recommended";
        public static final String LATEST = "Latest";
    }
}
