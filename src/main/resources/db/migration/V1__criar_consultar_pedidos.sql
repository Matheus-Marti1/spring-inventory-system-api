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
        -- Subquery para o total do pedido
        (SELECT SUM(i2.quantidade * i2.preco_unitario) FROM itens_pedido i2 WHERE i2.pedido_id = p.id) AS total,
        i.id AS item_id,
        i.produto_id,
        pr.nome AS produto_nome,
        i.quantidade,
        i.preco_unitario
    FROM pedidos p
             INNER JOIN clientes c ON c.id = p.cliente_id
             INNER JOIN itens_pedido i ON i.pedido_id = p.id
             INNER JOIN produtos pr ON pr.id = i.produto_id
    WHERE
        (p_cliente_id IS NULL OR p.cliente_id = p_cliente_id)
      AND (p_data_inicio IS NULL OR p.data >= p_data_inicio)
      AND (p_data_fim IS NULL OR p.data <= p_data_fim)
    HAVING
        (p_total_min IS NULL OR total >= p_total_min)
       AND (p_total_max IS NULL OR total <= p_total_max)
    ORDER BY p.data DESC, p.id, i.id;
END;