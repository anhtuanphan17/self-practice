package com.traningsprint1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
/** Class: ResponseObject is used to as data type when server response to client
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject <T>{
    private boolean status;
    private String message;
    private Map<String, String> errorMap;
    private List<T> data;


}
