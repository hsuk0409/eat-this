package com.eatthis.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDGenerateUtils {

    public static String makeShorUUID(int radix) {
        UUID uuid = UUID.randomUUID();
        return parseToShorUUID(uuid.toString(), radix);
    }

    public static String parseToShorUUID(String uuid, int radix) {
        int l = ByteBuffer.wrap(uuid.getBytes()).getInt();
        return Integer.toString(l, radix);
    }

}
