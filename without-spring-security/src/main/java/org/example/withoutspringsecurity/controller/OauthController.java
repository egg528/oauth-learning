package org.example.withoutspringsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.example.withoutspringsecurity.service.OauthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
