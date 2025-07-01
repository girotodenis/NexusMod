package com.dsg.ui;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SwingLogAppender extends AppenderBase<ILoggingEvent> {
    private static final int BUFFER_SIZE = 1000;
    private static final BlockingQueue<LogEntry> logBuffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static final List<LogListener> listeners = new CopyOnWriteArrayList<>();
    
    public SwingLogAppender() {
        System.out.println("SwingLogAppender construtor chamado");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("SwingLogAppender iniciado");
    }

    public static void addListener(LogListener listener) {
        System.out.println("Adicionando listener: " + listener);
        listeners.add(listener);
    }

    public static void removeListener(LogListener listener) {
        System.out.println("Removendo listener: " + listener);
        listeners.remove(listener);
    }

    public static List<LogEntry> getRecentLogs() {
        return new ArrayList<>(logBuffer);
    }

    @Override
    protected void append(ILoggingEvent event) {
        System.out.println("SwingLogAppender.append: " + event.getFormattedMessage());
        
        LogEntry entry = new LogEntry(
            event.getTimeStamp(),
            event.getThreadName(),
            event.getLevel().toString(),
            event.getLoggerName(),
            event.getFormattedMessage()
        );

        // Sempre adiciona ao buffer
        if (!logBuffer.offer(entry)) {
            logBuffer.poll();
            logBuffer.offer(entry);
        }

        // Notifica listeners
        if (!listeners.isEmpty()) {
            for (LogListener listener : listeners) {
                try {
                    listener.onNewLogEntry(entry);
                } catch (Exception e) {
                    System.err.println("Erro ao notificar listener: " + e.getMessage());
                }
            }
        }
    }

    public static class LogEntry {
        private final long timestamp;
        private final String thread;
        private final String level;
        private final String logger;
        private final String message;

        public LogEntry(long timestamp, String thread, String level, String logger, String message) {
            this.timestamp = timestamp;
            this.thread = thread;
            this.level = level;
            this.logger = logger;
            this.message = message;
        }

        public String getFormattedMessage() {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss.SSS");
            return sdf.format(new java.util.Date(timestamp)) + 
                   " [" + thread + "] " + 
                   level + " " + 
                   logger + " - " + 
                   message;
        }
        
        public String getLevel() {
            return level;
        }
    }

    public interface LogListener {
        void onNewLogEntry(LogEntry entry);
    }
}