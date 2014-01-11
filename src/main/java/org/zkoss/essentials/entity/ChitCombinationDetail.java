package org.zkoss.essentials.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CHIT_COMBINATION_DETAILS database table.
 * 
 */
@Entity
@Table(name="CHIT_COMBINATION_DETAILS")
public class ChitCombinationDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="CHIT_COMBINATION_DETAILS_ID_GENERATOR", sequenceName="CHIT_COMBINATION_DETAILS_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHIT_COMBINATION_DETAILS_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable=false)
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="RACE_DETAIL_ID", nullable=false)
	private RaceDetail raceDetail;

	//bi-directional many-to-one association to ChitCombination
    @ManyToOne
	@JoinColumn(name="CHIT_COMBINATION_ID", nullable=false)
	private ChitCombination chitCombination;

    public ChitCombinationDetail(RaceDetail raceDetail) {
    	this.raceDetail = raceDetail;
    }

    public ChitCombinationDetail(){

    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RaceDetail getRaceDetail() {
		return this.raceDetail;
	}

	public void setRaceDetailId(RaceDetail raceDetail) {
		this.raceDetail = raceDetail;
	}

	public ChitCombination getChitCombination() {
		return this.chitCombination;
	}

	public void setChitCombination(ChitCombination chitCombination) {
		this.chitCombination = chitCombination;
		if(!chitCombination.getChitCombinationDetails().contains(this)){
			chitCombination.getChitCombinationDetails().add(this);
		}
	}
	
}