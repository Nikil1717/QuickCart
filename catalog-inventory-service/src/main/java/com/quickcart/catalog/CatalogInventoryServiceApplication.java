package com.quickcart.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CatalogInventoryServiceApplication {
//hi
	public static void main(String[] args) {
		SpringApplication.run(CatalogInventoryServiceApplication.class, args);
	}

}
