package br.com.dsg.util;

public class Constantes {
	
	public static int ALTURA_APP = Integer.valueOf( System.getProperty("app.frame.altura") );
	public static int LARGURA_APP = Integer.valueOf( System.getProperty("app.frame.largura") );
	
	public static int LARGURA_MENU_FECHADO = Integer.valueOf( System.getProperty("app.largura.menu.fechado") );
	public static int LARGURA_MENU_ABERTO = Integer.valueOf( System.getProperty("app.largura.menu.aberto") );
	
	public static final int ALTURA_ITEM_MENU = Integer.valueOf( System.getProperty("app.altura.item.menu") );
	public static final int ALTURA_INICIAL_ITEM_MENU = Integer.valueOf( System.getProperty("app.altura.inicial.item.menu") );
	
	//Conf APP_BAR
	public static  int ALTURA_PROGRESS_BAR = Integer.valueOf( System.getProperty("app.altura.progress.bar") ); ;
	public static  int ALTURA_APP_BAR = Integer.valueOf( System.getProperty("app.bar.altura") );
	public static  int LARGURA_APP_BAR = LARGURA_APP - LARGURA_MENU_ABERTO;
	public static  int LARGURA_APP_BAR_FULL = LARGURA_APP - LARGURA_MENU_FECHADO;
	//Conf APP_BAR
	
	public static  int LARGURA_APP_CONTEUDO_MENU_ABERTO = LARGURA_APP - LARGURA_MENU_ABERTO;
	public static  int LARGURA_APP_CONTEUDO_MENU_FECHADO = LARGURA_APP - LARGURA_MENU_FECHADO;
	public static  int ALTURA_APP_CONTEUDO = ALTURA_APP - ALTURA_PROGRESS_BAR;
	
	

}
