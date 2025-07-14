package br.com.techchallenge.techbites.dtos;

import java.time.LocalDateTime;

public record RestaurantWithAddressResponseDTO(
        Long id,
        String name,
        String description,
        String cuisine,
        String openingHour,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt,
        UserResponseDTO owner,
        AddressResponseDTO address
) {
}
