package com.parkit.parkingsystem.integration.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	public static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;

	}

	@AfterAll
	public static void tearDown() {

	}

	private Ticket ticket;

	@Test
	public void testParkingACar() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		when(inputReaderUtil.readSelection()).thenReturn(1);
		Calendar dateIn = Calendar.getInstance();
		dateIn.set(2005, 6, 8, 12, 0, 0);
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle(dateIn.getTime());
		// TODO: check that a ticket is actualy saved in DB and Parking table is updated
		// with availability
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		assertNotNull(ticket);
		assertTrue(ticket.getParkingSpot().isAvailable());
	}

	@Test
	public void testParkingLotExit() throws Exception {

		try {
			this.testParkingACar();
			ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
			Calendar dateOut = Calendar.getInstance();
			dateOut.set(2005, 6, 8, 22, 0, 0);
			parkingService.processExitingVehicle(dateOut.getTime());
			assertThat(this.ticket.getPrice());
			equalTo(1.5);
			assertThat(this.ticket.getOutTime());
			equalTo(this.ticket.getInTime());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}

}
