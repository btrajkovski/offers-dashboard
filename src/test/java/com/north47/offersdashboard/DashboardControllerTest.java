package com.north47.offersdashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = "com.north47:offers:+:8080", stubsMode = StubRunnerProperties.StubsMode.)
public class DashboardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDashboardData() throws Exception {
        String dashboardStringResponse = this.mockMvc.perform(get("/dashboard").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DashboardData dashboardData = new ObjectMapper().readValue(dashboardStringResponse, DashboardData.class);
        assertThat(dashboardData).isNotNull();
        assertThat(dashboardData.getNumOfElements()).isEqualTo(3);
        assertThat(dashboardData.getOffers()).contains("Hulk Pop");
    }
}
