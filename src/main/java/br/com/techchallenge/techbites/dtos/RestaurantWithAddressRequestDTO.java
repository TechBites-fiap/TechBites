package br.com.techchallenge.techbites.dtos;

public record RestaurantWithAddressRequestDTO(
        String name,
        String description,
        String cuisine,
        String openingHour,
        Long ownerId,
        AddressRequestDTO address
) {
}
