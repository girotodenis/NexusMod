package com.dsg.nexusmod.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {

	private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
	
    private static Connection connection;

    /**
     * Inicializa a conexão com o banco de dados SQLite.
     * @param dbUrl Caminho para o arquivo do banco de dados SQLite.
     */
    public static void initialize(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
            log.trace("Conexão com o banco de dados SQLite estabelecida.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna a conexão ativa com o banco de dados.
     * @return Objeto Connection para o banco de dados.
     */
    public static Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Banco de dados não foi inicializado!");
        }
        return connection;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void shutdown() {
        if (connection != null) {
            try {
                connection.close();
                log.trace("Conexão com o banco de dados SQLite fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o banco de dados: " + e.getMessage());
            }
        }
    }
}