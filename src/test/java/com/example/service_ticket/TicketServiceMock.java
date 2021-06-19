package com.example.service_ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.TicketInformationEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.impl.ticket.TicketServiceImpl;
import com.example.service_ticket.service.kafka.KafkaTicketService;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import com.example.service_ticket.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TicketServiceMock {
    private static final TypeReference<List<TicketEntity>> LIST_TICKET_TYPE_REFERENCE = new TypeReference<List<TicketEntity>>() {};
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<TicketEntity> ticketList;
    private static UserEntity testUser;
    private static TicketEntity createTicket;
    private static TicketEntity updateTicket;

    @Mock
    TicketAutoFillService ticketAutoFillService;
    @Mock
    KafkaTicketService kafkaTicketService;
    @Mock
    UserService userService;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    TicketValidationService ticketValidationService;
    @InjectMocks
    TicketServiceImpl ticketService;

    @BeforeAll
    public static void init() throws IOException {
        ticketList = objectMapper.readValue(TicketServiceMock.class.getResourceAsStream("/ticket.json"), LIST_TICKET_TYPE_REFERENCE);
        ticketList.get(0).getTicketInformation().setCreateDate(LocalDate.now());
        ticketList.get(0).getTicketInformation().setUpdateDate(LocalDate.now());
        ticketList.get(1).getTicketInformation().setUpdateDate(LocalDate.now());
        createTicket = initializeCreateTicket();
        updateTicket = initializeUpdateTicket();
        testUser = initializeUser();
    }

    @Test
    void getAllTicketTest() {
        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
        List<TicketEntity> listTicket = ticketService.getAllTicket();
        Assertions.assertEquals(ticketList, listTicket);
    }

    @Test
    void deleteTicketTest(){
        Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThrows(TicketNotFoundException.class, () -> ticketService.deleteTicketById(10L));
    }

    @Test
    void saveTicketTest() {
        Mockito.when(ticketRepository.save(Mockito.any(TicketEntity.class))).thenAnswer((a) -> a.getArgument(0));
        Mockito.when(userService.getCurrentUser()).thenReturn(testUser);
        Mockito.when(ticketAutoFillService.fillOnCreate(Mockito.any(TicketEntity.class))).thenReturn(createTicket);
        Mockito.when(ticketRepository.save(Mockito.any(TicketEntity.class))).thenReturn(createTicket);
        Assertions.assertEquals(ticketList.get(0),ticketService.creatTicket(createTicket));
    }

    @Test
    void updateTicketTest() {
        Mockito.when(ticketRepository.update(Mockito.any(TicketEntity.class))).thenAnswer((a) -> a.getArgument(0));
        Mockito.when(userService.getCurrentUser()).thenReturn(testUser);
        Mockito.when(ticketAutoFillService.fillOnUpdate(Mockito.any(TicketEntity.class))).thenReturn(updateTicket);
        Mockito.when(ticketRepository.findById(Mockito.anyLong())).thenReturn(ticketList.get(1));
        Assertions.assertEquals(ticketList.get(1),ticketService.updateTicket(updateTicket));
    }

    private static UserEntity initializeUser () {
        testUser = new UserEntity();
        testUser.setUserId(2L);
        testUser.setFirstName("Ростислав");
        testUser.setLastName("Джангоев");
        return testUser;
    }

    private static TicketEntity initializeCreateTicket () {
        createTicket = new TicketEntity();
        createTicket.setTicketInformation(new TicketInformationEntity());
        createTicket.getTicketInformation().setName(ticketList.get(0).getTicketInformation().getName());
        createTicket.setTicketId(ticketList.get(0).getTicketId());
        createTicket.getTicketInformation().setDescription(ticketList.get(0).getTicketInformation().getDescription());
        createTicket.getTicketInformation().setCategory(ticketList.get(0).getTicketInformation().getCategory());
        createTicket.getTicketInformation().setStatus(ticketList.get(0).getTicketInformation().getStatus());
        createTicket.getTicketInformation().setUpdateDate(ticketList.get(0).getTicketInformation().getUpdateDate());
        createTicket.getTicketInformation().setCreateDate(ticketList.get(0).getTicketInformation().getCreateDate());
        return createTicket;
    }

    private static TicketEntity initializeUpdateTicket () {
        updateTicket = new TicketEntity();
        updateTicket.setTicketInformation(new TicketInformationEntity());
        updateTicket.setTicketId(2L);
        updateTicket.getTicketInformation().setName("Тестовый тикет");
        updateTicket.getTicketInformation().setDescription("описание тествого тикета");
        updateTicket.getTicketInformation().setUpdateDate(LocalDate.now());
        return updateTicket;
    }
}
