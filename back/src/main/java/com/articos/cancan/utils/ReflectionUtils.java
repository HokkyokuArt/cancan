package com.articos.cancan.utils;

import com.articos.cancan.common.interfaces.*;
import io.github.classgraph.*;
import org.apache.commons.lang3.*;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtils {

    public static Class<?> inferGenericType(Class<?> clazz, int index) {
        Type superClass = clazz.getGenericSuperclass();
        return (Class<?>) ((ParameterizedType) superClass).getActualTypeArguments()[index];
    }

    public static <T, D> D newInstance(T origem, Class<D> destinoClass) {
        Class<?> origemClass = origem.getClass();
        for (Constructor<?> constructor : destinoClass.getConstructors()) {
            Class<?>[] params = constructor.getParameterTypes();
            if (params.length == 1 && params[0].isAssignableFrom(origemClass)) {
                Constructor<D> ctor = (Constructor<D>) constructor;
                try {
                    return ctor.newInstance(origem);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    private static Set<Class<?>> getAllClasses() {
        Set<Class<?>> classSet;
        try (ScanResult scanResult = new ClassGraph().acceptPackages("com.articos.cancan").enableClassInfo().scan()) {
            classSet = new HashSet<>(scanResult.getAllClasses().loadClasses());
        }
        return classSet;
    }

    public static Class<?> findClass(String className, String basePath) {
        List<Class<?>> filteredClasses =
                getAllClasses().stream().filter(c -> c.getName().startsWith(basePath) && className.equals(c.getSimpleName())).toList();
        if (filteredClasses.isEmpty()) {
            throw new RuntimeException("Classe não encontrada");
        }
        return filteredClasses.get(0);
    }

    public static Class<?> findClass(String className) {
        return findClass(className, "com.articos.cancan");
    }

    public static Class<? extends Enum<?>> findEnumClass(String className) {
        Class<?> toReturn = findClass(className);
        if (!toReturn.isEnum()) {
            throw new RuntimeException("Classe não encontrada");
        }
        return (Class<? extends Enum<?>>) toReturn;
    }

    public static Map<String, Object> getOneEnum(Enum<?> enumConstant) {
        try {
            Map<String, Object> toReturn = new HashMap<>();
            Class<?> declaringClass = enumConstant.getDeclaringClass();
            List<Field> fields =
                    Arrays.stream(declaringClass.getDeclaredFields()).filter(f -> !f.isEnumConstant() && !Modifier.isStatic(f.getModifiers()) && f.getType().equals(String.class)).toList();
            toReturn.put("name", enumConstant.name());
            if (Descritivo.class.isAssignableFrom(declaringClass)) {
                toReturn.put("descritivo", ((Descritivo) enumConstant).getDescritivo());
            }
            for (Field field : fields) {
                String fieldName = field.getName();
                Object invoke = declaringClass.getMethod("get" + StringUtils.capitalize(fieldName)).invoke(enumConstant);
                toReturn.put(fieldName, invoke == null ? invoke : invoke.toString().isEmpty() ? null : invoke);
            }
            return toReturn;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Map<String, Object>> getEnumFull(Class<? extends Enum<?>> enumClass) {
        List<Map<String, Object>> toReturn = new ArrayList<>();
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            Map<String, Object> oneEnum = getOneEnum(enumConstant);
            if (oneEnum != null) {
                toReturn.add(oneEnum);
            }
        }
        return toReturn;
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException ignored) {
            return getField(clazz.getSuperclass(), name);
        }
    }

    //    apenas testes...
    public static void setIn(Object object, String fieldName, Object value) {
        try {
            Field field = getField(object.getClass(), fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    apenas testes...
    public static void setIn(Object object, Map<String, Object> fieldValueMap) {
        fieldValueMap.entrySet().forEach(s -> {
            setIn(object, s.getKey(), s.getValue());
        });
    }
}
