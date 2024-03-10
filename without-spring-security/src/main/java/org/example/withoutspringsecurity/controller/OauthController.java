package org.example.withoutspringsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.service.LoginResponse;
import org.example.withoutspringsecurity.service.OauthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login/{provider}")
    public RedirectView redirect(@PathVariable String provider) {
        String oauthServerUrl = oauthService.getLoginPageUrl(provider);

        return new RedirectView(oauthServerUrl);
    }

    @GetMapping("/callback/{provider}")
    public ResponseEntity<LoginResponse> callback(@PathVariable String provider, @RequestParam String code) {

        return ResponseEntity.ok(oauthService.login(provider, code));
    }
}
