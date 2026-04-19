package com.Smillio.app.dtos;

import jakarta.validation.constraints.NotBlank;

public class PagarFacturaRequest {

    @NotBlank
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
