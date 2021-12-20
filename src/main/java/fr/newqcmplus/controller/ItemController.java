package fr.newqcmplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.exception.ItemNotFoundException;
import fr.newqcmplus.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping("")
	public ResponseEntity<List<Item>> getAllItems() {
		List<Item> items = itemService.findAllItems();
		return new ResponseEntity<>(items, HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable("id") Integer id) {
		try {
			Item item = itemService.findItemById(id);
			return new ResponseEntity<>(item, HttpStatus.OK);
		} catch(ItemNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Item> addItem(@RequestBody Item item) {
		Item newItem = itemService.addItem(item);
		return new ResponseEntity<>(newItem, HttpStatus.CREATED);
	}
	
	@PutMapping("")
	public ResponseEntity<Item> updateItem(@RequestBody Item item) {
		Item updatedItem = itemService.updateItem(item);
		return new ResponseEntity<>(updatedItem, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable("id") Integer id) {
		itemService.deleteItem(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
