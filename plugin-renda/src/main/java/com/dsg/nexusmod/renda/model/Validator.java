package com.dsg.nexusmod.renda.model;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T object);
}