package tv.savageboy74.savagecore.common.util;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper
{
    public static void log(Level logLevel, Object object)
    {
        FMLLog.log(SavageCoreProps.MOD_NAME, logLevel, String.valueOf(object));
    }

    public static void advLog(String modName, Level logLevel, Object object)
    {
        FMLLog.log(modName, logLevel, String.valueOf(object));
    }

    public static void advLog(String modName, Level level, Throwable err, String format, Object... data)
    {
        FMLLog.log(modName, level, err, format, data);
    }

    public static void advLog(String modName, Level level, String format, Object... data)
    {
        FMLLog.log(modName, level, format, data);
    }

    public static void all(Object object)
    {
        log(Level.ALL, object);
    }

    public static void debug(Object object)
    {
        log(Level.DEBUG, object);
    }

    public static void error(Object object)
    {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object)
    {
        log(Level.FATAL, object);
    }

    public static void info(Object object)
    {
        log(Level.INFO, object);
    }

    public static void off(Object object)
    {
        log(Level.OFF, object);
    }

    public static void trace(Object object)
    {
        log(Level.TRACE, object);
    }

    public static void warn(Object object)
    {
        log(Level.WARN, object);
    }

    public static void all(String modName, Object object)
    {
        advLog(modName, Level.ALL, object);
    }

    public static void debug(String modName, Object object)
    {
        advLog(modName, Level.DEBUG, object);
    }

    public static void error(String modName, Object object)
    {
        advLog(modName, Level.ERROR, object);
    }

    public static void fatal(String modName, Object object)
    {
        advLog(modName, Level.FATAL, object);
    }

    public static void info(String modName, Object object)
    {
        advLog(modName, Level.INFO, object);
    }

    public static void off(String modName, Object object)
    {
        advLog(modName, Level.OFF, object);
    }

    public static void trace(String modName, Object object)
    {
        advLog(modName, Level.TRACE, object);
    }

    public static void warn(String modName, Object object)
    {
        advLog(modName, Level.WARN, object);
    }

    public static void advError(String modName, String format, Object... data)
    {
        advLog(modName, Level.ERROR, format,  data);
    }

    public static void advInfo(String modName, String format, Object... data)
    {
        advLog(modName, Level.INFO, format,  data);
    }

    public static void advWarn(String modName, String format, Object... data)
    {
        advLog(modName, Level.WARN, format, data);
    }

    public static void advFatal(String modName, String format, Object... data)
    {
        advLog(modName, Level.FATAL, format, data);
    }

    public static void advAll(String modName, String format, Object... data)
    {
        advLog(modName, Level.ALL, format, data);
    }

    public static void advDebug(String modName, String format, Object... data)
    {
        advLog(modName, Level.DEBUG, format, data);
    }

    public static void advError(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.ERROR, err, format,  data);
    }

    public static void advInfo(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.INFO, err, format,  data);
    }

    public static void advWarn(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.WARN, err, format, data);
    }

    public static void advFatal(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.FATAL, err, format, data);
    }

    public static void advAll(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.ALL, err, format, data);
    }

    public static void advDebug(String modName, Throwable err, String format, Object... data)
    {
        advLog(modName, Level.DEBUG, err, format, data);
    }
}