package pl.kubaretip.cpo.api.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AddressRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;


    @Test
    public void shouldReturnAddressById() {

        //given
        var address = new Address();
        address.setId(1L);
        given(addressRepository.findById(1L)).willReturn(Optional.of(address));

        //when
        var addressById = addressService.findAddressById(1);

        //then
        assertThat(addressById, is(not(nullValue())));
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void exceptionShouldBeThrownWhenNotFoundAddressById() {

        //given
        given(addressRepository.findById(anyLong())).willReturn(Optional.empty());

        //when + then
        assertThrows(NotFoundException.class, () -> addressService.findAddressById(1));
        verify(addressRepository, times(1)).findById(anyLong());
    }


    @Test
    public void shouldUpdateAddress() {

        //given
        var addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setZipCode("zip-code");
        addressDTO.setStreet("street");
        addressDTO.setCountry("country");
        addressDTO.setCity("city");
        addressDTO.setApartment("apartment");
        addressDTO.setBuilding("building");

        given(addressRepository.findById(anyLong())).willReturn(Optional.of(new Address()));
        given(addressRepository.save(any())).willAnswer(returnsFirstArg());

        // when
        var address = addressService.updateAddressById(addressDTO);

        // then
        assertThat(address, is(not(nullValue())));
        assertThat(address.getApartment(), is(equalTo(addressDTO.getApartment())));
        assertThat(address.getZipCode(), is(equalTo(addressDTO.getZipCode())));
        assertThat(address.getApartment(), is(equalTo(addressDTO.getApartment())));
        assertThat(address.getBuilding(), is(equalTo(addressDTO.getBuilding())));
        assertThat(address.getCity(), is(equalTo(addressDTO.getCity())));
        assertThat(address.getStreet(), is(equalTo(addressDTO.getStreet())));
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
    }


    @Test
    public void shouldMarkAddressAsDeleted() {

        // given
        var mockAddress = mock(Address.class);
        given(addressRepository.findById(anyLong())).willReturn(Optional.of(mockAddress));

        // when
        addressService.markAddressAsDeleted(1L);

        // then
        verify(addressRepository, times(1)).save(mockAddress);
        verify(mockAddress, times(1)).setDeleted(true);
    }


}
