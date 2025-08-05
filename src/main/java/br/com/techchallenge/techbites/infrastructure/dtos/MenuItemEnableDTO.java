package br.com.techchallenge.techbites.infrastructure.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class MenuItemEnableDTO {

    @NotNull(message = "The price is mandatory.")
    @Positive(message = "The price must be positive.")
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}