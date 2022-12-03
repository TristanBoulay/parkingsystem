package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		double durationMs = outHour - inHour;
		double msToHours = 1000 * 60 * 60;

		double duration = durationMs / msToHours;
//		creation d'une variable qui arrondi duration a deux decimales près
		double durationAr = Math.round(duration * 100.0) / 100.0;

		if (durationAr < 0.5) {
			ticket.setPrice(0);
			return;
		}

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			double price = durationAr * Fare.CAR_RATE_PER_HOUR;
			System.out.println("car price :");
			System.out.println(price);
			System.out.println("duration :");
			System.out.println(durationAr);
			if (ticket.isAlreadyVisited()) {
				price = price - (price * 5 / 100);
			}
			ticket.setPrice(price);
			break;
		}
		case BIKE: {
			double price = durationAr * Fare.BIKE_RATE_PER_HOUR;
			if (ticket.isAlreadyVisited()) {
				price = price - (price * 5 / 100);
			}
			ticket.setPrice(price);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

	}
}