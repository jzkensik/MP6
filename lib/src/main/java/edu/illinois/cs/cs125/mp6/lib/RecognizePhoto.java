package edu.illinois.cs.cs125.mp6.lib;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Classifies pictures based off of APIs.
 */
public final class RecognizePhoto {

    /**
     * Get the image width.
     *
     * @param json the JSON string returned by the Microsoft Cognitive Services API
     * @return the width of the image or 0 on failure
     */
    public static int getWidth(final String json) {
        if (json == null) {
            return 0;
        }
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject total = result.getAsJsonObject("metadata");
        int width = total.get("width").getAsInt();
        return width;
    }

    /**
     * Get the image height.
     *
     * @param json the JSON string returned by the Microsoft Cognitive Services API
     * @return the width of the image or 0 on failure
     */
    public static int getHeight(final String json) {
        if (json == null) {
            return 0;
        }
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject total = result.getAsJsonObject("metadata");
        int height = total.get("height").getAsInt();
        return height;
    }

    /**
     * Get the image file type.
     *
     * @param json the JSON string returned by the Microsoft Cognitive Services API
     * @return the type of the image or null
     */
    public static String getFormat(final String json) {
        if (json == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject total = result.getAsJsonObject("metadata");
        String format = total.get("format").getAsString();
        return format;
    }

    /**
     * Returns captions for pictures in JSON format.
     * @param json the string put in.
     * @return the caption
     */
    public static String getCaption(final String json) {
        if (json == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonObject description = object.getAsJsonObject("description");
        JsonArray array = description.getAsJsonArray("captions");
        for (JsonElement caption: array) {
            JsonObject captor = caption.getAsJsonObject();
            String doggy = captor.get("text").getAsString();
            return doggy;
        }
        return null;
    }

    /**
     * Checks to see if an image is of a dog.
     * @param json the string taken
     * @param minConfidence the minimum confidence needed to identify an object
     * @return if the image is of a dog.
     */
    public static boolean isADog(final String json, final double minConfidence) {
        if (json == null) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray tag = object.getAsJsonArray("tags");
        if (tag == null) {
            return false;
        }
        for (JsonElement name: tag) {
            JsonObject dog = name.getAsJsonObject();
            String doggy = dog.get("name").getAsString();
            if (doggy.equals("dog")) {
                double confide = dog.get("confidence").getAsDouble();
                if (confide >= minConfidence) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Checks to see if an image is of a cat.
     * @param json the string fed in
     * @param minConfidence the minimum confidence to identify an object
     * @return whether the image is of a cat
     */
    public static boolean isACat(final String json, final double minConfidence) {
        if (json == null) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray tag = object.getAsJsonArray("tags");
        if (tag == null) {
            return false;
        }
        for (JsonElement name: tag) {
            JsonObject cat = name.getAsJsonObject();
            String kitty = cat.get("name").getAsString();
            if (kitty.equals("cat")) {
                double confide = cat.get("confidence").getAsDouble();
                if (confide >= minConfidence) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * A meme.
     * @param json the string fed in
     * @return If you're ever gonna be given up
     */
    public static boolean isRick(final String json) {
        try {
            if (json == null) {
                return false;
            }
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(json).getAsJsonObject();
            JsonArray tag = object.getAsJsonArray("categories");
            if (tag == null) {
                return false;
            }
            for (JsonElement name : tag) {
                JsonObject yes = name.getAsJsonObject();
                JsonObject next = yes.getAsJsonObject("detail");
                JsonArray secondArray = next.getAsJsonArray("celebrities");
                if (secondArray == null) {
                    return false;
                }
                for (JsonElement arrghh : secondArray) {
                    JsonObject no = arrghh.getAsJsonObject();
                    String rick = no.get("name").getAsString();
                    if (rick.equalsIgnoreCase("Rick Astley")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (NullPointerException f) {
            return false;
        }
    }
}
