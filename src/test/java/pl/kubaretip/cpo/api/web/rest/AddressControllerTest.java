package pl.kubaretip.cpo.api.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.kubaretip.cpo.api.JWTUtilTestConfig;
import pl.kubaretip.cpo.api.SpringSecurityWebTestConfig;
import pl.kubaretip.cpo.api.TranslatorTestConfig;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.dto.mapper.AddressMapper;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.service.AddressService;
import pl.kubaretip.cpo.api.web.rest.request.UpdateAddressRequest;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.kubaretip.cpo.api.SpringSecurityWebTestConfig.MARKETER_USER;
import static pl.kubaretip.cpo.api.SpringSecurityWebTestConfig.MODERATOR_USER;

@WebMvcTest(controllers = AddressController.class)
@ContextConfiguration(classes = {SpringSecurityWebTestConfig.class, JWTUtilTestConfig.class, TranslatorTestConfig.class})
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @MockBean
    private AddressMapper addressMapper;

    @Test
    @WithMockUser
    public void shouldReturns200WhenGetAddressById() throws Exception {

        // given
        var id = 1L;
        var addressDTO = new AddressDTO();
        addressDTO.setId(id);
        given(addressService.findAddressById(anyLong())).willReturn(new Address());
        given(addressMapper.mapToDTO(any(Address.class))).willReturn(addressDTO);

        // when
        var request = get("/addresses/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(((int) id))));
    }

    @Test
    @WithMockUser
    public void shouldReturns404WhenNotFoundAddressById() throws Exception {
        // given
        given(addressService.findAddressById(anyLong())).willThrow(NotFoundException.class);

        // when
        var request = get("/addresses/{id}", 1);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @ParameterizedTest
    @MethodSource("invalidAddresses")
    public void shouldReturns400WhenUpdateAddressWithIncorrectInput(UpdateAddressRequest data) throws Exception {

        // given (in method source)
        // when
        var request = put("/addresses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        // then
        mockMvc.perform(request).andExpect(status().isBadRequest());
        verify(addressService, times(0)).updateAddressById(any());
    }

    private static Stream<Arguments> invalidAddresses() {
        var updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.setId(null);
        updateAddressRequest.setApartment("");
        updateAddressRequest.setBuilding("");
        updateAddressRequest.setZipCode("");
        updateAddressRequest.setStreet("");
        updateAddressRequest.setCity("");
        updateAddressRequest.setCountry("");
        return Stream.of(
                Arguments.of(new UpdateAddressRequest()),
                Arguments.of(updateAddressRequest)
        );
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MARKETER_USER)
    @Test
    public void shouldReturns204WhenUpdateAddressWithCorrectInput() throws Exception {

        // given
        var updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.setId(1L);
        updateAddressRequest.setApartment("test");
        updateAddressRequest.setBuilding("test");
        updateAddressRequest.setZipCode("test");
        updateAddressRequest.setStreet("test");
        updateAddressRequest.setCity("test");
        updateAddressRequest.setCountry("test");
        given(addressMapper.mapUpdateAddressRequestToAddressDTO(any(UpdateAddressRequest.class))).willReturn(new AddressDTO());
        given(addressService.updateAddressById(any(AddressDTO.class))).willReturn(new Address());
        given(addressMapper.mapToDTO(any(Address.class))).willReturn(new AddressDTO());

        // when
        var request = put("/addresses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAddressRequest));

        // then
        mockMvc.perform(request).andExpect(status().isOk());
        verify(addressService, times(1)).updateAddressById(any(AddressDTO.class));
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MARKETER_USER)
    @Test
    public void shouldReturns204WhenMarkAddressAsDeleted() throws Exception {

        // given
        willDoNothing().given(addressService).markAddressAsDeleted(1);

        // when
        var request = patch("/addresses/{id}", 1);

        // then
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(addressService, times(1)).markAddressAsDeleted(1);
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MARKETER_USER)
    @Test
    public void shouldReturns404WhenMarkAddressAsDeleted() throws Exception {

        // given
        willDoNothing().given(addressService).markAddressAsDeleted(1);

        // when
        var request = patch("/addresses/{id}", 1);

        // then
        mockMvc.perform(request).andExpect(status().isNoContent());
        verify(addressService, times(1)).markAddressAsDeleted(1);
    }


}
