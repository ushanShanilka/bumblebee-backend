package com.bumblebee.bumblebeebackend.filter;

import com.bumblebee.bumblebeebackend.entity.AdminLoginCredential;
import com.bumblebee.bumblebeebackend.entity.AdminPassword;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.repo.AdminLoginCredentialRepo;
import com.bumblebee.bumblebeebackend.repo.AdminPasswordRepo;
import com.bumblebee.bumblebeebackend.repo.StatusRepo;
import com.bumblebee.bumblebeebackend.util.AdminTypeId;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    AdminPasswordRepo adminPasswordRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Status active = statusRepo.findById(StatusId.ACTIVE);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String password = "";

        AdminLoginCredential adminLoginCredential = adminLoginCredentialRepo.findByUserNameAndStatusId(username, active);

        if (Objects.equals(adminLoginCredential.getAdminId().getAdminTypeId().getId(), AdminTypeId.ADMIN)) {
            AdminPassword pass = adminPasswordRepo.findByAdminLoginCredentialIdAndStatusId(adminLoginCredential, active);

            if (Objects.equals(pass, null)) {
                throw new BadCredentialsException("Invalid Credentials");
            }

            authorities.add(new SimpleGrantedAuthority("ADMIN"));

            password = pass.getPassword();
        }

        if (Objects.equals(adminLoginCredential.getAdminId().getAdminTypeId().getId(), AdminTypeId.SUPERADMIN)) {
            AdminPassword pass = adminPasswordRepo.findByAdminLoginCredentialIdAndStatusId(adminLoginCredential, active);

            if (Objects.equals(pass, null)) {
                throw new BadCredentialsException("Invalid Credentials");
            }

            authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));

            password = pass.getPassword();
        }
        return new User(username,password, authorities);
    }
}
