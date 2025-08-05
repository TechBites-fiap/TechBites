package br.com.techchallenge.techbites.infrastructure.dtos;

public record AddressRequestDTO(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {

}
