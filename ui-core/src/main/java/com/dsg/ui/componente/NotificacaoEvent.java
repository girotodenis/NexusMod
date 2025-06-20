package com.dsg.ui.componente;

import com.dsg.nexusmod.ui.TaskNotificationType;

public class NotificacaoEvent {
	private String mensagem;
	private TaskNotificationType tipo;

	public NotificacaoEvent(String mensagem, TaskNotificationType tipo) {
		super();
		this.mensagem = mensagem;
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public TaskNotificationType getTipo() {
		return tipo;
	}

}
