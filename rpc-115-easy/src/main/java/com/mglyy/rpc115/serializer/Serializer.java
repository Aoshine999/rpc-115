package com.mglyy.rpc115.serializer;

import java.io.IOException;

public interface Serializer {
    /**
     * 反序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     *
     * @param bytes
     * @param type
     * @oaram <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes,Class<T> type) throws IOException;
}
