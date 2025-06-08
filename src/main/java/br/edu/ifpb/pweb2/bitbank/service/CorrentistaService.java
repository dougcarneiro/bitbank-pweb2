package br.edu.ifpb.pweb2.bitbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.util.PasswordUtil;

@Component
public class CorrentistaService implements Service<Correntista, Integer> {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Correntista> findAll() {
        return correntistaRepository.findAll();
    }
    
    @Override
    public Correntista findById(Integer id) {
        return correntistaRepository.findById(id).orElse(null);
    }

    @Override
    public Correntista save(Correntista correntista) {
        correntista.setSenha(PasswordUtil.hashPassword(correntista.getSenha()));
        return correntistaRepository.save(correntista);
    }
    
}
