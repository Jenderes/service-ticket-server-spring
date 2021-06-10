package com.example.service_ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.impl.ticket.TicketServiceImpl;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import com.example.service_ticket.service.user.UserService;
import com.example.service_ticket.utils.PatchUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TicketServiceMock {
    private static final TypeReference<List<TicketEntity>> LIST_TICKET_TYPE_REFERENCE = new TypeReference<List<TicketEntity>>() {};
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<TicketEntity> ticketList;
    private static UserEntity testUser;
    @Spy
    TicketValidationService ticketValidationService;
    @Mock
    TicketAutoFillService ticketAutoFillService;
    @Mock
    UserService userService;
    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    TicketServiceImpl ticketServiceMock;

    @BeforeAll
    public static void init() throws IOException {
        ticketList = objectMapper.readValue(TicketServiceMock.class.getResourceAsStream("/ticket.json"), LIST_TICKET_TYPE_REFERENCE);
        testUser = new UserEntity();
        testUser.setUserId(1L);
        testUser.setFirstName("Ростислав");
        testUser.setLastName("Джангоев");
    }

    @Test
    void getAllTicketTest() {
        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
        List<TicketEntity> listTicket = ticketServiceMock.getAllTicket();
        Assertions.assertEquals(ticketList, listTicket);
    }

    @Test
    void deleteTicketTest(){
        Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThrows(TicketNotFoundException.class, () -> ticketServiceMock.deleteTicketById(10L));
    }

    @Test
    void saveTicketTest() {
        Mockito.when(ticketRepository.save(Mockito.any(TicketEntity.class))).thenAnswer((a) -> a.getArgument(0));
        Mockito.when(ticketAutoFillService.fillOnCreate(Mockito.any(TicketEntity.class))).thenAnswer((a) -> a.getArgument(0));
        Mockito.when(userService.getCurrentUser()).thenReturn(testUser);
        Assertions.assertEquals(ticketList.get(0),ticketServiceMock.creatTicket(ticketList.get(0)));
    }

    @Test
    void updateTicketTest() {
        Mockito.when(ticketRepository.update(Mockito.any(TicketEntity.class))).thenAnswer((a) -> a.getArgument(0));
        Mockito.when(userService.getCurrentUser()).thenReturn(testUser);
        Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(ticketList.get(0));
        Mockito.when(PatchUtils.mergeToUpdate(Mockito.any(TicketEntity.class),Mockito.any(TicketEntity.class))).thenReturn(ticketList.get(0));
        Assertions.assertEquals(ticketList.get(0),ticketServiceMock.updateTicket(ticketList.get(0)));
    }
}
