package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IItemDAO;
import fr.newqcmplus.entity.Item;
import fr.newqcmplus.exception.ItemNotFoundException;

@Service
public class ItemService {

	@Autowired
	private IItemDAO itemDAO;

	public Item addItem(Item item) {
		return itemDAO.save(item);
	}

	public Item findItemById(Integer id) {
		return itemDAO.findById(id).orElseThrow(() -> new ItemNotFoundException("Item by id " + id + "was not found"));
	}

	public List<Item> findAllItems() {
		return itemDAO.findAll();
	}

	public Item updateItem(Item item) {
		return itemDAO.save(item);
	}

	public void deleteItem(Integer id) {
		itemDAO.deleteById(id);
	}

}