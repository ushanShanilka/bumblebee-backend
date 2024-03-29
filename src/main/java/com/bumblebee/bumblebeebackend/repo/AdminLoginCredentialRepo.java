package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Admin;
import com.bumblebee.bumblebeebackend.entity.AdminLoginCredential;
import com.bumblebee.bumblebeebackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@Repository
public interface AdminLoginCredentialRepo extends JpaRepository<AdminLoginCredential, Long> {
	Optional<AdminLoginCredential> findById(Long id);
	AdminLoginCredential findByAdminId (Admin adminId);
	List<AdminLoginCredential> findAll();
	void deleteById(Long id);
	AdminLoginCredential findByUserNameAndStatusId(String userName, Status status);

//	@Query(value = "select * from admin_login_credential where user_name=?1 and status_id=1", nativeQuery = true)
//	AdminLoginCredential findByUserNameAndStatusId(String userName, Status status);
}
