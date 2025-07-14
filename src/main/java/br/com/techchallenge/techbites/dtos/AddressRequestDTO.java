package br.com.techchallenge.techbites.dtos;

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
