package org.zkoss.essentials.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the CHIT_COMBINATIONS database table.
 * 
 */
@Entity
@Table(name="CHIT_COMBINATIONS")
public class ChitCombination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="CHIT_COMBINATIONS_ID_GENERATOR", sequenceName="CHIT_COMBINATIONS_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHIT_COMBINATIONS_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable=false)
	private long id;

	@Column(name="BET_VALUE", nullable=false, precision=10, scale=4)
	private BigDecimal betValue;

	@Column(name="COMBINED_ELEMENTS", nullable=false)
	private BigDecimal combinedElements;

	@Column(name="WIN_PLACE", length=1)
	private String winPlace;

	@Column(name="WIN_VALUE", precision=12, scale=4)
	private BigDecimal winValue;

	//bi-directional many-to-one association to Chit
    @ManyToOne
	@JoinColumn(name="CHIT_ID", nullable=false)
	private Chit chit;

	//bi-directional many-to-one association to ChitCombinationDetail
	@OneToMany(mappedBy="chitCombination", fetch=FetchType.LAZY)
	private Set<ChitCombinationDetail> chitCombinationDetails;

    public ChitCombination() {
        chitCombinationDetails = new HashSet<ChitCombinationDetail>();
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getBetValue() {
		return this.betValue;
	}

	public void setBetValue(BigDecimal betValue) {
		this.betValue = betValue;
	}

	public BigDecimal getCombinedElements() {
		return this.combinedElements;
	}

	public void setCombinedElements(BigDecimal combinedElements) {
		this.combinedElements = combinedElements;
	}

	public String getWinPlace() {
		return this.winPlace;
	}

	public void setWinPlace(String winPlace) {
		this.winPlace = winPlace;
	}

	public BigDecimal getWinValue() {
		return this.winValue;
	}

	public void setWinValue(BigDecimal winValue) {
		this.winValue = winValue;
	}

	public Chit getChit() {
		return this.chit;
	}

	public void setChit(Chit chit) {
		this.chit = chit;
		if(!chit.getChitCombinations().contains(this)){
			chit.getChitCombinations().add(this);
		}
	}
	
	public Set<ChitCombinationDetail> getChitCombinationDetails() {
		return this.chitCombinationDetails;
	}

	public void setChitCombinationDetails(Set<ChitCombinationDetail> chitCombinationDetails) {
		this.chitCombinationDetails = chitCombinationDetails;
	}
	
	public void addCombinationDetail(ChitCombinationDetail combinationDetail){
		this.chitCombinationDetails.add(combinationDetail);
		if(combinationDetail.getChitCombination()!= this){
			combinationDetail.setChitCombination(this);
		}
	}
	
}