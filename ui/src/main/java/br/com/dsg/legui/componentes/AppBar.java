package br.com.dsg.legui.componentes;

import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.ProgressBar;

import br.com.dsg.legui.componentes.eventos.MenuChangeSizeEvent;
import br.com.dsg.legui.componentes.eventos.MenuChangeSizeEventListener;
import br.com.dsg.util.Constantes;

public class AppBar extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5615207212602152683L;

	private float x, y, w, h = 0;
	private ProgressBar progressBar ;

	public AppBar(float x, float y, float w, float h) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.progressBar = new ProgressBar(0, this.h-Constantes.ALTURA_PROGRESS_BAR, this.w, Constantes.ALTURA_PROGRESS_BAR);

		add(progressBar);
		
		this.setPosition(this.x, this.y);
		this.setSize(this.w, this.h);

		getListenerMap().addListener(MenuChangeSizeEvent.class, (MenuChangeSizeEventListener) event -> {
			
			float larguraMenu = event.getNewSize().x();
			float larguraApp = this.getFrame().getTooltipLayer().getSize().x();
			float widthAppBar = larguraApp-larguraMenu;
			
			this.x = larguraMenu;
			this.setPosition(this.x, this.y);
			
			this.w = widthAppBar;
			this.setSize(this.w, this.h);
			
			this.progressBar.setSize(this.w, Constantes.ALTURA_PROGRESS_BAR);
		});

	}

	public void setValue(float value) {
		progressBar.setValue(value);
	}

	
}
