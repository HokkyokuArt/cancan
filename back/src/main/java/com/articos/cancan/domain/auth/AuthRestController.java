package com.articos.cancan.domain.auth;

import com.articos.cancan.domain.auth.AuthRecords.*;
import com.articos.cancan.domain.usuario.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UUID> register(@RequestBody @Valid RegisterPayload request) {
        Usuario user = authService.register(request);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginPayload request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
