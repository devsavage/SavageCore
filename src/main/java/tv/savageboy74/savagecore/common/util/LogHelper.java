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

    public static void advLog(Level level, Throwable err, String format, Object... data)
    {
        FMLLog.log(SavageCoreProps.MOD_NAME, level, err, format, data);
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

    public static void advError(Throwable err, String format, Object... data)
    {
        advLog(Level.ERROR, err, format,  data);
    }

    public static void advInfo(Throwable err, String format, Object... data)
    {
        advLog(Level.INFO, err, format,  data);
    }

    public static void advWarn(Throwable err, String format, Object... data)
    {
        advLog(Level.WARN, err, format, data);
    }

    public static void advFatal(Throwable err, String format, Object... data)
    {
        advLog(Level.FATAL, err, format, data);
    }

    public static void advAll(Throwable err, String format, Object... data)
    {
        advLog(Level.ALL, err, format, data);
    }

    public static void advDebug(Throwable err, String format, Object... data)
    {
        advLog(Level.DEBUG, err, format, data);
    }
}