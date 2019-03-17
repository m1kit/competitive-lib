package dev.mikit.atcoder.lib.debug;

import dev.mikit.atcoder.lib.io.LightWriter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Debug {

    private static final char ESC = 27;
    private static final String DEBUG_PREFIX = ESC + "[34;43;1m[DEBUG]"+ ESC + "[m %key%=";
    private static final String DEBUG_PROPERTY = "debug";
    private static final String DEBUG_UNKNOWN_CALLER = "unknown";
    private static final String DEBUG_CALL_PATTERN = "^.+\\.debug\\((.+)\\);.*$";
    private static Pattern debugRegex;
    private static boolean enabled = false;
    private static String src;
    private static LightWriter out;

    public static void enable(String s) {
        enabled = true;
        src = s;
        if (debugRegex == null) {
            debugRegex = Pattern.compile(DEBUG_CALL_PATTERN);
        }
    }

    public static boolean autoEnable() {
        try {
            String s = System.getProperty(DEBUG_PROPERTY);
            if (s != null) {
                enable(s);
                return true;
            }
        } catch(AccessControlException ex) {
            src = null;
        }
        return false;
    }



    public static void debug(String key, Object obj) {
        if (!enabled) {
            return;
        }
        if (obj == null) {
            out().ans("null").ln();
        } else if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            if (obj.getClass().getComponentType().isArray()) {
                for (int i = 0; i < len; i++) {
                    debug(key + "[" + i + "]", Array.get(obj, i));
                }
            } else {
                out().print(DEBUG_PREFIX.replace("%key%", key));
                for (int i = 0; i < len; i++) {
                    Object elem = Array.get(obj, i);
                    out().ansSingle(elem);
                }
                out().ln();
            }
        } else {
            out().print(DEBUG_PREFIX.replace("%key%", key)).ansSingle(obj).ln();
        }
    }

    public static void debug(Object obj) {
        if (!enabled) {
            return;
        }
        StackTraceElement elem = Thread.currentThread().getStackTrace()[2];
        String caller = elem.getClassName().replace('.', '/');
        int di = caller.indexOf('$');
        if (di >= 0) {
            caller = caller.substring(0, di);
        }
        Path path = Paths.get(src, caller + ".java");
        try (Stream<String > lines = Files.lines(path)) {
            String line = lines.skip(elem.getLineNumber() - 1).findFirst().orElseThrow(IOException::new);
            Matcher match = debugRegex.matcher(line);
            if (match.matches() && match.groupCount() > 0) {
                debug(match.group(1), obj);
            } else {
                debug(DEBUG_UNKNOWN_CALLER, obj);
            }
        } catch (IOException e) {
            debug(DEBUG_UNKNOWN_CALLER, obj);
        }
    }

    public static LightWriter out() {
        if (out == null) {
            out = new LightWriter(System.out);
            out.enableAutoFlush();
        }
        return out;
    }

}
