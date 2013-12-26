package org.zkoss.essentials.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RACE_DETAILS database table.
 * 
 */
@Entity
@Table(name="RACE_DETAILS")
public class RaceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="RACE_DETAILS_RACEDETID_GENERATOR", sequenceName="RACE_DETAILS_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RACE_DETAILS_RACEDETID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RACE_DET_ID", nullable=false)
	private long raceDetId;

	@Column(nullable=false, precision=2)
	private BigDecimal draw;

	@Column(name="HORSE_ID", nullable=false, length=50)
	private String horseId;

	@Column(length=100)
	private String jockey;

	@Column(name="RESULT_POSITION", precision=2)
	private BigDecimal resultPosition;

	@Column(length=10)
	private String sp;

	@Column(length=100)
	private String trainer;

	//bi-directional many-to-one association to Race
    @ManyToOne
	@JoinColumn(name="RACE_SERIAL_NO", nullable=false)
	private Race race;

    public RaceDetail() {
    }

	public long getRaceDetId() {
		return this.raceDetId;
	}

	public void setRaceDetId(long raceDetId) {
		this.raceDetId = raceDetId;
	}

	public BigDecimal getDraw() {
		return this.draw;
	}

	public void setDraw(BigDecimal draw) {
		this.draw = draw;
	}

	public String getHorseId() {
		return this.horseId;
	}

	public void setHorseId(String horseId) {
		this.horseId = horseId;
	}

	public String getJockey() {
		return this.jockey;
	}

	public void setJockey(String jockey) {
		this.jockey = jockey;
	}

	public BigDecimal getResultPosition() {
		return this.resultPosition;
	}

	public void setResultPosition(BigDecimal resultPosition) {
		this.resultPosition = resultPosition;
	}

	public String getSp() {
		return this.sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getTrainer() {
		return this.trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public Race getRace() {
		return this.race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

    @Override
    public String toString() {
        return getHorseId();
    }
}