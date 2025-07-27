package br.com.techchallenge.techbites.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.techchallenge.techbites.application.useCases.AddressUseCase;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressUseCase useCase;

    @MockBean
    private AddressDTOMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressRequestDTO addressRequestDTO;
    private Address domainAddress;
    private Address updatedDomainAddress;
    private AddressResponseDTO addressResponseDTO;

    @BeforeEach
    void setup() {
        addressRequestDTO = new AddressRequestDTO(
                "Rua das Palmeiras", "123", "Apt 2", "Centro", "São Paulo", "SP", "12345-678"
        );

        domainAddress = new Address(
                null,
                "Rua das Palmeiras",
                "123",
                "Apt 2",
                "Centro",
                "São Paulo",
                "SP",
                "12345-678",
                null,
                null,
                true
        );

        updatedDomainAddress = new Address(
                1L,
                "Rua das Palmeiras",
                "123",
                "Apt 2",
                "Centro",
                "São Paulo",
                "SP",
                "12345-678",
                null,
                null,
                true
        );

        addressResponseDTO = new AddressResponseDTO(
                1L, "Rua das Palmeiras", "123", "Apt 2", "Centro", "São Paulo", "SP", "12345-678",
                true,null, null
        );
    }

    @Test
    @DisplayName("PUT /address/{id} - Deve atualizar endereço e retornar 200")
    void shouldUpdateAddressAndReturnOkStatus() throws Exception {
        // GIVEN
        when(mapper.toDomain(any(AddressRequestDTO.class))).thenReturn(domainAddress);
        when(useCase.updateAddressById(eq(domainAddress), eq(1L))).thenReturn(updatedDomainAddress);
        when(mapper.toResponseDTO(eq(updatedDomainAddress))).thenReturn(addressResponseDTO);

        // WHEN AND THEN
        mockMvc.perform(put("/address/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.street").value("Rua das Palmeiras"));

        verify(mapper, times(1)).toDomain(any(AddressRequestDTO.class));
        verify(useCase, times(1)).updateAddressById(eq(domainAddress), eq(1L));
        verify(mapper, times(1)).toResponseDTO(eq(updatedDomainAddress));
    }
}

