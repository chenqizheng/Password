package com.yonyou.password.ui.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class PasswordList {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Password> ITEMS = new ArrayList<Password>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Password> ITEM_MAP = new HashMap<String, Password>();

    private static final int COUNT = 25;


    private static void addItem(Password item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class Password {
        public final String id;
        public final String name;
        public final String password;

        public Password(String id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }
    }
}
