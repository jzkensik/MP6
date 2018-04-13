package edu.illinois.cs.cs125.mp6.lib;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    public static String getCaption(final String json) {
        if (json == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonObject description = object.getAsJsonObject("description");
        JsonArray array = description.getAsJsonArray("captions");
        for (JsonElement caption: array ) {
            JsonObject captor = caption.getAsJsonObject();
            String doggy = captor.get("text").getAsString();
            return doggy;
        }
        return null;
    }
    public static boolean isADog(String json, double minConfidence) {
        if (json == null) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray tag = object.getAsJsonArray("tags");
        if (tag == null) {
            return false;
        }
        for (JsonElement name: tag ) {
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
    public static boolean isACat(String json, double minConfidence) {
        if (json == null) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray tag = object.getAsJsonArray("tags");
        if (tag == null) {
            return false;
        }
        for (JsonElement name: tag ) {
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
    public static boolean isRick(String json){
        if (json == null) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray tag = object.getAsJsonArray("categories");
        if (tag == null) {
            return false;
        }
        for (JsonElement name: tag ) {
            JsonObject yes = name.getAsJsonObject();
            JsonObject next = yes.getAsJsonObject("detail");
            JsonArray secondArray = next.getAsJsonArray("celebrities");
            if (secondArray == null) {
                return false;
            }
            for (JsonElement arrghh: secondArray) {
                JsonObject no = arrghh.getAsJsonObject();
                String rick = no.get("name").getAsString();
                if (rick.equalsIgnoreCase("Rick Astley")) {
                    return true;
                }
            }
        }
        return false;
    }
}
