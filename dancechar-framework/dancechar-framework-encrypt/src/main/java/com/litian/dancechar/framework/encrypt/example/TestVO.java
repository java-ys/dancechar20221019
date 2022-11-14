package com.litian.dancechar.framework.encrypt.example;

import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;

@EncryptClass
@Data
public class TestVO extends BaseVO{

    private String id;

    // @EncryptField(value =  EncryptTypeEnum.ADDRESS)
    private String address;

    @EncryptField(value =  EncryptTypeEnum.EMAIL)
    private String email;

    @EncryptField(value =  EncryptTypeEnum.ID_CARD)
    private String idCard;

    @EncryptField(value =  EncryptTypeEnum.ID_CARD)
    private String bankCard;
}
