package org.zkoss.essentials.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the CHITS database table.
 * 
 */
@Entity
@Table(name="CHITS")
public class Chit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="CHITS_ID_GENERATOR", sequenceName="CHITS_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHITS_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable=false)
	private long id;

	@Column(name="CHIT_CENTRE_NO", nullable=false)
	private BigDecimal chitCentreNo;

	@Column(name="CHIT_SERIAL_NO", nullable=false, length=20)
	private String chitSerialNo;

	@Column(name="CHIT_VALUE", nullable=false, precision=10, scale=4)
	private BigDecimal chitValue;

	@Column(name="CHIT_WIN_VALUE", precision=12, scale=4)
	private BigDecimal chitWinValue;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHIT_DATE",nullable = false)
    private Date chitDate;

	//bi-directional many-to-one association to ChitCombination
	@OneToMany(mappedBy="chit", fetch=FetchType.LAZY)
	private Set<ChitCombination> chitCombinations;

    public Chit() {
        chitCombinations = new HashSet<ChitCombination>();
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getChitCentreNo() {
		return this.chitCentreNo;
	}

	public void setChitCentreNo(BigDecimal chitCentreNo) {
		this.chitCentreNo = chitCentreNo;
	}

	public String getChitSerialNo() {
		return this.chitSerialNo;
	}

	public void setChitSerialNo(String chitSerialNo) {
		this.chitSerialNo = chitSerialNo;
	}

	public BigDecimal getChitValue() {
		return this.chitValue;
	}

	public void setChitValue(BigDecimal chitValue) {
		this.chitValue = chitValue;
	}

	public BigDecimal getChitWinValue() {
		return this.chitWinValue;
	}

	public void setChitWinValue(BigDecimal chitWinValue) {
		this.chitWinValue = chitWinValue;
	}

	public Set<ChitCombination> getChitCombinations() {
		return this.chitCombinations;
	}

	public void setChitCombinations(Set<ChitCombination> chitCombinations) {
		this.chitCombinations = chitCombinations;
	}
	
	public void addChitCombinatiion(ChitCombination chitCombination){
		this.chitCombinations.add(chitCombination);
		if(chitCombination.getChit() != this){
			chitCombination.setChit(this);
		}
	}

    public Date getChitDate() {
        return chitDate;
    }

    public void setChitDate(Date chitDate) {
        this.chitDate = chitDate;
    }
}