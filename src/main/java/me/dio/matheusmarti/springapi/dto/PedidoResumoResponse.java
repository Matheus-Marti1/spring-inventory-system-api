package me.dio.matheusmarti.springapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record PedidoResumoResponse(
        Long id,
        String clienteNome,
        LocalDate data,
        BigDecimal total,
        List<ItemPedidoResponse> itens
) {
    public static List<PedidoResumoResponse> fromRows(List<Object[]> rows) {
        Map<Long, PedidoResumoResponse> pedidosMap = new LinkedHashMap<>();
        for (Object[] row : rows) {
            Long pedidoId = ((Number) row[0]).longValue();
            String clienteNome = (String) row[1];
            LocalDate data = ((java.sql.Date) row[2]).toLocalDate();
            BigDecimal total = (BigDecimal) row[3];

            // ProdutoResponse parcial: apenas id e nome
            ProdutoResponse produto = new ProdutoResponse(
                    ((Number) row[5]).longValue(), // produto_id
                    (String) row[6],               // produto_nome
                    null,                          // descricao
                    null,                          // preco
                    0,                             // estoque
                    null                           // categoria
            );

            ItemPedidoResponse item = new ItemPedidoResponse(
                    ((Number) row[4]).longValue(), // item_id
                    produto,
                    ((Number) row[7]).intValue(),  // quantidade
                    (BigDecimal) row[8],           // preco_unitario
                    ((BigDecimal) row[8]).multiply(BigDecimal.valueOf(((Number) row[7]).intValue())) // subtotal
            );

            PedidoResumoResponse pedido = pedidosMap.get(pedidoId);
            if (pedido == null) {
                pedido = new PedidoResumoResponse(
                        pedidoId,
                        clienteNome,
                        data,
                        total,
                        new ArrayList<>()
                );
                pedidosMap.put(pedidoId, pedido);
            }
            pedido.itens().add(item);
        }
        return new ArrayList<>(pedidosMap.values());
    }
}
