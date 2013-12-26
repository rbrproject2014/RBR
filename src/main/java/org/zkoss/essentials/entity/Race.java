package org.zkoss.essentials.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the RACES database table.
 * 
 */
@Entity
@Table(name="RACES")
public class Race implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="RACES_RACESERIALNO_GENERATOR", sequenceName="RACES_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RACES_RACESERIALNO_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RACE_SERIAL_NO", nullable=false, precision=10)
	private long raceSerialNo;

	@Column(name="FIRST_PLACE_AMOUNT", precision=31, scale=2)
	private BigDecimal firstPlaceAmount;

	@Column(name="FOURTH_PLACE_AMOUNT", precision=31, scale=2)
	private BigDecimal fourthPlaceAmount;

	@Column(name="MEETING_PLACE", nullable=false, length=50)
	private String meetingPlace;

	@Column(name="NUMBER_OF_HORSES", nullable=false, precision=2)
	private BigDecimal numberOfHorses;

    @Temporal( TemporalType.DATE)
	@Column(name="RACE_DATE", nullable=false)
	private Date raceDate;

	@Column(name="RACE_ID", nullable=false, length=50)
	private String raceId;

	@Column(name="RACE_TIME")
	private Timestamp raceTime;

	@Column(name="SECOND_PLACE_AMOUNT", precision=31, scale=2)
	private BigDecimal secondPlaceAmount;

	@Column(name="THIRD_PLACE_AMOUNT", precision=31, scale=2)
	private BigDecimal thirdPlaceAmount;

	@Column(name="WINNERS_WIN_AMOUNT", precision=31, scale=2)
	private BigDecimal winnersWinAmount;

	//bi-directional many-to-one association to RaceDetail
	@OneToMany(mappedBy="race", fetch=FetchType.LAZY)
	private Set<RaceDetail> raceDetails;

    public Race() {
    }

	public long getRaceSerialNo() {
		return this.raceSerialNo;
	}

	public void setRaceSerialNo(long raceSerialNo) {
		this.raceSerialNo = raceSerialNo;
	}

	public BigDecimal getFirstPlaceAmount() {
		return this.firstPlaceAmount;
	}

	public void setFirstPlaceAmount(BigDecimal firstPlaceAmount) {
		this.firstPlaceAmount = firstPlaceAmount;
	}

	public BigDecimal getFourthPlaceAmount() {
		return this.fourthPlaceAmount;
	}

	public void setFourthPlaceAmount(BigDecimal fourthPlaceAmount) {
		this.fourthPlaceAmount = fourthPlaceAmount;
	}

	public String getMeetingPlace() {
		return this.meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public BigDecimal getNumberOfHorses() {
		return this.numberOfHorses;
	}

	public void setNumberOfHorses(BigDecimal numberOfHorses) {
		this.numberOfHorses = numberOfHorses;
	}

	public Date getRaceDate() {
		return this.raceDate;
	}

	public void setRaceDate(Date raceDate) {
		this.raceDate = raceDate;
	}

	public String getRaceId() {
		return this.raceId;
	}

	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}

	public Timestamp getRaceTime() {
		return this.raceTime;
	}

	public void setRaceTime(Timestamp raceTime) {
		this.raceTime = raceTime;
	}

	public BigDecimal getSecondPlaceAmount() {
		return this.secondPlaceAmount;
	}

	public void setSecondPlaceAmount(BigDecimal secondPlaceAmount) {
		this.secondPlaceAmount = secondPlaceAmount;
	}

	public BigDecimal getThirdPlaceAmount() {
		return this.thirdPlaceAmount;
	}

	public void setThirdPlaceAmount(BigDecimal thirdPlaceAmount) {
		this.thirdPlaceAmount = thirdPlaceAmount;
	}

	public BigDecimal getWinnersWinAmount() {
		return this.winnersWinAmount;
	}

	public void setWinnersWinAmount(BigDecimal winnersWinAmount) {
		this.winnersWinAmount = winnersWinAmount;
	}

	public Set<RaceDetail> getRaceDetails() {
		return this.raceDetails;
	}

	public void setRaceDetails(Set<RaceDetail> raceDetails) {
		this.raceDetails = raceDetails;
	}
	
}