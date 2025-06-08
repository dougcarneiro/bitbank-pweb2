package br.edu.ifpb.pweb2.bitbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;

@Component
public class ContaService implements Service<Conta, Integer> {
    
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CorrentistaService correntistaService;

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findById(Integer id) {
        return contaRepository.findById(id).orElse(null);
    }

    @Override
    public Conta save(Conta conta) {
        Correntista correntista = correntistaService.findById(conta.getCorrentista().getId());
        conta.setCorrentista(correntista);
        return contaRepository.save(conta);
    }

    public Conta findByCorrentista(Correntista correntista) {
        return contaRepository.findByCorrentista(correntista);
    }

    public Conta findByNumeroWithTransacoes(String numero) {
        return contaRepository.findByNumeroWihTransacoes(numero);
    }

    public Conta findByIdWithTransacoes(Integer id) {
        return contaRepository.findByIdWithTransacoes(id);
    }
}