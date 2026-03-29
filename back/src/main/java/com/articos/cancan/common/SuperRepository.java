package com.articos.cancan.common;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

import java.util.*;

@NoRepositoryBean
public interface SuperRepository<T> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
}
