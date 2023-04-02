package com.bumblebee.bumblebeebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "installment_plan")
public class InstallmentPlan implements Serializable {
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
    @Column(name = "plan_name")
    private String planName;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
}
