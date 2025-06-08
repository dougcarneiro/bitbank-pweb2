package br.edu.ifpb.pweb2.bitbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.model.Transacao;
import br.edu.ifpb.pweb2.bitbank.service.ContaService;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private ContaService contaService;
    
    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaService.findAll();
    }
    
    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("contas/form");
        modelAndView.addObject("conta", new Conta(new Correntista()));
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView modelAndView) {
        modelAndView.setViewName("contas/list");
        modelAndView.addObject("contas", contaService.findAll());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView adicioneConta(Conta conta, ModelAndView modelAndView, RedirectAttributes attr) {
        contaService.save(conta);
        attr.addFlashAttribute("mensagem", "Conta inserida com sucesso!");
        modelAndView.setViewName("redirect:/contas");
        return modelAndView;
    }

    @GetMapping("/nuconta")
    public String getNuConta() {
        return "contas/operacao";
    }

    @PostMapping(value = "/operacao")
    public ModelAndView operacaoConta(String nuConta, Transacao transacao, ModelAndView mav) {
        if (nuConta != null && transacao.getValor() == null) {
            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            if (conta != null) {
                mav.addObject("conta", conta);
                mav.addObject("transacao", transacao);
                mav.setViewName("contas/operacao");
            } else {
                mav.addObject("mensagem", "Conta inexistente!");
                mav.setViewName("contas/operacao");
            }
        } else {
            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            conta.addTransacao(transacao);
            contaService.save(conta);
            return addTransacaoConta(conta.getId(), mav);
        }
        return mav;
    }

    @GetMapping("/{id}/transacoes")
    public ModelAndView addTransacaoConta(@PathVariable("id") Integer idConta, ModelAndView mav) {
        Conta conta = contaService.findByIdWithTransacoes(idConta);
        mav.addObject("conta", conta);
        mav.setViewName("contas/transacoes");
        return mav;
    }
}