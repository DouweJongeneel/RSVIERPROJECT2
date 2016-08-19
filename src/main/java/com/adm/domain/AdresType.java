package com.adm.domain;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public enum AdresType {

	THUISADRES ("Thuisadres"),
	WERKADRES ("Werkadres"),
	BEZORGADRES ("Bezorgadres");

	@Id
	@SequenceGenerator(name = "adresTypeId", sequenceName = "zadresType_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "adresTypeId")
	private Long id;

	private String text;

	AdresType(String text){
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

}
