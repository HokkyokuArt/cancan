package com.articos.cancan;

import java.lang.reflect.*;

public class ReflectionUtils {

    public static Class<?> inferGenericType(Class<?> clazz, int index) {
        Type superClass = clazz.getGenericSuperclass();
        return (Class<?>) ((ParameterizedType) superClass).getActualTypeArguments()[index];
    }
}
