package org.hassen;

import org.hassen.dao.ProduitRepository;
import org.hassen.entities.Produit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CataMvcApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CataMvcApplication.class, args);
		
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		
		produitRepository.save(new Produit("LG 465", 850, 60));
		produitRepository.save(new Produit("Samsung galaxy", 1550, 80));
		produitRepository.save(new Produit("Sony", 860, 20));
		produitRepository.save(new Produit("PS4", 1200, 63));
		produitRepository.save(new Produit("LED 465", 350, 60));
		produitRepository.save(new Produit("PS5", 1850, 10));
		produitRepository.save(new Produit("Vodafone box", 150, 160));
		produitRepository.save(new Produit("Ooredoo box", 650, 200));
		
		produitRepository.findAll().forEach(p->System.out.println(p.getDesignation()));
	}

}
