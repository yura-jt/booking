package com.railway.booking.controller;

import com.railway.booking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTest {

//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void findById_TodoEntryNotFound_ShouldRender404View() throws Exception {
//        when(userService.findById(1));
//
//        mockMvc.perform(get("/admin/panel", 1L));
//
//        verify(userService, times(1)).findByEmail(anyString());
//        verifyZeroInteractions(userService);
//    }
}