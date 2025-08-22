package org.example.atm;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class AtmResponse {
    private final boolean success;
    private final Map<Integer, Integer> withdrawalMap;
}
