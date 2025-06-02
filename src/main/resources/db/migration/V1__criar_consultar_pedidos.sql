CREATE PROCEDURE IF NOT EXISTS consultar_pedidos (
    IN p_cliente_id BIGINT,
    IN p_data_inicio DATE,
    IN p_data_fim DATE,
    IN p_total_min DECIMAL(15,2),
    IN p_total_max DECIMAL(15,2)
)
BEGIN
SELECT
    p.id AS pedido_id,
    c.nome AS cliente_nome,
    p.data,
    SUM(i.quantidade * i.preco_unitario) AS total
FROM pedidos p
         INNER JOIN clientes c ON c.id = p.cliente_id
         INNER JOIN itens_pedido i ON i.pedido_id = p.id
WHERE
    (p_cliente_id IS NULL OR p.cliente_id = p_cliente_id)
  AND (p_data_inicio IS NULL OR p.data >= p_data_inicio)
  AND (p_data_fim IS NULL OR p.data <= p_data_fim)
GROUP BY p.id, c.nome, p.data
HAVING
    (p_total_min IS NULL OR total >= p_total_min)
   AND (p_total_max IS NULL OR total <= p_total_max)
ORDER BY p.data DESC;
END;