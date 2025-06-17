package br.com.dsg.swing.util;

public class Constantes {
	
	public static int ALTURA_APP = PropertiesUtil.getInt("altura.app");
	public static int LARGURA_APP= PropertiesUtil.getInt("largura.app");
	public static final java.awt.Color COR_FUNDO_APP = new java.awt.Color(255, 255, 255);
	
	public static int LARGURA_MENU_FECHADO = PropertiesUtil.getInt("largura.menu.fechado");
	public static int LARGURA_MENU_ABERTO = PropertiesUtil.getInt("largura.menu.aberto");
	public static final java.awt.Color COR_FUNDO_MENU = new java.awt.Color(23, 35, 51);
	
	public static final int ALTURA_ITEM_MENU = PropertiesUtil.getInt("altura.item.menu");
	public static final int ALTURA_INICIAL_ITEM_MENU = PropertiesUtil.getInt("altura.inicial.item.menu");
	public static final java.awt.Color COR_FUNDO_ITEM_MENU = new java.awt.Color(23, 35, 51);
	public static final java.awt.Color COR_FUNDO_ITEM_MENU_SELECIONADO = new java.awt.Color(41,57,80);
	public static final java.awt.Color COR_FUNDO_LABEL_ITEM_MENU = new java.awt.Color(255, 255, 255);
	
	//Conf APP_BAR
	public static  int ALTURA_PROGRESS_BAR = 5 ;
	public static  int ALTURA_APP_BAR = PropertiesUtil.getInt("altura.app.bar") ;
	public static  int LARGURA_APP_BAR = LARGURA_APP - LARGURA_MENU_ABERTO;
	public static  int LARGURA_APP_BAR_FULL = LARGURA_APP - LARGURA_MENU_FECHADO;
	public static final java.awt.Color COR_FUNDO_APP_BAR = new java.awt.Color(71, 120, 197);
	//Conf APP_BAR
	
	public static  int LARGURA_APP_CONTEUDO_MENU_ABERTO = LARGURA_APP - LARGURA_MENU_ABERTO;
	public static  int LARGURA_APP_CONTEUDO_MENU_FECHADO = LARGURA_APP - LARGURA_MENU_FECHADO;
	public static  int ALTURA_APP_CONTEUDO = ALTURA_APP - ALTURA_APP_BAR;
	
	

}
