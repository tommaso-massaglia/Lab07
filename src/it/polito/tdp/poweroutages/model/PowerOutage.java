package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {

	int nerc_id;
	int customers_afflicted;
	LocalDateTime start;
	LocalDateTime finish;

	public PowerOutage(int nerc_id, int customers_afflicted, LocalDateTime start, LocalDateTime finish) {
		this.nerc_id = nerc_id;
		this.customers_afflicted = customers_afflicted;
		this.start = start;
		this.finish = finish;
	}

	public int getNerc_id() {
		return nerc_id;
	}

	public int getCustomers_afflicted() {
		return customers_afflicted;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getFinish() {
		return finish;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finish == null) ? 0 : finish.hashCode());
		result = prime * result + nerc_id;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (finish == null) {
			if (other.finish != null)
				return false;
		} else if (!finish.equals(other.finish))
			return false;
		if (nerc_id != other.nerc_id)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage [nerc_id=" + nerc_id + ", customers_afflicted=" + customers_afflicted + ", start=" + start
				+ ", finish=" + finish + "]";
	}

}
