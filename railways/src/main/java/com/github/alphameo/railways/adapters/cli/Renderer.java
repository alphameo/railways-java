package com.github.alphameo.railways.adapters.cli;

import java.util.List;

public class Renderer {

    public static String PROMPT = "> ";
    public static int CMD_NAME_WIDTH = 25;

    public String showHelp(String[] options) {
        StringBuilder sb = new StringBuilder();
        sb.append("---Choose option---\n");
        for (var opt : options) {
            sb.append(opt);
        }

        return sb.toString();
    }

    public static String renderOptions(final String title, final String[] options) {
        StringBuilder sb = new StringBuilder();
        sb.append(renderTitle(title));
        sb.append("\n");
        for (int i = 0; i < options.length; i++) {
            sb.append(String.format("  %s. %s\n", i + 1, options[i]));
        }
        sb.append(PROMPT);

        return sb.toString();
    }

    public static <T> String renderList(String title, List<T> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[ %s ]\n", title));
        if (list.isEmpty()) {
            sb.append("  (empty)");
        }
        for (T item : list) {
            sb.append(String.format("  - %s\n", item.toString()));
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static String renderTitle(String title) {
        return String.format("===[ %s ]===", title);
    }

    public static String renderSection(String title) {
        return String.format("---[ %s ]---", title);
    }

    public static String renderOption(String title) {
        return String.format("<> %s", title);
    }

    public static String renderSignature(final String name, final String shortName, final String args) {
        final String argsLine;
        final var names = String.format("%s, %s", name, shortName);
        final var len = names.length();
        if (len == 0) {
            argsLine = "";
        } else {
            argsLine = String.format(
                    "%s%s",
                    " ".repeat(CMD_NAME_WIDTH - len),
                    args);
        }
        return String.format("%s%s", names, argsLine);
    }
}
