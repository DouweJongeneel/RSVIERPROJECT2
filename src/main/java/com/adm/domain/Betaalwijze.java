package com.adm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class Betaalwijze {

	private final String[] type = {"harrie.betaling.creditcard",
			"harrie.betaling.cash",
			"harrie.betaling.ideal",
			"harrie.betaling.paypal",
			"harrie.betaling.inkind"};

	@Id
	@SequenceGenerator(name = "betaalwijzeId", sequenceName = "zbetaalwijze_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "betaalwijzeId")
	private Long id;

	@Column
	private String betaalWijze;

	public String getBetaalWijze() {
		return betaalWijze;
	}

	public void setBetaalWijze(String betaalWijze) {
		this.betaalWijze = betaalWijze;
	}

	public String[] getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return betaalWijze;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValue(int value) {
		this.betaalWijze = type[value];
	}


}
