package com.dsg.nexusmod.renda.model;

import java.util.HashMap;
import java.util.Map;

import com.dsg.nexusmod.renda.entidade.Carteira;

public class CarteiraValidator implements Validator<Carteira> {
    @Override
    public Map<String, String> validate(Carteira carteira) {
        Map<String, String> errors = new HashMap<>();
        
        // Validação do nome
        if (carteira.getNome() == null || carteira.getNome().trim().isEmpty()) {
            errors.put("nome", "O nome da carteira é obrigatório");
        } else if (carteira.getNome().length() < 3) {
            errors.put("nome", "O nome deve ter pelo menos 3 caracteres");
        }
        
        // Validação da descrição
        if (carteira.getDescricao() == null || carteira.getDescricao().trim().isEmpty()) {
            errors.put("descricao", "A descrição da carteira é obrigatória");
        }
        
        return errors;
    }
}
