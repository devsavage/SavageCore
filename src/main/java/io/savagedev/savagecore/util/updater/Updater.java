package io.savagedev.savagecore.util.updater;

/*
 * Updater.java
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.savagedev.savagecore.util.reference.CoreReference;
import io.savagedev.savagecore.util.logger.LogHelper;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class Updater
{
    public UpdateStatus STATUS = UpdateStatus.NONE;

    protected String baseUrl = CoreReference.Updater.UPDATE_URL;
    protected String currentVersion;
    protected String minecraftVersion;
    protected String modId;
    protected boolean recommended = false;

    protected String downloadUrl;

    public Updater setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;

        return this;
    }

    public Updater setMinecraftVersion(String minecraftVersion) {
        this.minecraftVersion = minecraftVersion;

        return this;
    }

    public Updater setRecommendedOnly(boolean latest) {
        this.recommended = latest;

        return this;
    }

    public Updater setModId(String modId) {
        this.modId = modId;

        return this;
    }

    public void checkForUpdate() throws IOException {
        String version = this.recommended ? getRecommendedVersion() : getLatestVersion();

        if(version == null) {
            STATUS = UpdateStatus.UNKNOWN;
            return;
        }

        beginUpdateCheck();
    }

    public void beginUpdateCheck() {
        String versionData = null;

        try {
            STATUS = UpdateStatus.PENDING;
            versionData = getVersionsData();

            if(versionData == null) {
                STATUS = UpdateStatus.UNKNOWN;
                return;
            }

        } catch (IOException ignored) {}

        if(versionData != null) {
            String version = this.recommended ? getRecommendedVersion(versionData) : getLatestVersion(versionData);

            if(version != null) {
                processVersionStatus(version);
                if(this.getStatus() == UpdateStatus.OUTDATED) {
                    setDownloadUrl(getDownloadUrl(versionData, this.recommended ? CoreReference.Strings.RECOMMENDED.toLowerCase() : CoreReference.Strings.LATEST.toLowerCase()));
                }
            } else {
                STATUS = UpdateStatus.UNKNOWN;
            }
        }
    }

    public String getVersionsData() throws IOException {
        String data = null;
        HttpURLConnection connection = null;
        InputStream stream = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(this.buildUpdateUrl());

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");

            connection.connect();

            stream = connection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            data = bufferedReader.readLine();
        } catch (IOException ignored) {
            LogHelper.error("Update checking encountered an error!");
        } finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException exp) {
                    LogHelper.error("Failed to close buffer!");
                }
            }

            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException exp) {
                    LogHelper.error("Failed to close input stream!");
                }
            }

            if(connection != null) {
                connection.disconnect();
            }
        }

        return data;
    }

    public String getRecommendedVersion(String versionsJson) {
        if(versionsJson == null) {
            return null;
        }

        JsonParser parser = new JsonParser();

        try {
            Object obj = parser.parse(versionsJson);
            JsonObject jsonObject = (JsonObject) obj;

            return jsonObject.get(CoreReference.Strings.RECOMMENDED.toLowerCase()).getAsString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getLatestVersion(String versionsJson) {
        if(versionsJson == null) {
            return null;
        }

        JsonParser parser = new JsonParser();

        Object obj = parser.parse(versionsJson);
        JsonObject jsonObject = (JsonObject) obj;

        return jsonObject.get(CoreReference.Strings.LATEST.toLowerCase()).getAsString();
    }

    public String getRecommendedVersion() {
        try {
            return getRecommendedVersion(getVersionsData());
        } catch (IOException ignored) {}

        return null;
    }

    public String getLatestVersion() {
        try {
            return getLatestVersion(getVersionsData());
        } catch (IOException ignored) {}

        return null;
    }

    public String getVersionForOutput() {
        if(this.isRecommended()) {
            return this.getRecommendedVersion();
        }

        return this.getLatestVersion();
    }

    public void processVersionStatus(String versionToCompare) {
        ComparableVersion current = new ComparableVersion(this.getCurrentVersion());
        ComparableVersion recOrLatest = new ComparableVersion(versionToCompare);

        if(currentVersion.equals("NONE")) {
            setStatus(UpdateStatus.NONE);
            LogHelper.info("Current version is NONE. Development environment?");
            return;
        }

        int versionDiff = recOrLatest.compareTo(current);

        if(versionDiff == 0) {
            setStatus(UpdateStatus.UP_TO_DATE);
        } else if(versionDiff < 0) {
            setStatus(UpdateStatus.AHEAD);
        } else {
            setStatus(UpdateStatus.OUTDATED);
        }
    }

    public UpdateStatus getStatus() {
        return this.STATUS;
    }

    public String getMinecraftVersion() {
        return this.minecraftVersion;
    }

    public String getModId() {
        return this.modId;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String buildUpdateUrl() {
        return this.getBaseUrl() + "/mc-mods/" + this.getModId() + "/versions";
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setStatus(UpdateStatus status) {
        this.STATUS = status;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public String getDownloadUrl(String versionsData, String downloadType) {
        if(versionsData == null) {
            return null;
        }

        JsonParser parser = new JsonParser();

        Object obj = parser.parse(versionsData);
        JsonObject jsonObject = (JsonObject) obj;

        return jsonObject.get(CoreReference.Strings.DOWNLOAD.toLowerCase() + "-"  + downloadType).getAsString();
    }

    public void setDownloadUrl(String url) {
        this.downloadUrl = url;
    }

    public boolean isRecommended() {
        return this.recommended;
    }
}
