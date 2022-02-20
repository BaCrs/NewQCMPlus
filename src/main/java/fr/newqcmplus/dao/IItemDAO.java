package fr.newqcmplus.dao;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IItemDAO extends JpaRepository<Item, Integer> {

    @Modifying
    @Query("DELETE FROM Item i WHERE i.id = :id")
    void deleteById(@Param("id") Integer id);

}
