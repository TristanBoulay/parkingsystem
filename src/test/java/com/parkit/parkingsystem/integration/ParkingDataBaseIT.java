package com.parkit.parkingsystem.integration;

import java.util.Calendar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
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
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;

	}

	@AfterAll
	private static void tearDown() {

	}

	@Test
	public void testParkingACar() {
		Calendar dateIn = Calendar.getInstance();
		dateIn.set(2005, 6, 8, 12, 0, 0);
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle(dateIn.getTime());
		// TODO: check that a ticket is actualy saved in DB and Parking table is updated
		// with availability
	}

	@Test
	public void testParkingLotExit() {
		Calendar dateOut = Calendar.getInstance();
		dateOut.set(2005, 6, 8, 22, 0, 0);
		this.testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processExitingVehicle(dateOut.getTime());
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}

}
