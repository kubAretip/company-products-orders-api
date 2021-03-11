package pl.kubaretip.cpo.api.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kubaretip.cpo.api.domain.Authority;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AuthorityRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AuthorityServiceImplTest {

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private AuthorityServiceImpl authorityService;


    @Test
    public void shouldReturnAuthorityWhenExists() {

        // given
        var authority = new Authority();
        authority.setName("test");
        given(authorityRepository.findByNameOrViewNameIgnoreCase("test")).willReturn(Optional.of(authority));
        // when
        var result = authorityService.getAuthority("test");

        // then
        assertThat(result.getName(), is(equalTo(authority.getName())));
        verify(authorityRepository, times(1)).findByNameOrViewNameIgnoreCase("test");
    }


    @Test
    public void exceptionShouldBeThrownNotFoundAuthority() {

        // given
        given(authorityRepository.findByNameOrViewNameIgnoreCase(anyString())).willReturn(Optional.empty());

        // when + then
        assertThrows(NotFoundException.class, () -> authorityService.getAuthority(""));
    }


}
