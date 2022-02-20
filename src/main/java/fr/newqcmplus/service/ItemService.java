package fr.newqcmplus.service;

import fr.newqcmplus.dao.IItemDAO;
import fr.newqcmplus.entity.Item;
import fr.newqcmplus.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
	

	@Autowired
	private IItemDAO itemDAO;
	
	public Item saveItem(Item item) {
		return itemDAO.save(item);
	}

	public Item findItemById(Integer id) {
		return itemDAO.findById(id).orElseThrow(() -> new ItemNotFoundException("Item by id " + id + "was not found"));
	}

	public List<Item> findAllItems() {
		return itemDAO.findAll();
	}

	public void deleteItem(Integer id) {
		itemDAO.deleteById(id);;
	}

}