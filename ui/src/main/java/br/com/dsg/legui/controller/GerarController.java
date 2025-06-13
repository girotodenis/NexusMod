package br.com.dsg.legui.controller;

public interface GerarController<T extends AbstractController<?> > {

	
	T criar(AbstractController<?> controllerPai);
}
