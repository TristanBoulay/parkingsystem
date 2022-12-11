package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
//		changement de la methode getHours avec la methode getTime qui nous donne un temps en
//		milliseconde, donc plus precis pour calculer le temps passé dans le garage
		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

//		creation d'une variable pour avoir lça duree en milliseconde
		double durationMs = outHour - inHour;
//		coefficient de division des milliseconde pour passer en heure
		double msToHours = 1000 * 60 * 60;

//		calcul de la duree en heure, de type double, non arrondi
		double duration = durationMs / msToHours;

//		integration de free 30 min gratuite de parking
//		en etablissant la condition

		if (duration < 0.5) {
			ticket.setPrice(0);
			return;
		}

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

	}
}