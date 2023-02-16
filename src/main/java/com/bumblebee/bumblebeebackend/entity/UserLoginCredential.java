package com.bumblebee.bumblebeebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_login_credential")
public class UserLoginCredential implements Serializable {
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
    @Column(name = "user_name")
    private String userName;
    @Column(name = "login_status")
    private Short loginStatus;
    @Column(name = "active_jwt")
    private String activeJwt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
}
