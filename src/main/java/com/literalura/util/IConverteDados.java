package com.literalura.util;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}