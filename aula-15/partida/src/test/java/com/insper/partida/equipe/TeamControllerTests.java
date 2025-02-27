package com.insper.partida.equipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTests {

    MockMvc mockMvc;

    @InjectMocks
    TeamController teamController;

    @Mock
    TeamService teamService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(teamController)
                .build();
    }

    @Test
    void test_listBets() throws Exception {

    }

    @Test
    void test_createBets() throws Exception {
        Bet bet = new Bet();
        bet.setResult(BetResult.HOME);
        bet.setStatus(BetStatus.WON);

        Mockito.when(betService.saveBet(bet)).thenReturn(bet);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bet")
                .contentType("application/json")
                .content("{\"result\": \"HOME\", \"status\": \"WON\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        Bet resp = objectMapper.readValue(result.getResponse().getContentAsString(), Bet.class);

        Assertions.assertEquals(BetResult.HOME, resp.getResult());
        Assertions.assertEquals(BetStatus.WON, resp.getStatus());

    }

    @Test
    void test_verifyBets() throws Exception {
        Bet bet = new Bet();
        bet.setResult(BetResult.HOME);
        bet.setStatus(BetStatus.WON);

        Mockito.when(betService.verifyBet(1)).thenReturn(bet);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/bet/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        Bet resp = objectMapper.readValue(result.getResponse().getContentAsString(), Bet.class);

        Assertions.assertEquals(BetResult.HOME, resp.getResult());
        Assertions.assertEquals(BetStatus.WON, resp.getStatus());

    }



}
