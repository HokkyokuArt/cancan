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

    public static <T, D> D newInstance(T origem, Class<D> destinoClass) throws InvocationTargetException, InstantiationException,
            IllegalAccessException {
        Class<?> origemClass = origem.getClass();
        for (Constructor<?> constructor : destinoClass.getConstructors()) {
            Class<?>[] params = constructor.getParameterTypes();
            if (params.length == 1 && params[0].isAssignableFrom(origemClass)) {
                Constructor<D> ctor = (Constructor<D>) constructor;
                return ctor.newInstance(origem);
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

    public static Map<String, Object> getOneEnum(Enum<?> enumConstant) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
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
    }

    public static List<Map<String, Object>> getEnumFull(Class<? extends Enum<?>> enumClass) throws InvocationTargetException, NoSuchMethodException
            , IllegalAccessException {
        List<Map<String, Object>> toReturn = new ArrayList<>();
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            toReturn.add(getOneEnum(enumConstant));
        }
        return toReturn;
    }
}
