package fr.newqcmplus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.newqcmplus.entity.Item;

public interface IItemDAO extends JpaRepository<Item, Integer> {

}
