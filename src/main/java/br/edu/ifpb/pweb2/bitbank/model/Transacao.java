package br.edu.ifpb.pweb2.bitbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "conta")
@Entity
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    @NumberFormat(pattern = "###,##0.00")
    private BigDecimal valor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;
}
