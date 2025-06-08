package br.edu.ifpb.pweb2.bitbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @OneToMany(mappedBy = "conta")
    private Set<Transacao> transacoes = new HashSet<Transacao>();

    @OneToOne
    @JoinColumn(name = "id_correntista")
    private Correntista correntista;

    public BigDecimal getSaldo() {
        BigDecimal total = BigDecimal.ZERO;
        for (Transacao t : this.transacoes) {
            if (t.getValor() != null) {
                total = total.add(t.getValor());
            }
        }
        return total;
    }

    public Conta(Correntista correntista) {
        this.correntista = correntista;
    }

    public void addTransacao(Transacao transacao) {
        transacao.setConta(this);
        this.transacoes.add(transacao);
    }
}