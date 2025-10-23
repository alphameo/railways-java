package com.github.alphameo.railways.application.cli;

import java.util.List;

public class Renderer {

    public static String PROMPT = "> ";

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
}
