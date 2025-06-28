package com.dsg.nexusmod.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dsg.nexusmod.osgi.Session;

public class DatabaseSession implements Session {

    private final Connection connection;

    /**
     * Construtor que obtém a conexão compartilhada do DatabaseManager.
     */
    public DatabaseSession() {
        this.connection = DatabaseManager.getConnection();
    }

    /**
     * Inicia uma transação no banco de dados.
     * @throws SQLException Se ocorrer um erro ao iniciar a transação.
     */
    @Override
	public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Confirma a transação atual.
     * @throws SQLException Se ocorrer um erro ao confirmar a transação.
     */
    @Override
	public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Cancela a transação atual.
     * @throws SQLException Se ocorrer um erro ao cancelar a transação.
     */
    @Override
	public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    /**
     * Executa um comando SQL que não retorna resultados (INSERT, UPDATE, DELETE).
     * @param sql Comando SQL.
     * @param params Parâmetros para o comando SQL.
     * @return O número de linhas afetadas.
     * @throws SQLException Se ocorrer um erro na execução do comando.
     */
    @Override
	public int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }

    /**
     * Executa um comando SQL que retorna resultados (SELECT).
     * @param sql Comando SQL.
     * @param params Parâmetros para o comando SQL.
     * @return Um ResultSet com os resultados da consulta.
     * @throws SQLException Se ocorrer um erro na execução do comando.
     */
    @Override
	public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        setParameters(stmt, params);
        return stmt.executeQuery();
    }

    /**
     * Define os parâmetros do PreparedStatement.
     * @param stmt PreparedStatement.
     * @param params Parâmetros a serem definidos.
     * @throws SQLException Se ocorrer um erro ao definir os parâmetros.
     */
    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}