package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		try {

			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
			when(inputReaderUtil.readSelection()).thenReturn(1);

			when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(42);
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Test
	public void calculateDiscountForRegularUser() {
		Calendar dateIn = Calendar.getInstance();
		dateIn.set(2005, 6, 8, 12, 0, 0);

		ParkingSpot mockParkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket mockTicket = new Ticket();
		mockTicket.setInTime(dateIn.getTime());
		mockTicket.setParkingSpot(mockParkingSpot);
		mockTicket.setVehicleRegNumber("ABCDEF");
		mockTicket.setAlreadyVisited(true);

		when(ticketDAO.getTicket(anyString())).thenReturn(mockTicket);

		parkingService.processIncomingVehicle(dateIn.getTime());

		when(ticketDAO.getTicket(anyString())).thenReturn(mockTicket);
		when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
		when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

		Calendar dateOut = Calendar.getInstance();
		dateOut.set(2005, 6, 8, 22, 0, 0);

		parkingService.processExitingVehicle(dateOut.getTime());

		assertEquals(14.25, mockTicket.getPrice());
	}

}
