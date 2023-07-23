package com.Swaraj.BankApp.transaction;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
//    @ManyToOne(cascade = CascadeType.ALL,optional = false)
//    @JoinColumn(name = "account")
    private Integer sender;

//    @ManyToOne(cascade = CascadeType.ALL,optional = false)
//    @JoinColumn(name = "account")
    private Integer receiver;
    private Long amount;
    private String date;
}
