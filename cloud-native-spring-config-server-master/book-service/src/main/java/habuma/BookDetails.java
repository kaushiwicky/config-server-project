package habuma;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDetails {

	private String isbn;
	private String title;
	private String author;
	private String description;
	private long quantityOnHand;
	
}
