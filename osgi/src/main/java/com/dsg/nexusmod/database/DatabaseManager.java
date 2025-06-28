package com.dsg.nexusmod.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static Connection connection;

    /**
     * Inicializa a conexão com o banco de dados SQLite.
     * @param dbUrl Caminho para o arquivo do banco de dados SQLite.
     */
    public static void initialize(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
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
                System.out.println("Conexão com o banco de dados SQLite fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o banco de dados: " + e.getMessage());
            }
        }
    }
}