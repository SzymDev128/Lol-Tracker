package com.example.loltracker.controller;

import com.example.loltracker.dto.PlayerProfileResponse;
import com.example.loltracker.service.PlayerService;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
@Validated
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{platform}/{gameName}/{tagLine}")
    public PlayerProfileResponse getPlayerProfile(
            @Pattern(regexp = "euw1|eun1|na1|kr|jp1|br1|la1|la2|tr1|ru", message = "Invalid platform")
            @PathVariable String platform,
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        return playerService.getPlayerProfile(platform, gameName, tagLine);
    }
}
