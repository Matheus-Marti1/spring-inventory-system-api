package me.dio.matheusmarti.springapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResumoResponse(
        Long id,
        String clienteNome,
        LocalDate data,
        BigDecimal total
) {
    public static PedidoResumoResponse fromRow(Object[] row) {
        return new PedidoResumoResponse(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((java.sql.Date) row[2]).toLocalDate(),
                (BigDecimal) row[3]
        );
    }
}
