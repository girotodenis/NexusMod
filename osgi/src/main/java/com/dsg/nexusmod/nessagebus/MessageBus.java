package com.dsg.nexusmod.nessagebus;

import java.util.ArrayList;
import java.util.List;

import com.dsg.nexusmod.plugin.MessageListener;

public class MessageBus {
	private final List<MessageListener> listeners = new ArrayList<>();

    // Registra um ouvinte
    public void registerListener(MessageListener listener) {
        listeners.add(listener);
    }

    // Envia uma mensagem para todos os ouvintes
    public void sendMessage(String senderPluginId, String message) {
        for (MessageListener listener : listeners) {
            listener.onMessage(senderPluginId, message);
        }
    }
}
