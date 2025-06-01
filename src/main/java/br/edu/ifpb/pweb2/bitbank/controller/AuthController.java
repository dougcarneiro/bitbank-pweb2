package br.edu.ifpb.pweb2.bitbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @GetMapping
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("auth/login");
        model.addObject("usuario", new Correntista());
        return model;
    }

    @PostMapping
    public ModelAndView valide(Correntista correntista, HttpSession session, ModelAndView model, RedirectAttributes redirectAttributes) {
        if ((correntista = this.isValido(correntista)) != null) {
            session.setAttribute("usuario", correntista);
            model.setViewName("redirect:/home");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
            model.setViewName("redirect:/auth");
        }

        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView model, HttpSession session) {
        session.invalidate();
        model.setViewName("redirect:/auth");
        return model;
    }

    private Correntista isValido(Correntista correntista) {
        Correntista correntistaBD = correntistaRepository.findByEmail(correntista.getEmail());
        boolean valido = false;
        if (correntistaBD != null) {
            if (PasswordUtil.checkPass(correntista.getSenha(), correntistaBD.getSenha())) {
                valido = true;
            } 
        }
        return valido ? correntistaBD : null;
    }
}
