package com.tyfff.musicapi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnums {

    SUCCESS(200,"success"),

    HTTP_STATUS_500(500,"server error");

    private final int code;

    private final String message;
}
