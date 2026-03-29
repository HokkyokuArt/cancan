package com.articos.cancan.domain;

import com.articos.cancan.security.jwt.role.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TesteRestController {

    @GetMapping("/publico")
    public ResponseEntity<Object> publico() {
        return ResponseEntity.ok("esse método é público!");
    }

    @MemberAccess
    @GetMapping("/member")
    public ResponseEntity<Object> precisaEstarLogado() {
        return ResponseEntity.ok("esse método precisa estar logado!");
    }

    @AdminOnly
    @GetMapping("/admin")
    public ResponseEntity<Object> precisaSerAdmin() {
        return ResponseEntity.ok("esse métdodo precisa ser admin!");
    }
}
