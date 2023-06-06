package com.bhole.shop.authn.api.dto;

import com.bhole.shop.common.base.bean.BaseDTO;
import lombok.Data;

/**
 * @program: bhole-shop-authn
 * @description:
 * @author: joke
 * @date: 2023/5/30 14:31
 * @version: 1.0
 */
@Data
public class RegisteredClientReqDTO extends BaseDTO {

    private String clientId;

    private String clientSecret;

    private String clientName;
}
