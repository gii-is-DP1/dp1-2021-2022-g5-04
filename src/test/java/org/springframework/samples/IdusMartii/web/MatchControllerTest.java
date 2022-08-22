package org.springframework.samples.IdusMartii.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.InvitationService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.samples.IdusMartii.configuration.SecurityConfiguration;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = MatchController.class, excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)

class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;
    @MockBean
	private CurrentUserService currentUserService;
    @MockBean
	private UserService userService;
    @MockBean
	private PlayerService playerService;
    @MockBean
	private InvitationService invitationService;
    @MockBean
    AchievementService achievementService;

    @WithMockUser(username = "admin1")
    @Test
    void testInitCreationForm() throws Exception{
        mockMvc.perform(get("/matches/new"))
                .andExpect(status().isOk()) .andExpect(model().attributeExists("match"))
                .andExpect(view().name("users/crearPartida"));;
    }


    
}
