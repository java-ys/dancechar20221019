package com.litian.dancechar.framework.encrypt.example;

import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;

@EncryptClass
@Data
public class BaseVO{

    private String id;

    @EncryptField(value =  EncryptTypeEnum.MOBILE)
    private String mobile;
}
