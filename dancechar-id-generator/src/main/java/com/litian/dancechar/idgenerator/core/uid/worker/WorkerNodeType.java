package com.litian.dancechar.idgenerator.core.uid.worker;

import com.litian.dancechar.idgenerator.core.uid.utils.ValuedEnum;

public enum WorkerNodeType implements ValuedEnum<Integer> {

    CONTAINER(1), ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
    private WorkerNodeType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer value() {
        return type;
    }

}
