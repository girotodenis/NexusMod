package br.com.dsg.legui.componentes.eventos;

import org.liquidengine.legui.listener.EventListener;

public interface  MenuChangeSizeEventListener extends EventListener<MenuChangeSizeEvent> {

  
    /**
     *
     */
    @Override
    void process(MenuChangeSizeEvent event);

    
}
