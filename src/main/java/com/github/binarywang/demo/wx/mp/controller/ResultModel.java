package com.github.binarywang.demo.wx.mp.controller;

import lombok.Data;

@Data
public class ResultModel<T> {
    private Integer code = 200;
    private String message = "操作成功";
    private T data;
}
