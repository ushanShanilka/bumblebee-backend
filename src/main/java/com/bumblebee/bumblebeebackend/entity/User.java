package com.bumblebee.bumblebeebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "loan_balance")
    private double loanBalance;
    @Column(name = "used_amount")
    private double usedAmount;
    @Column(name = "email")
    private String email;
    @Column(name = "nic_no")
    private String nicNo;
    @Column(name = "address")
    private String address;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "phone_number")
    private String phoneNumber;
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InstallmentPlan installmentPlanId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
}
