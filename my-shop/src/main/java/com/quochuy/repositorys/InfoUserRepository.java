package com.quochuy.repositorys;

import com.quochuy.models.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUser, Long> {
}
