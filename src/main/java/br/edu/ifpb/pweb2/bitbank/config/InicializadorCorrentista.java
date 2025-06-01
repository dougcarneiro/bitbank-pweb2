package br.edu.ifpb.pweb2.bitbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.util.PasswordUtil;

@Component
public class InicializadorCorrentista implements ApplicationRunner {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (correntistaRepository.findByEmail("admin@bitbank.com") == null) {
            Correntista correntista = new Correntista();
            correntista.setNome("Administrador");
            correntista.setEmail("admin@bitbank.com");
            correntista.setSenha(PasswordUtil.hashPassword("123"));
            correntista.setAdmin(true);

            correntistaRepository.save(correntista);
            System.out.println("Correntista admin inserido com sucesso.");
        } else {
            System.out.println("Correntista admin já existe.");
        }
    }
}
