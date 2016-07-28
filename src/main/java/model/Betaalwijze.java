package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public enum Betaalwijze {

	CREDITCARD ("Creditcard"),
	PIN ("Pinbetaling"),
	IDEAL ("IDeal betaling"),
	CONTANT ("Contant"),
	NATURA ("Natura");
	
	@Id
	@SequenceGenerator(name = "betaalwijzeId", sequenceName = "zbetaalwijze_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "betaalwijzeId")
	private long id;

	private String text;
	
	Betaalwijze(String text){
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
