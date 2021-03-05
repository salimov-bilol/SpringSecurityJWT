package com.harvard.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    String jwt;
}
