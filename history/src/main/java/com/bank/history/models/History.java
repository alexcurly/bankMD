package com.bank.history.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="history" , schema="history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transfer_audit_id")
    @Nullable
    private Long transferAuditId;

    @Column(name="profile_audit_id")
    @Nullable
    private Long profileAuditId;

    @Column(name="account_audit_id")
    @Nullable
    private Long accountAuditId;

    @Column(name="anti_fraud_audit_id")
    @Nullable
    private Long antiFraudAuditId;

    @Column(name="public_bank_info_audit_id")
    @Nullable
    private Long publicBankInfoAuditId;

    @Column(name="authorization_audit_id")
    @Nullable
    private Long authorizationAuditId;



}
