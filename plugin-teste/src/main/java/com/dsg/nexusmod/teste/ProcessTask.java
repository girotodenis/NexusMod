package com.dsg.nexusmod.teste;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.dsg.nexusmod.controller.ControllerRoot;

public class ProcessTask  extends SwingWorker<Void, Integer> {
	
	private ControllerRoot root;
	
	ProcessTask(ControllerRoot root) {
		this.root = root;
	}
	
    @Override
    protected Void doInBackground() throws Exception {
        // Simulação de uma tarefa que leva tempo
        for (int i = 0; i <= 100; i += 5) {
            // Simula algum trabalho
            Thread.sleep(200);
//            root.fireEvent(new com.dsg.nexusmod.model.Progress(i, "Processando... " + i + "%"));
            // Publica o progresso atual
            publish(i);
        }
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks) {
        // Este método é chamado no EDT quando publish() é invocado
        // Atualiza a UI com o último valor de progresso
        int latestProgress = chunks.get(chunks.size() - 1);
        root.fireEvent(new com.dsg.nexusmod.model.Progress(latestProgress, "latestProgress: " + latestProgress + "%"));
    }
    
    @Override
    protected void done() {
        try {
            get();
            root.fireEvent(new com.dsg.nexusmod.model.Progress(0, "Processamento concluído!"));
        } catch (InterruptedException | ExecutionException ex) {
            root.fireEvent(new com.dsg.nexusmod.model.Progress(0, "Erro no processamento: " + ex.getMessage()));
        }
    }

}
