package com.example.loltracker.controller;

import com.example.loltracker.dto.PlayerProfileResponse;
import com.example.loltracker.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{platform}/{gameName}/{tagLine}")
    public PlayerProfileResponse getPlayerProfile(
            @PathVariable String platform,
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        return playerService.getPlayerProfile(platform, gameName, tagLine);
    }
}
