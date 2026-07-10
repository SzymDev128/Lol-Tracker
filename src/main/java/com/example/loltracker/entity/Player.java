package com.example.loltracker.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String puuid;
    private String gameName;
    private String tagLine;
    private String platform;
    private long summonerLevel;
    private int profileIconId;
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RankEntity> rankEntries =  new ArrayList<>();


}
