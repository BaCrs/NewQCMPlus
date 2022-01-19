package fr.newqcmplus.dao;

import fr.newqcmplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.newqcmplus.entity.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAuthorityDAO extends JpaRepository<Authority, Integer> {

    @Query("SELECT a FROM Authority a WHERE a.name = :name")
    Authority getAuthorityByName(@Param("name") String name);

}
