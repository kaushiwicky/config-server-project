package habuma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Service
public class BookRepositoryImpl {

	private RestTemplate rest;
	
	// tag::loadBalancedRestTemplate[]
	@Autowired
	public BookRepositoryImpl(@LoadBalanced RestTemplate rest) {
		this.rest = rest;
	}
	// end::loadBalancedRestTemplate[]
	
	// tag::getQuantityOnHand[]
	public long getQuantityOnHand(String isbn) {
		try {
			Inventory s = rest.getForObject("http://inventory/products/{isbn}", Inventory.class, isbn); // <1>
			return s.getAvailable();
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return 0;
			} else {
				throw e;
			}
		}
	}
	// end::getQuantityOnHand[]
	
	@Data
	private static class Inventory {
		private String sku;
		private long available;
	}
	
}
