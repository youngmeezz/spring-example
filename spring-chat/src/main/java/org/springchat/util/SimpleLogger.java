package org.springchat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zacconding
 */
public class SimpleLogger {

    private static final String NEW_LINE;
    private static Gson TO_SRING_GSON;
    public static final PrintStream PS = System.out;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT;

    static {
        TO_SRING_GSON = new GsonBuilder().serializeNulls().create();
        NEW_LINE = System.getProperty("line.separator");
        SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyMMdd HH:mm:ss.SSS");
    }

    public static void info(String message, Object... args) {
        String prefix = SIMPLE_DATE_FORMAT.format(new Date()) + " : " + getClassName();
        println(prefix + " " + message, args);
    }

    public static void error(Throwable t) {
        error(null, t);
    }

    public static void error(String message, Throwable t) {
        println(SIMPLE_DATE_FORMAT.format(new Date()) + " [ERROR] " + getClassName() + " : " + (message == null ? "" : message));
        if (t != null) {
            t.printStackTrace(PS);
            // println(getStackTraceString(t));
        }
    }

    public static void print(String content) {
        PS.print(content);
    }

    public static void print(String content, Object... args) {
        PS.print(parseContent(content, args));
    }

    public static void println(String content) {
        PS.println(content);
    }

    public static void println(String content, Object... args) {
        PS.println(parseContent(content, args));
    }

    public static String toJSON(Object inst) {
        return TO_SRING_GSON.toJson(inst);
    }

    public static String getStackTraceString(int cursor) {
        StackTraceElement[] elts = Thread.currentThread().getStackTrace();
        if (elts == null || elts.length == 1) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int start, size;
        if (cursor >= 0) {
            start = cursor + 2;
            size = elts.length;
        }
        else {
            start = 2;
            size = start - cursor + 1;
        }

        return getStackTraceString(elts, start, size);
    }

    public static String getStackTraceString(StackTraceElement[] se, int start, int size) {
        if (se == null) {
            return "";
        }

        if (size < 0) {
            size = 0;
        }
        size = Math.min(size, se.length);
        if (start >= size) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < size; i++) {
            sb.append("\t").append(se[i].toString());
            if (i != size - 1) {
                sb.append(NEW_LINE);
            }
        }

        return sb.toString();
    }

    public static String getStackTraceString(Throwable t) {
        StackTraceElement[] elts = null;

        if ((t == null) || ((elts = t.getStackTrace()) == null)) {
            return "";
        }

        return getStackTraceString(elts, 0, elts.length);
    }

    // ============================= private
    private static String parseContent(String content, Object[] args) {
        if (args == null || args.length == 0 || content == null || content.length() < 2) {
            return content;
        }

        int argIdx = 0;
        int length = content.length();
        StringBuilder sb = new StringBuilder(length > 16 ? length : 16);

        for (int i = 0; i < length; i++) {
            char curChar = content.charAt(i);
            if ((content.charAt(i) == '{') && (i + 1 < length) && (content.charAt(i + 1) == '}') && (isRange(args, argIdx))) {
                sb.append(args[argIdx++]);
                i++;
            }
            else {
                sb.append(curChar);
            }
        }

        return sb.toString();
    }

    private static boolean isRange(Object[] array, int idx) {
        if (idx < 0 || array == null || array.length <= idx) {
            return false;
        }
        return true;
    }

    private static String getClassName() {
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }
}