package com.articos.cancan.security.jwt.role;

import com.articos.cancan.common.*;
import jakarta.persistence.*;

@Converter(autoApply = true)
public class RoleAttributeConverter extends SuperAttributeConverter<Role> {
}
