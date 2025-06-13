package uia3.componentes;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

public class HomeView extends Panel {

   /**
    * 
    */
   private static final long serialVersionUID = -5615207212602152683L;

   public Button botao1;
   public Button botao2;
   public Button botao3;

   public Button botao4;
   public Button botao5;

   public HomeView() {
	   
	   add(this.botao1 = new Button("navegar outra tela", 50, 0,  130, 30));
	   add(this.botao2 = new Button("executar tarefa", 50, 40, 130, 30));
	   add(this.botao3 = new Button("executar ação", 50, 80, 130, 30));
	   
	   add(this.botao4 = new Button("esconder menu", 50, 120, 130, 30));
	   add(this.botao5 = new Button("mostrar menu", 50, 160, 130, 30));
   }


}
