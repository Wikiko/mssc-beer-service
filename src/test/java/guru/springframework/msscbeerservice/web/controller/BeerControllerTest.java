package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJSON = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJSON = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    private BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("BeerName")
                .beerStyle(BeerStyleEnum.ALE)
                .price(BigDecimal.ONE)
                .upc(23123L)
                .build();
    }
}