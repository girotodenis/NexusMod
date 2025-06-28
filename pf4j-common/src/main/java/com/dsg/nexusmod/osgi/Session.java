package com.dsg.nexusmod.osgi;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Session {

	/**
	 * Inicia uma transação no banco de dados.
	 * @throws SQLException Se ocorrer um erro ao iniciar a transação.
	 */
	void beginTransaction() throws SQLException;

	/**
	 * Confirma a transação atual.
	 * @throws SQLException Se ocorrer um erro ao confirmar a transação.
	 */
	void commitTransaction() throws SQLException;

	/**
	 * Cancela a transação atual.
	 * @throws SQLException Se ocorrer um erro ao cancelar a transação.
	 */
	void rollbackTransaction() throws SQLException;

	/**
	 * Executa um comando SQL que não retorna resultados (INSERT, UPDATE, DELETE).
	 * @param sql Comando SQL.
	 * @param params Parâmetros para o comando SQL.
	 * @return O número de linhas afetadas.
	 * @throws SQLException Se ocorrer um erro na execução do comando.
	 */
	int executeUpdate(String sql, Object... params) throws SQLException;

	/**
	 * Executa um comando SQL que retorna resultados (SELECT).
	 * @param sql Comando SQL.
	 * @param params Parâmetros para o comando SQL.
	 * @return Um ResultSet com os resultados da consulta.
	 * @throws SQLException Se ocorrer um erro na execução do comando.
	 */
	ResultSet executeQuery(String sql, Object... params) throws SQLException;

}