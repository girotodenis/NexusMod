package com.dsg.nexusmod;

import java.util.Iterator;

import org.slf4j.LoggerFactory;

import com.dsg.ui.SwingLogAppender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

public class LoggingConfigurator {

	static boolean appenderExists = false;
   
	public static void configureSwingLogAppender() {
        try {
            // Obtém o logger raiz
            Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            LoggerContext context = rootLogger.getLoggerContext();
            
            // Verifica se o SwingLogAppender já está configurado
            Iterator<Appender<ILoggingEvent>> iteratorForAppenders = rootLogger.iteratorForAppenders();
            iteratorForAppenders.forEachRemaining(appender->{
            	if (appender instanceof SwingLogAppender) {
            		appenderExists = true;
            		System.out.println("SwingLogAppender já está configurado");
            	}
            });
            // Se não existir, adiciona o SwingLogAppender
            if (!appenderExists) {
                System.out.println("Adicionando SwingLogAppender programaticamente");
                
                // Cria e configura o appender
                SwingLogAppender swingAppender = new SwingLogAppender();
                swingAppender.setContext(context);
                swingAppender.setName("SWING");
                swingAppender.start();
                
                // Adiciona ao logger raiz
                rootLogger.addAppender(swingAppender);
                
                System.out.println("SwingLogAppender configurado com sucesso");
            }
            
            // Gera um log de teste para verificar
            org.slf4j.Logger logger = LoggerFactory.getLogger(LoggingConfigurator.class);
            logger.info("SwingLogAppender configurado e pronto para uso");
            
        } catch (Exception e) {
            System.err.println("Erro ao configurar SwingLogAppender: " + e.getMessage());
            e.printStackTrace();
        }
    }
}