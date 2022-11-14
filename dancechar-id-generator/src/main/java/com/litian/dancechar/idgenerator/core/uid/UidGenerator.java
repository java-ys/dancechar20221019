package com.litian.dancechar.idgenerator.core.uid;

import com.litian.dancechar.idgenerator.core.uid.exception.UidGenerateException;

public interface UidGenerator {
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);
}
