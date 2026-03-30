package com.articos.cancan.utils;

import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.*;
import java.util.*;

@RestController
@RequestMapping("utils")
public class UtilsRestController {
    @GetMapping("/enum/{enumName}")
    public ResponseEntity<List<Map<String, Object>>> getEnum(@PathVariable @NotEmpty String enumName) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
        Class<? extends Enum<?>> enumClass = ReflectionUtils.findEnumClass(enumName);
        return ResponseEntity.ok(ReflectionUtils.getEnumFull(enumClass));
    }
}
