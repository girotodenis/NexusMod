package com.dsg.nexusmod.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class LogBundleActivator implements BundleActivator {

    // Logger do Log4j
    private static final Logger logger = LogManager.getLogger(LogBundleActivator.class);

    @Override
    public void start(BundleContext context) {
        logger.info("LogBundle iniciado com sucesso!");
        logger.info("Este é um exemplo de log usando Log4j em um bundle OSGi.");
        System.out.println("LogBundle iniciado com sucesso!");
    }

    @Override
    public void stop(BundleContext context) {
        logger.info("LogBundle parado!");
        System.out.println("LogBundle parado!");
    }
}
