package com.lelegspears.project_wev_services.repositories;

import com.lelegspears.project_wev_services.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
