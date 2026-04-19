package com.Smillio.app.dtos;

public class EstadisticasFacturasResponse {

    private Double totalPagado;
    private Double totalPendiente;
    private Double totalVencido;
    private Long cantidadPagadas;
    private Long cantidadPendientes;
    private Long cantidadVencidas;

    public EstadisticasFacturasResponse(Double totalPagado, Double totalPendiente, Double totalVencido,
                                        Long cantidadPagadas, Long cantidadPendientes, Long cantidadVencidas) {
        this.totalPagado = totalPagado;
        this.totalPendiente = totalPendiente;
        this.totalVencido = totalVencido;
        this.cantidadPagadas = cantidadPagadas;
        this.cantidadPendientes = cantidadPendientes;
        this.cantidadVencidas = cantidadVencidas;
    }

    public Double getTotalPagado() { return totalPagado; }
    public Double getTotalPendiente() { return totalPendiente; }
    public Double getTotalVencido() { return totalVencido; }
    public Long getCantidadPagadas() { return cantidadPagadas; }
    public Long getCantidadPendientes() { return cantidadPendientes; }
    public Long getCantidadVencidas() { return cantidadVencidas; }
}
