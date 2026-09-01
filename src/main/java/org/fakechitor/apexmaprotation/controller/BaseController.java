package org.fakechitor.apexmaprotation.controller;

import lombok.RequiredArgsConstructor;
import org.fakechitor.apexmaprotation.dto.BaseMapInfo;
import org.fakechitor.apexmaprotation.service.ApexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BaseController {

    private final ApexService apexService;

    @GetMapping("/maps/ranked")
    public ResponseEntity<Map<String, BaseMapInfo>> getRankedRotation(){
        return ResponseEntity.ok().body(apexService.getRankedInfo());
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth(){
        return ResponseEntity.ok().body(Map.of("status", "ok"));
    }
}
