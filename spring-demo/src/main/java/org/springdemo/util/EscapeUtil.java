package org.springdemo.util;

/**
 * http://javacan.tistory.com/entry/Servlet-3-Async
 */
public abstract class EscapeUtil {

    public static String escape(String orig) {
        if (orig == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder((int) (orig.length() * 1.2f));
        for (int i = 0; i < orig.length(); i++) {
            char c = orig.charAt(i);
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                default:
                    builder.append(c);
            }
        }
        return builder.toString();
    }
}
